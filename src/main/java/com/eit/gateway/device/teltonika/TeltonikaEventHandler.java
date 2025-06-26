/**
 * 
 */
package com.eit.gateway.device.teltonika;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eit.gateway.cache.CacheManager;
import com.eit.gateway.dataservice.CustomService;
import com.eit.gateway.dataservice.VehicleService;
import com.eit.gateway.device.DeviceEventHandler;
import com.eit.gateway.device.common.CommonDeviceParser;
import com.eit.gateway.device.common.DeviceLogicHandlerBO;
import com.eit.gateway.entity.CompanyTrackDevice;
import com.eit.gateway.entity.VehicleEvent;
import com.eit.gateway.exceptions.CloseDeviceConnectionException;
import com.eit.gateway.server.AsyncGatewayServer;
import com.eit.gateway.util.CommonUtils;

/**
 * 
 */
@Component
public class TeltonikaEventHandler extends DeviceEventHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(TeltonikaEventHandler.class);

	@Autowired
	private VehicleService vehicleService;

	@Autowired
	private CustomService customService;

	@Autowired
	private CommonDeviceParser commonDeviceParser;

	@Autowired
	private DeviceLogicHandlerBO deviceLogicHandlerBO;

	public TeltonikaEventHandler() {
		addCodecs();
	}

	@Override
	public void completed(Integer bytesRead, Void attachment) {

		boolean closeDevice = false;

		// if no data to process
		if (bytesRead == 0)
			return;

		try {

			if (bytesRead > 0) {

				getBuffer().flip();

				// Comment or uncomment the below condition to stop or start sending socket data
				// to the test server

				// Check the received data to be sent to the test server
				if (getDeviceDetails().getPushDataToTestServer() != null && getMapKey() != null) {

					getBuffer().mark();

					byte[] byteMsg = new byte[getBuffer().limit()];
					getBuffer().get(byteMsg, 0, getBuffer().limit());

					AsyncGatewayServer.writeToRemoteServer(byteMsg, getMapKey());

					getBuffer().reset();
				}

				// If null, it is the first packet
				if (getImeiNo() == null) {

					byte[] bufferData = new byte[getBuffer().limit()];

					getBuffer().get(bufferData, 0, getBuffer().limit());

					setImeiNo(new String(bufferData, StandardCharsets.UTF_8));

					// Get the validated status
					boolean status = CacheManager.isImeiNoExist(getImeiNo());

					// If true
					if (status) {
						sendToDevice(CommonUtils.SUCCESS);
					} else {
						closeDevice = true;
						sendToDevice(CommonUtils.FAILURE);

						LOGGER.error("IMEI number " + getImeiNo()
								+ " doesn't exist or the status may not be active in database.");
					}

					return;
				}

				byte[] resonseMsg = null;

				// Process the data with received buffer
				resonseMsg = this.proces();

				// Check if a response message to be sent to the device
				if (resonseMsg != null)
					sendToDevice(resonseMsg);

			} else {
				LOGGER.error("Connection lost with " + getDeviceDetails().getDeviceType() + " having IMEI number "
						+ getImeiNo());
				closeDevice = true;
			}

		} catch (Exception e) {

			e.printStackTrace();

			closeDevice = true;

		} finally {

			if (closeDevice) {
				AsyncGatewayServer.connectionLost(getDeviceDetails().getDeviceType());
				try {
					getAsyncSocketChannel().close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// Clear the buffer only when no bytes remaining in the buffer to process.
				if (!getBuffer().hasRemaining())
					getBuffer().clear();
				else // Otherwise copy the remaining bytes to the beginning of the buffer.
					getBuffer().compact();

				// Go for next read
				getAsyncSocketChannel().read(getBuffer(), null, this);
			}
		}

	}

	@Override
	public void failed(Throwable exc, Void attachment) {
		LOGGER.error("Failed in TeltonikaEventHandler ->" + exc.getMessage());
		AsyncGatewayServer.connectionLost(getDeviceDetails().getDeviceType());
		try {
			getAsyncSocketChannel().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public byte[] proces() throws Exception {

		try {

			if (getBuffer().remaining() < 8)
				return null;

			getBuffer().getInt(); // Reading zero bytes

			int dataSize = getBuffer().getInt();
			LOGGER.info("dataSize=" + dataSize);
			if (dataSize > 0xFFFF)
				throw new CloseDeviceConnectionException("Data packet size is greater than 65535)");

			if (dataSize < 1)
				throw new CloseDeviceConnectionException("Data packet size is < 1");

			// if data in the buffer is less than the data size, return to read the next
			// available data
			if (getBuffer().remaining() < dataSize) {
				LOGGER.info(getBuffer().remaining() + " is less than " + dataSize);
				return null;
			}

			byte[] data = new byte[dataSize];
			getBuffer().get(data);

			if (data.length != dataSize)
				throw new CloseDeviceConnectionException(
						"Received data size " + data.length + " is less than specified packet size of " + dataSize);

			int correctCrc = CommonUtils.getCrc(data);
			int crc = getBuffer().getInt();

			if (crc != correctCrc)
				throw new CloseDeviceConnectionException("Crc test failed: " + crc + " != " + correctCrc);

			// Check the Codec ID = 12
			if (data[0] == 0x0C) {
				String command = Tools.toHexadecimal(data);
				LOGGER.debug("Received command response: {}", command);
				byte[] bytes = { data[3], data[4], data[5], data[6] };
				int commandSize = Codec12Format.byteArrayToInt(bytes) * 2;
				String commandStatus = Codec12Format.hexStringToASCIIString(command.substring(14, 14 + commandSize));
				LOGGER.info("Command status for IMEI {}: {}", getImeiNo(), commandStatus);
			} else {

				AvlData decoder = (AvlData) getCodec(data[0]);
				if (decoder == null) {
					// send failure response to the device
					return CommonUtils.FAILURE;

				} else {
					AvlData[] decoded = decoder.decode(data);
					LOGGER.error("Inserting........");

					// Any exception while processing the message in the below method must be
					// handled locally and shouldn't be thrown.
					insertService(decoded, data.length);

					// Regardless of the exception while processing the message, the length of the
					// array must be sent to the device.
					return CommonUtils.intToByteArray(decoded.length);
				}
			}

		} catch (CloseDeviceConnectionException e) {
			throw e;

		} catch (Exception e) {
			throw e;

		}

		return null;
	}

	private void loadVehicle() {

		setVehicle(vehicleService.getActiveVehicleByImeiNo(getImeiNo()));
	}

	private void insertService(AvlData[] avlDataArray, long byteTrx) {

		try {

			// Must be true. Otherwise problem in database configuration.
			loadVehicle();
			LOGGER.info("Vehicle Data {}", getVehicle());

			if (getVehicle() == null) {
				LOGGER.info("Vehicle is Empty :: Vin {}", getImeiNo());
				return;
			}

			CompanyTrackDevice companyTrackDevice = customService.getCompanyTrackDevice(getVehicle().getVin());

			if (companyTrackDevice == null) {
				LOGGER.info("NUll in CompanyTrackDevice :::{}", getVehicle().getVin());
			} else {
				VehicleEvent vehicleEvent = deviceLogicHandlerBO.prepareVehicleEvents(getVehicle(), avlDataArray,
						byteTrx, companyTrackDevice);

				if (vehicleEvent != null) {
					// This call returns immediately - calling thread doesn't wait
					commonDeviceParser.persistVehicleDataAsync(vehicleEvent, getVehicle(), companyTrackDevice);
					LOGGER.error("Submitted vehicle event for async processing: {}", vehicleEvent.getVin());
				} else {
					LOGGER.warn("No vehicle events to process for IMEI: {}", getImeiNo());
				}
			}

		} catch (Exception e) {
			LOGGER.error("Exception while persisting data for IMEI {}: {}", getImeiNo(), e.getMessage(), e);
		}
	}

	public void sendToDevice(byte[] dataToWrite) throws CloseDeviceConnectionException {
		try {
			ByteBuffer bb = ByteBuffer.wrap(dataToWrite);
			getAsyncSocketChannel().write(bb);
			bb.clear();
		} catch (Exception e) {
			throw new CloseDeviceConnectionException(
					"Exception while sending data to the device[" + e.getMessage() + "]");
		}
	}
}
