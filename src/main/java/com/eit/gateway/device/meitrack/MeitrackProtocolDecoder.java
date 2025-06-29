package com.eit.gateway.device.meitrack;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MeitrackProtocolDecoder {

	private static final Logger LOGGER = LoggerFactory.getLogger(MeitrackProtocolDecoder.class);

	public MeitrackP88LPacket decode(byte[] data) {
		if (data == null) {
			LOGGER.error("Invalid data length: " + (data != null ? data.length : "null"));
			return null;
		}
		LOGGER.error("Received data length: " + data.length);

		return parseP88LPacket(data);
	}

	private MeitrackP88LPacket parseP88LPacket(byte[] data) {
		try {
			MeitrackP88LPacket packet = new MeitrackP88LPacket();
			packet.setRawData(data);
			packet.setTimestamp(new Date());

			int offset = 2; // Skip $$

			// Parse ASCII header part
			StringBuilder asciiPart = new StringBuilder();
			while (offset < data.length && data[offset] != 0x00 && data[offset] != 0x0D) {
				asciiPart.append((char) data[offset]);
				offset++;
			}

			String asciiString = asciiPart.toString();
			LOGGER.error("ASCII header: " + asciiString);

			// Parse ASCII components (messageId,imei,command,...)
			String[] parts = asciiString.split(",");
			if (parts.length >= 3) {
				packet.setMessageId(parts[0]);
				packet.setImei(parts[1]);
				packet.setCommand(parts[2]);
			}

			// Skip null bytes and parse binary data if present
//			while (offset < data.length && data[offset] == 0x00) {
//				offset++;
//			}

			if (offset < data.length - 2) { // -2 for \r\n
				parseBinaryData(packet, data, offset);
			}

			return packet;

		} catch (Exception e) {
			LOGGER.error("Error parsing P88L packet: " + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Enhanced binary data parsing method following Meitrack P88L protocol
	 */
	private void parseBinaryData(MeitrackP88LPacket packet, byte[] data, int startOffset) {
		try {
			LOGGER.error("=== ENHANCED PARAMETER PARSING ===");
			LOGGER.error("Starting at offset: " + startOffset);

			// First, let's find where 0x02 and 0x03 parameters are located in the data
//	        findGPSParametersInData(data, startOffset);

			// Check if we have enough data for basic parsing
			if (data.length - startOffset < 10) {
				LOGGER.warn("Insufficient binary data length for parsing");
				return;
			}

			GPSInfo gpsData = new GPSInfo();
			int offset = startOffset;

			// Parse according to Meitrack P88L protocol structure
			LOGGER.error("--- PARSING PROTOCOL STRUCTURE ---");

			// 1. Number of remaining cache records (4 bytes, little-endian)
			long remainingCacheRecords = readLittleEndianInt32(data, offset);
			offset += 4;
			LOGGER.error("1. Cache records: " + remainingCacheRecords + " (offset: " + offset + ")");

			// 2. Number of data packets (2 bytes, little-endian)
			int numDataPackets = readLittleEndianInt16(data, offset);
			offset += 2;
			LOGGER.error("2. Data packets: " + numDataPackets + " (offset: " + offset + ")");

			// 3. Length of data packet (2 bytes, little-endian)
			int dataPacketLength = readLittleEndianInt16(data, offset);
			offset += 2;
			LOGGER.error("3. Packet length: " + dataPacketLength + " (offset: " + offset + ")");

			// 4. Total number of ID in data packet (2 bytes, little-endian)
			int totalIdCount = readLittleEndianInt16(data, offset);
			offset += 2;
			LOGGER.error("4. Total IDs: " + totalIdCount + " (offset: " + offset + ")");

			// 5. Parse parameter sections
			offset = parseParameterSections(data, offset, gpsData, packet);

			// If structured parsing didn't find longitude, use enhanced manual extraction

			packet.setGpsInfo(gpsData);

			// Final validation
			GPSInfo finalGPS = packet.getGpsInfo();
			if (finalGPS != null) {
				LOGGER.error("=== FINAL GPS RESULT ===");
				LOGGER.error("Latitude: " + finalGPS.getLatitude());
				LOGGER.error("Longitude: " + finalGPS.getLongitude());
				LOGGER.error("Valid: " + finalGPS.isValid());
			}

		} catch (Exception e) {
			LOGGER.error("Error in parseBinaryData: " + e.getMessage(), e);
		}
	}

	private int parseParameterSections(byte[] data, int offset, GPSInfo gpsData, MeitrackP88LPacket packet) {
		try {
			// 5. Number of 1-byte parameter IDs
			if (offset >= data.length)
				return offset;

			int oneByteParamCount = data[offset] & 0xFF;
			offset++;
			LOGGER.error("5. 1-byte params: " + oneByteParamCount + " (offset: " + offset + ")");

			// Parse 1-byte parameters
			for (int i = 0; i < oneByteParamCount && offset < data.length; i++) {
				int paramId;
				int value = 0;

				// Check if this is a 2-byte parameter ID (starts with 0xFE)
				if ((data[offset] & 0xFF) == 0xFE && offset + 2 < data.length) {
					// 2-byte parameter ID
					paramId = ((data[offset] & 0xFF) << 8) | (data[offset + 1] & 0xFF);
					value = data[offset + 2] & 0xFF;
					offset += 3; // Move 3 bytes (2 for ID + 1 for value)
				} else {

					// 1-byte parameter ID
					paramId = data[offset] & 0xFF;
//					if (paramId == 0x01) {
//						byte[] values = new byte[16];
//						System.arraycopy(data, offset + 2, values, 0, 16);
//						offset += 18;
//					} else
					value = data[offset + 1] & 0xFF;

					offset += 2; // Move 2 bytes (1 for ID + 1 for value)
				}

				LOGGER.error(" param: 0x" + String.format("%04X", paramId) + " = " + value);
				parse1ByteParameter(paramId, value, gpsData, packet);
			}

			// 6. Number of 2-byte parameter IDs
			if (offset >= data.length)
				return offset;

			int twoByteParamCount = data[offset] & 0xFF;
			offset++;
			LOGGER.error("6. 2-byte params: " + twoByteParamCount + " (offset: " + offset + ")");

			// Parse 2-byte parameters
			for (int i = 0; i < twoByteParamCount && offset + 2 < data.length; i++) {
				int paramId = data[offset] & 0xFF;
				int value = readLittleEndianInt16(data, offset + 1);
				offset += 3;

				LOGGER.error("  2-byte param: 0x" + String.format("%02X", paramId) + " = " + value);
				parse2ByteParameter(paramId, value, gpsData, packet);
			}

			// 7. Number of 4-byte parameter IDs (THIS IS WHERE LAT/LON SHOULD BE)
			if (offset >= data.length)
				return offset;

			int fourByteParamCount = data[offset] & 0xFF;
			offset++;
			LOGGER.error("7. 4-byte params: " + fourByteParamCount + " (offset: " + offset + ")");

			// Parse 4-byte parameters with enhanced logging
			for (int i = 0; i < fourByteParamCount && offset < data.length; i++) {
				if (offset >= data.length) {
					LOGGER.warn("Reached end of data while parsing 4-byte param " + i);
					break;
				}

				int paramId = data[offset] & 0xFF;
				offset++;

				LOGGER.error("  4-byte param " + i + ": ID=0x" + String.format("%02X", paramId) + " (offset: "
						+ (offset - 1) + ")");

				// Handle extended IDs (0xFE prefix)
				if (paramId == 0xFE && offset < data.length) {
					int extendedId = data[offset] & 0xFF;
					offset++;
					if (offset + 4 <= data.length) {
						long value = readLittleEndianInt32(data, offset);
						offset += 4;
						LOGGER.error(
								"    Extended param: 0x" + String.format("%04X", 0xFE00 | extendedId) + " = " + value);
						parse4ByteExtendedParameter(0xFE00 | extendedId, value, gpsData, packet);
					} else {
						LOGGER.warn("Not enough data for extended parameter value");
						break;
					}
				} else {
					// Standard 4-byte parameter
					if (offset + 4 <= data.length) {
						long value = readLittleEndianInt32(data, offset);
						offset += 4;
						parse4ByteParameter(paramId, value, gpsData, packet);
					} else {
						LOGGER.warn("Not enough data for 4-byte parameter " + i + " (need 4 bytes, have "
								+ (data.length - offset) + ")");
						break;
					}
				}
			}

			// 8. Unfixed-byte parameters
			if (offset < data.length - 2) {
				int unfixedByteParamCount = data[offset] & 0xFF;
				offset++;
				LOGGER.error("8. Unfixed params: " + unfixedByteParamCount + " (offset: " + offset + ")");

				for (int i = 0; i < unfixedByteParamCount && offset < data.length - 2; i++) {
					int paramId = data[offset] & 0xFF;
					offset++;
					LOGGER.error("  Unfixed param " + i + ": 0x" + String.format("%02X", paramId));
					offset = parseUnfixedByteParameter(paramId, data, offset, gpsData, packet);
				}
			}

			return offset;

		} catch (Exception e) {
			LOGGER.error("Error in parseParameterSections: " + e.getMessage(), e);
			return offset;
		}
	}

	private void parse4ByteExtendedParameter(int paramId, long value, GPSInfo gpsData, MeitrackP88LPacket packet) {
		switch (paramId) {
		case 0xFE37: // Step count
			packet.setStepCount((int) value);
			break;
		default:
			LOGGER.debug("Unknown 4-byte extended parameter ID: 0x" + String.format("%04X", paramId));
		}
	}

	private void parse4ByteParameter(int paramId, long value, GPSInfo gpsData, MeitrackP88LPacket packet) {
		switch (paramId) {
		case 0x02: // Longitude (millionth of a degree)
			double latitude = value / 1000000.0;
			gpsData.setLatitude(latitude);
			break;
		case 0x03: // Longitude (millionth of a degree)
			double longitude = value / 1000000.0;
			gpsData.setLongitude(longitude);
			break;
		case 0x04: // Date and time (seconds since 2000-01-01)
			long timestamp = value + 946684800L; // Add seconds from 1970-01-01 to 2000-01-01
			gpsData.setTimestamp(new Date(timestamp * 1000));
			break;
		case 0x0C: // Mileage (meters)
			packet.setMileage(value);
			break;
		case 0x0D: // Run time (seconds)
			packet.setRunTime(value);
			break;
		case 0x1C: // System flag
			parseSystemFlag(value, packet, gpsData);
			break;
		default:
			LOGGER.debug("Unknown 4-byte parameter ID: 0x" + String.format("%02X", paramId));
		}
	}

	private void parseSystemFlag(long systemFlag, MeitrackP88LPacket packet, GPSInfo gpsData) {
		// Parse system flag bits according to protocol
		packet.setEep2Modified((systemFlag & 0x01) != 0);
		packet.setAccOn((systemFlag & 0x02) != 0);
//		packet.setAntiTheftArmed((systemFlag & 0x04) != 0);
		packet.setVibrating((systemFlag & 0x08) != 0);
		packet.setMoving((systemFlag & 0x10) != 0);
		packet.setExternalPowerConnected((systemFlag & 0x20) != 0);
		packet.setCharging((systemFlag & 0x40) != 0);
		packet.setSleepModeEnabled((systemFlag & 0x80) != 0);

		// Update GPS fix type based on system flags
		if ((systemFlag & 0x02) != 0) { // ACC is on
			gpsData.setFixType("3D");
		} else if (gpsData.getSatellites() >= 4) {
			gpsData.setFixType("3D");
		} else if (gpsData.getSatellites() >= 3) {
			gpsData.setFixType("2D");
		} else {
			gpsData.setFixType("No Fix");
		}
	}

	private void parseBaseStationInfo(byte[] data, int offset, MeitrackP88LPacket packet) {
		// Parse base station info: MCC, MNC, LAC, CELL_ID, RX_LEVEL
		int mcc = readLittleEndianInt16(data, offset);
		int mnc = readLittleEndianInt16(data, offset + 2);
		int lac = readLittleEndianInt16(data, offset + 4);
		long cellId = readLittleEndianInt32(data, offset + 6);
		int rxLevel = readLittleEndianInt16Signed(data, offset + 10);

		LOGGER.debug("Base Station - MCC: " + mcc + ", MNC: " + mnc + ", LAC: " + lac + ", Cell ID: " + cellId
				+ ", RX Level: " + rxLevel + " dBm");
	}

	private void parseWiFiInfo(int paramId, byte[] data, int offset, MeitrackP88LPacket packet) {
		// Parse WiFi info: MAC address (6 bytes) + RSSI (2 bytes)
		StringBuilder macAddress = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			if (i > 0)
				macAddress.append("-");
			macAddress.append(String.format("%02X", data[offset + i] & 0xFF));
		}
		int rssi = readLittleEndianInt16Signed(data, offset + 6);

		LOGGER.debug("WiFi " + (paramId - 0x1C) + " - MAC: " + macAddress + ", RSSI: " + rssi + " dBm");
	}

	private void parseNetworkInfo(byte[] data, int offset, MeitrackP88LPacket packet) {
		int dataLength = data[offset] & 0xFF;
		if (dataLength >= 3) {
			int version = data[offset + 1] & 0xFF;
			int networkType = data[offset + 2] & 0xFF;
			int descriptorLen = data[offset + 3] & 0xFF;

			if (descriptorLen > 0 && offset + 4 + descriptorLen <= data.length) {
				String descriptor = new String(data, offset + 4, descriptorLen);
				LOGGER.debug("Network Info - Type: " + networkType + ", Descriptor: " + descriptor);
			}
		}
	}

	private int parseUnfixedByteParameter(int paramId, byte[] data, int offset, GPSInfo gpsData,
			MeitrackP88LPacket packet) {
		switch (paramId) {
		case 0x0E: // Base station info (12 bytes)
			if (offset + 12 <= data.length) {
				parseBaseStationInfo(data, offset, packet);
				return offset + 12;
			}
			break;
		case 0x1D: // WiFi info (8 bytes each)
		case 0x1E:
		case 0x1F:
		case 0x20:
		case 0x21:
		case 0x22:
		case 0x23:
		case 0x24:
			if (offset + 8 <= data.length) {
				parseWiFiInfo(paramId, data, offset, packet);
				return offset + 8;
			}
			break;
		case 0x4B: // Network information (variable length)
			if (offset < data.length) {
				int dataLength = data[offset] & 0xFF;
				if (offset + dataLength + 1 <= data.length) {
					parseNetworkInfo(data, offset, packet);
					return offset + dataLength + 1;
				}
			}
			break;
		default:
			LOGGER.debug("Unknown unfixed-byte parameter ID: 0x" + String.format("%02X", paramId));
			return offset + 1; // Skip unknown parameter
		}
		return offset;
	}

	private void parse1ByteParameter(int paramId, int value, GPSInfo gpsData, MeitrackP88LPacket packet) {
		switch (paramId) {
		case 0x01: // Event code
			packet.setEventCode(value);
			LOGGER.error("Event code: " + value);
			break;
		case 0x05: // GPS positioning status
			gpsData.setValid(value == 1);
			gpsData.setGpsFixed(value == 1);
			break;
		case 0x06: // Number of satellites
			gpsData.setSatelliteCount(value);
			break;
		case 0x07: // Number of satellites
			packet.setGsmSignalStrength(value);
			break;
		case 0x15: // Input port status
			packet.setInputPortStatus(value);
			break;
		case 0xFE69: // Input port status
			packet.setBatteryPercentage(value);
			break;
		default:
			LOGGER.debug("Unknown 1-byte parameter ID: 0x" + String.format("%02X", paramId));
		}
	}

	private void parse2ByteParameter(int paramId, int value, GPSInfo gpsData, MeitrackP88LPacket packet) {
		switch (paramId) {
		case 0x08: // Speed (km/h)
			gpsData.setSpeed(value);
			break;
		case 0x09: // Driving direction (degrees)
			gpsData.setCourse(value);
			break;
		case 0x0A: // HDOP (×0.1)
			gpsData.setHdop(value / 10.0);
			// Set accuracy based on HDOP
			if (value <= 10)
				gpsData.setAccuracy(1.0); // Excellent
			else if (value <= 20)
				gpsData.setAccuracy(2.0); // Good
			else if (value <= 50)
				gpsData.setAccuracy(3.0); // Moderate
			else
				gpsData.setAccuracy(4.0); // Poor
			break;
		case 0x0B: // Altitude (meters) - signed 16-bit
			int signedAltitude = (value > 32767) ? value - 65536 : value;
			gpsData.setAltitude(signedAltitude);
			break;
		case 0x1A: // AD5 (external power analog)
			packet.setExternalPowerVoltage(value / 100.0); // Voltage = AD5/100
			break;
		default:
			LOGGER.debug("Unknown 2-byte parameter ID: 0x" + String.format("%02X", paramId));
		}
	}

	// Helper methods for reading little-endian values
	private int readLittleEndianInt16(byte[] data, int offset) {
		return (data[offset] & 0xFF) | ((data[offset + 1] & 0xFF) << 8);
	}

	private int readLittleEndianInt16Signed(byte[] data, int offset) {
		int value = readLittleEndianInt16(data, offset);
		return (value > 32767) ? value - 65536 : value;
	}

	private long readLittleEndianInt32(byte[] data, int offset) {
		return (data[offset] & 0xFF) | ((data[offset + 1] & 0xFF) << 8) | ((data[offset + 2] & 0xFF) << 16)
				| ((data[offset + 3] & 0xFF) << 24);
	}

}
