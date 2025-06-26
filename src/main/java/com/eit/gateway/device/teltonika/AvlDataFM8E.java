package com.eit.gateway.device.teltonika;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author Ernestas Vaiciukevicius (ernestas.vaiciukevicius@teltonika.lt)
 *
 *         <p>
 *         Implementation of data codec used in FM4 modules.
 *         </p>
 */
public class AvlDataFM8E extends AvlDataFM4 {
	private int eventSource = 0;
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

//	public AvlDataFM8E() {
//	}

//	public AvlDataFM8E(long timestamp, GpsElement gpsElement, IOElement ioElement, byte priority, int eventSourceId) {
//		super(timestamp, gpsElement, ioElement, priority);
//		this.eventSource = eventSourceId;
//	}

//	@Override
//	protected AvlData read(BitInputStream codecInputStream) throws IOException {
//		try(DataInputStream dataInputStream = new DataInputStream(codecInputStream)) {
//			
//			long timestamp = dataInputStream.readLong();
//			byte priority = dataInputStream.readByte();
//			GpsElement gpsData = GpsElement.read(codecInputStream);
//			
//			int eventSourceId =  dataInputStream.readShort();
//			
//			LongIOElement iodata = readIOElement(codecInputStream);
//			
////			AvlDataFM8E ret = new AvlDataFM8E(timestamp, gpsData, iodata, priority, eventSourceId);
//			
//			return new AvlDataFM8E(timestamp, gpsData, iodata, priority, eventSourceId);
//			
//		}
//	}
	
	
	protected int geteventSourceFromData(BitInputStream codecInputStream) throws IOException {
		try (DataInputStream dis = new DataInputStream(codecInputStream)) {
		     return dis.readShort();
		}
	}

	protected LongIOElement readIOElement(BitInputStream codecInputStream) throws IOException {
		try (DataInputStream dis = new DataInputStream(codecInputStream)) {

			LongIOElement ret = new LongIOElement();
			int totalProperties = dis.readUnsignedShort();
			int propertiesRead = 0;

			// read one-byte properties
			int oneByteProperties = dis.readUnsignedShort();
			for (int i = 0; i < oneByteProperties; ++i) {
				int id = dis.readUnsignedShort();
				int value = dis.readByte();
				ret.addProperty(new int[] { id, value });
				++propertiesRead;
			}

			// read two-byte properties
			int twoByteProperties = dis.readUnsignedShort();
			for (int i = 0; i < twoByteProperties; ++i) {
				int id = dis.readUnsignedShort();
				int value = dis.readShort();
				ret.addProperty(new int[] { id, value });
				++propertiesRead;
			}

			// read four-byte properties
			int fourByteProperties = dis.readUnsignedShort();
			for (int i = 0; i < fourByteProperties; ++i) {
				int id = dis.readUnsignedShort();
				int value = dis.readInt();
				ret.addProperty(new int[] { id, value });
				++propertiesRead;
			}

			// read eight-byte properties
			int eightByteProperties = dis.readUnsignedShort();
			for (int i = 0; i < eightByteProperties; ++i) {
				int id = dis.readUnsignedShort();
				long value = dis.readLong();
				ret.addLongProperty(new long[] { id, value });
				++propertiesRead;
			}

			// read Nth-byte properties
			int nThByteProperties = dis.readUnsignedShort();
			for (int i = 0; i < nThByteProperties; ++i) {
				int id = dis.readUnsignedShort();
				int valueLength = dis.readUnsignedShort();

				switch (valueLength) {
				case 1: {
					int value = dis.readByte();
					ret.addProperty(new int[] { id, value });
					++propertiesRead;
					break;
				}
				case 2: {
					int value = dis.readShort();
					ret.addProperty(new int[] { id, value });
					++propertiesRead;
					break;
				}
				case 4: {
					int value = dis.readInt();
					ret.addProperty(new int[] { id, value });
					++propertiesRead;
					break;
				}
				case 8: {
					long value = dis.readLong();
					ret.addLongProperty(new long[] { id, value });
					++propertiesRead;
					break;
				}
				default: {
					byte[] x = new byte[valueLength];
					dis.readFully(x);
					String sb=bytesToHex(x);
					++propertiesRead;
					
					if (valueLength < 9) {
						long value = (short) Long.parseLong(sb, 16);
						ret.addLongProperty(new long[] { id, value });
						
					}
					
					else if(id==109) {
						BigInteger decimalValue = new BigInteger(sb, 16);
						
						ret.addBigIntProperty(new BigInteger[] { BigInteger.valueOf(id), decimalValue });
							
					}
					
					break;
				}
				}
			}

			if (totalProperties != propertiesRead) {
				throw new IOException("Wrong totalProperties field");
			}

			return ret;

		}
	}
	
	public String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for (int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars);
	}

	/**
	 * Instance of data codec used to encode/decode this data element
	 */
	private static AvlData dataCodec = null;

	/**
	 * 
	 * @return Returns DataCodec for encoding/decoding this data element
	 */
	public static AvlData getCodec() {
		if (dataCodec == null) {
			dataCodec = new AvlDataFM8E();

		}

		return dataCodec;
	}

	@Override
	public byte getCodecId() {
		return -114;
	}

//	@Override
//	public int getTriggeredPropertyId() {
//		return eventSource;
//	}

	@Override
	public String toString() {
		return "FM8E [Priority=" + getPriority() + "] [GPS element=" + getGpsElement() + "] [IO="
				+ getInputOutputElement() + "] [Timestamp=" + getTimestamp() + "] [EventSource="
				+ getTriggeredPropertyId() + "]";
	}

}
