/**
 * 
 */
package com.eit.gateway.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eit.gateway.device.DeviceEventHandler;
import com.eit.gateway.device.DeviceEventHandlerFactory;
import com.eit.gateway.device.DeviceType;
import com.eit.gateway.entity.DeviceDetails;

/**
 * 
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AcceptCompletionHandler.class);

	private final AsynchronousServerSocketChannel assc;
	private DeviceEventHandlerFactory dehf;
	private DeviceDetails dd;
	private String mapKey;
	
	AcceptCompletionHandler(AsynchronousServerSocketChannel assc, DeviceEventHandlerFactory dehf,
			DeviceDetails dd, String mapKey) {
		this.assc = assc;
		this.dehf = dehf;
		this.dd = dd;
		this.mapKey = mapKey;
	}

	@Override
	public void completed(AsynchronousSocketChannel asc, Void attachment) {

		// Immediately accept another connection
		if (assc.isOpen()) {
			assc.accept(attachment, this);
		}

		// Notify a new connection is accepted
		AsyncGatewayServer.connectionAccepted(dd.getDeviceType());

		// Get the correct device type enum
		DeviceType dt = DeviceType.getDeviceType(dd.getDeviceType());

		if (dd.getPacketSize() < dt.getDefaultPaketSize())
			dd.setPacketSize(dt.getDefaultPaketSize());

		if (dd.getReadTimeoutInSeconds() < dt.getDefaultRedTimeoutInSeconds())
			dd.setReadTimeoutInSeconds(dt.getDefaultRedTimeoutInSeconds());

		// Reusable buffer will be used by socket channel to read the socket data
		ByteBuffer buffer = ByteBuffer.allocate(dd.getPacketSize());
				
		// Get the device specific handler to handle the socket data
		DeviceEventHandler deh = dehf.createDeviceEventHandler(dt);
		deh.setAsyncSocketChannel(asc);
		deh.setBuffer(buffer);
		deh.setDeviceDetails(dd);
		deh.setMapKey(mapKey);
		
		// Pass the buffer and handler instance to the socket channel to read and handle the socket data
		asc.read(buffer, null, deh);
	}

	@Override
	public void failed(Throwable exc, Void attachment) {
		LOGGER.error("Failed to accept connection on port " + dd.getListeningPort());
		exc.printStackTrace();

	}

}
