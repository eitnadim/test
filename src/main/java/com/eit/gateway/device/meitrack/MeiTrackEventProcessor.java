/**
 * 
 */
package com.eit.gateway.device.meitrack;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.eit.gateway.cache.CacheManager;
import com.eit.gateway.device.DeviceEventHandler;
import com.eit.gateway.device.common.EventFactory;
import com.eit.gateway.entity.CompanyTrackDevice;
import com.eit.gateway.entity.VehicleEvent;
import com.eit.gateway.exceptions.CloseDeviceConnectionException;
import com.eit.gateway.server.AsyncGatewayServer;
import com.eit.gateway.util.CommonUtils;

/**
 * 
 */
public class MeiTrackEventProcessor extends DeviceEventHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(MeiTrackEventProcessor.class);

	@Autowired
	CommonUtils commonUtils;

	@Autowired
	MeitrackProtocolDecoder meitrackProtocolDecoder;

	@Autowired
	EventFactory EventFactory;

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

					// Extract IMEI from the received byte array
					String imeiNo = extractIMEIFromByteArray(bufferData);

					if (imeiNo == null)
						return;

					setImeiNo(imeiNo);
					// Get the validated status
					boolean status = CacheManager.isImeiNoExist(getImeiNo());

					if (!status) {
						closeDevice = true;
						LOGGER.error("IMEI number " + getImeiNo() + " is not registered in the system.");
					}

				}

				byte[] resonseMsg = null;

				// Process the data with received buffer
				resonseMsg = this.proces();

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
	public byte[] proces() throws Exception {
		try {

			// Get the validated status

			byte[] bufferData = new byte[getBuffer().limit()];

			getBuffer().get(bufferData, 0, getBuffer().limit());

			MeitrackP88LPacket packet = meitrackProtocolDecoder.decode(bufferData);
			if (packet == null) {
				throw new CloseDeviceConnectionException("Invalid packet received or unable to decode packet");
			}

			insertService(packet, CommonUtils.decimalByteArrayToHexDecimalByteArray(bufferData));

		} catch (CloseDeviceConnectionException e) {
			throw e;

		} catch (Exception e) {
			throw e;

		}

		return null;
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

	private String extractIMEIFromByteArray(byte[] packetData) {
		try {
			if (packetData == null || packetData.length < 10) {
				LOGGER.warn("Packet data is null or too short for IMEI extraction");
				return null;
			}

			// Check for start delimiter $$
			if (packetData[0] != 0x24 || packetData[1] != 0x24) {
				LOGGER.warn("Invalid start delimiter - not a valid Meitrack packet");
				return null;
			}

			int offset = 2;
			StringBuilder asciiSection = new StringBuilder();

			while (offset < packetData.length && packetData[offset] != 0x00 && packetData[offset] != 0x0D) {
				asciiSection.append((char) packetData[offset]);
				offset++;
			}

			String asciiString = asciiSection.toString();
			LOGGER.debug("ASCII section extracted: {}", asciiString);

			String[] parts = asciiString.split(",");
			if (parts.length < 2) {
				LOGGER.warn("Insufficient parts in packet, expected at least 2, got: {}", parts.length);
				return null;
			}

			return parts[1].trim();

		} catch (Exception e) {
			LOGGER.error("Error extracting IMEI from packet: {}", e.getMessage(), e);
			return null;
		}
	}

	private void insertService(MeitrackP88LPacket packet, String hexDataAsStringFormate) {

		try {

			// Must be true. Otherwise problem in database configuration.
			setVehicle(commonUtils.loadVehicle(getImeiNo()));
			LOGGER.info("Vehicle Data {}", getVehicle());

			if (getVehicle() == null) {
				LOGGER.info("Vehicle is Empty :: Vin {}", getImeiNo());
				return;
			}

			CompanyTrackDevice companyTrackDevice = commonUtils.loadCompanyTrackDevice(getVehicle().getVin());

			if (companyTrackDevice == null) {
				LOGGER.info("NUll in CompanyTrackDevice :::{}", getVehicle().getVin());
			} else {
				VehicleEvent vehicleEvent = EventFactory.TelematicsEventFactory(getVehicle(), packet,
						companyTrackDevice);
				if (vehicleEvent == null) {
					LOGGER.warn("VehicleEvent is null for IMEI: {}", getImeiNo());
					return;
				}

			}

		} catch (Exception e) {
			LOGGER.error("Exception while persisting data for IMEI {}: {}", getImeiNo(), e.getMessage(), e);
		}
	}

}
