/**
 * 
 */
package com.eit.gateway.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.eit.gateway.dataservice.DeviceDetailsService;
import com.eit.gateway.device.DeviceEventHandlerFactory;
import com.eit.gateway.entity.DeviceDetails;
import com.eit.gateway.util.ConnectionCounter;
import com.eit.gateway.util.Constants;

import jakarta.annotation.PostConstruct;

/**
 * 
 */
@Component
public class AsyncGatewayServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncGatewayServer.class);

	private static Map<String, SocketClient> socketClientMap = new ConcurrentHashMap<String, SocketClient>();

	private static DeviceDetailsService dds;
	private static DeviceEventHandlerFactory dehf;
	private static ConnectionCounterThread counterThread;

	private Lock lock = new ReentrantLock();

	public AsyncGatewayServer(DeviceDetailsService dds, DeviceEventHandlerFactory dehf) {
		AsyncGatewayServer.dds = dds;
		AsyncGatewayServer.dehf = dehf;
	}

	@PostConstruct
	public void startServer() throws IOException {
		
		// Initialize the counter display thread
		counterThread = new ConnectionCounterThread();

		List<DeviceDetails> ddList = dds.getAllDeviceDetails();

		// Loop through all the device details
		for (DeviceDetails dd : ddList) {
			
			// Initialize the counter for all the devices
			ConnectionCounterThread.initializeCounter(dd.getDeviceType());
			AsynchronousChannelGroup acg;
			// Create asynchronous server socket channel
			AsynchronousServerSocketChannel assc = AsynchronousServerSocketChannel.open();
			assc.bind(new InetSocketAddress(dd.getListeningPort()));

			LOGGER.info("Waiting for a device connection on port:" + dd.getListeningPort());

			String mapKey = null;
			StringBuilder keyBuilder = null;
			
			// Comment or uncomment the below condition to stop or start sending socket data to the test server
			if ((dd.getPushDataToTestServer() != null && dd.getPushDataToTestServer().equalsIgnoreCase(Constants.YES))
					&& dd.getTestServerIp() != null && dd.getTestServerPort() != null) {

				// Key format is IpAddress:Port
				keyBuilder = new StringBuilder();
				keyBuilder.append(dd.getTestServerIp());
				keyBuilder.append(":");
				keyBuilder.append(dd.getTestServerPort());

				if (connectToRemoteServer(keyBuilder.toString(), dd.getTestServerIp(), dd.getTestServerPort()))
					mapKey = keyBuilder.toString();
				
				keyBuilder.delete(0, keyBuilder.length());
			}

			// Handler to accept the new connection
			AcceptCompletionHandler ach = new AcceptCompletionHandler(assc, dehf, dd, mapKey);
						
			// Pass the handler to the socket channel to handle the new connections
			assc.accept(null, ach);
		}

		// Start the counter display thread.
		new Thread(counterThread).start();
	}

	public static void connectionAccepted(String deviceType) {
		ConnectionCounterThread.write(new ConnectionCounter(deviceType, 1));
	}

	public static void connectionLost(String deviceType) {
		ConnectionCounterThread.write(new ConnectionCounter(deviceType, -1));
	}

	public static void writeToRemoteServer(byte[] byteMsg, String mapKey) {

		SocketClient socketClient = socketClientMap.get(mapKey);

		try {
			if (socketClient != null && socketClient.isConnected())
				socketClient.write(byteMsg);
		} catch (Exception e) {

			// If data couldn't be written, close the client socket connection. No device
			// events will be sent to the test server

			socketClientMap.remove(mapKey);
		}

	}

	private boolean connectToRemoteServer(String mapKey, String serverIP, Integer port) {

		try {

			// Only one remote server connection will be established for the each IP address
			// and port

			// Check if the socket client instance already exist
			if (socketClientMap.containsKey(mapKey)) {
				return true;
			} else {
				try {
					lock.lock();

					// Safe check to prevent creating another socket client connection for the same
					// IP and Port
					if (socketClientMap.containsKey(mapKey.toString()))
						return true;

					// Establish a new Socket Client connection if not previously established
					SocketClient socketClient = new SocketClient(serverIP, port);
					if (socketClient.connect()) {
						new Thread(socketClient).start();

						socketClientMap.put(mapKey, socketClient);
						return true;
					}

				} finally {
					lock.unlock();
				}

			}

		} catch (Exception e) {
			LOGGER.error("Exception in connectToRemoteServer=>" + e.getMessage());
		}

		return false;
	}

	/***********************************************************************************************
	 ****************************************** ConnectionCounterThread Class **********************************
	 ************************************************************************************************/

	class ConnectionCounterThread implements Runnable {

		private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionCounterThread.class);
		private static Map<String, Integer> connectionCounterMap = new HashMap<String, Integer>();
		private static BlockingQueue<ConnectionCounter> notifyQ = new LinkedBlockingDeque<>();
		private static boolean stopPolling;

		public static void initializeCounter(String deviceType) {
			connectionCounterMap.put(deviceType, 0);
		}

		public static void write(ConnectionCounter cc) {
			try {

				notifyQ.add(cc);

			} catch (Exception e) {
				LOGGER.error("Exception while adding the connection counter in to the queue");
				stopPolling = true;

			}
		}

		@Override
		public void run() {

			StringBuilder displayBuffer = new StringBuilder();
			String prefix = "Total Device Connections[";

			while (!stopPolling) {

				try {
					ConnectionCounter cc = notifyQ.poll(3, TimeUnit.SECONDS);

					if (cc != null) {

						Integer counter = connectionCounterMap.get(cc.getDeviceType());

						if (counter != null) {
							if (cc.getCounter() == 1)
								counter++;
							else
								counter--;

							connectionCounterMap.put(cc.getDeviceType(), counter);
						}
					}
					
					displayBuffer.append(prefix);
					
					for (String deviceType : connectionCounterMap.keySet()) {
						Integer counter = connectionCounterMap.get(deviceType);
						displayBuffer.append(deviceType);
						displayBuffer.append("=");
						displayBuffer.append(counter);
						displayBuffer.append(",");
					}

					displayBuffer.deleteCharAt(displayBuffer.length() - 1);

					displayBuffer.append("]");

					System.out.println(displayBuffer.toString());

					displayBuffer.delete(0, displayBuffer.length());

				} catch (Exception e) {
					e.printStackTrace();

					break;
				}
			}
		}

		public void run1() {

			StringBuilder displayBuffer = new StringBuilder();
			String prefix = "Total Device Connections[";

			while (!stopPolling) {

				try {
					ConnectionCounter cc = notifyQ.poll();

					if (cc != null) {

						Integer counter = connectionCounterMap.get(cc.getDeviceType());

						if (counter != null) {
							if (cc.getCounter() == 1)
								counter++;
							else
								counter--;

							connectionCounterMap.put(cc.getDeviceType(), counter);

							displayBuffer.append(prefix);
							for (String deviceType : connectionCounterMap.keySet()) {
								counter = connectionCounterMap.get(deviceType);
								displayBuffer.append(deviceType);
								displayBuffer.append("=");
								displayBuffer.append(counter);
								displayBuffer.append(",");
							}

							displayBuffer.deleteCharAt(displayBuffer.length() - 1);

							displayBuffer.append("]");

							System.out.println(displayBuffer.toString());

							displayBuffer.delete(0, displayBuffer.length());
						}
					}

					Thread.sleep(3000);

				} catch (Exception e) {
					e.printStackTrace();

					break;
				}
			}
		}

	}

	/***********************************************************************************************
	 ****************************************** SocketClient Class **********************************
	 ************************************************************************************************/
	class SocketClient implements Runnable {

		private static final Logger LOGGER = LoggerFactory.getLogger(SocketClient.class);
		private Queue<byte[]> eventDataQ = new ConcurrentLinkedQueue<>();
		private AsynchronousSocketChannel client;
		private String ipAddress;
		private int port;
		private boolean stopPolling;
		private boolean connected;
		private String mapKey;

		public SocketClient(String ipAddress, int port) {
			this.ipAddress = ipAddress;
			this.port = port;
		}

		/**
		 * @return the mapKey
		 */
		public String getMapKey() {
			return mapKey;
		}

		/**
		 * @param mapKey the mapKey to set
		 */
		public void setMapKey(String mapKey) {
			this.mapKey = mapKey;
		}

		/**
		 * @return the stopPolling
		 */
		public boolean isStopPolling() {
			return stopPolling;
		}

		/**
		 * @param stopPolling the stopPolling to set
		 */
		public void setStopPolling(boolean stopPolling) {
			this.stopPolling = stopPolling;
		}

		/**
		 * @return the connected
		 */
		public boolean isConnected() {
			return connected;
		}

		/**
		 * @param connected the connected to set
		 */
		public void setConnected(boolean connected) {
			this.connected = connected;
		}

		public boolean connect() {
			// TODO Auto-generated method stub
			connected = false;

			try {
				LOGGER.error("Establishing connection with the test server "+ipAddress+" on port "+port);
				Future<Void> future = this.connectToRemoteServer();
				future.get(1, TimeUnit.MINUTES);

				connected = true;
				
				LOGGER.error("Connection established with the test server "+ipAddress+" on port "+port);

			} catch (TimeoutException e) {
				LOGGER.error(
						"TimeoutException while connecting to " + ipAddress + ":" + port + "[" + e.getMessage() + "]");
			} catch (Exception e) {
				LOGGER.error(
						"Exception while communicating with " + ipAddress + ":" + port + "[" + e.getMessage() + "]");
			}

			return connected;
		}

		public void write(byte[] byteMsg) throws Exception {
			try {
				
				eventDataQ.add(byteMsg);

			} catch (Exception e) {
				LOGGER.error(
						"Exception while adding the event data in to the queue[Writing to remote server is stopped]");
				stopPolling = true;
				connected = false;
				throw e;
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			while (!stopPolling) {

				try {
					byte[] byteMsg = eventDataQ.poll();
					if (byteMsg != null) {
						ByteBuffer buffer = ByteBuffer.wrap(byteMsg);
						Future<Integer> future = client.write(buffer);
						future.get(5, TimeUnit.SECONDS);
					}

					Thread.sleep(25);

				} catch (Exception e) {
					LOGGER.error("Exception while writing to " + ipAddress + ":" + port + "[" + e.getMessage() + "]");

					break;
				}
			}

			close();
		}

		public void close() {
			try {
				stopPolling = true;
				connected = false;
				eventDataQ.clear();

				if (client != null && client.isOpen())
					client.close();

			} catch (Exception e) {
			}
		}

		private Future<Void> connectToRemoteServer() throws IOException {
			client = AsynchronousSocketChannel.open();
			InetSocketAddress hostAddress = new InetSocketAddress(ipAddress, port);
			return client.connect(hostAddress);
		}
	}

}