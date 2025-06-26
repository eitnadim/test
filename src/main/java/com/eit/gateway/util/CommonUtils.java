/**
 * 
 */
package com.eit.gateway.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class CommonUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

	public static final byte[] SUCCESS = new byte[] { (byte) 0x01 };
	public static final byte[] FAILURE = new byte[] { (byte) 0x00 };
	
	public static byte[] wrap(byte[] data) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		try {
			dos.write(new byte[] { 0, 0, 0, 0 });
			dos.writeInt(data.length);
			dos.write(data);
			dos.writeInt(getCrc(data));
			dos.flush();
		} catch (IOException e) {
// This one shuld never hapend
		}

		return baos.toByteArray();
	}

	public static int getCrc(byte[] buffer) {
		return getCrc16(buffer, 0, buffer.length, 0xA001, 0);
	}

	public static int getCrc16(byte[] buffer, int offset, int bufLen, int polynom, int preset) {
		preset &= 0xFFFF;
		polynom &= 0xFFFF;

		int crc = preset;
		for (int i = 0; i < bufLen; i++) {
			int data = buffer[(i + offset) % buffer.length] & 0xFF;
			crc ^= data;
			for (int j = 0; j < 8; j++) {
				if ((crc & 0x0001) != 0) {
					crc = (crc >> 1) ^ polynom;
				} else {
					crc = crc >> 1;
				}
			}
		}

		return crc & 0xFFFF;
	}
	
	public static String decimalByteArrayToHexDecimalByteArray(byte[] byteArray) {
		LOGGER.trace("Converting byte array of length {} to hex string", byteArray.length);
		StringBuilder pp1 = new StringBuilder("");
		try {
			for (int i = 0; i < byteArray.length; i++) {
				String demodata = Integer.toHexString(byteArray[i]);

				if (demodata.length() == 8) {
					pp1.append(demodata.substring(demodata.length() - 2));
				} else if (demodata.length() == 1) {
					pp1.append("0").append(demodata);
				} else {
					pp1.append(demodata);
				}
			}
			LOGGER.trace("Successfully converted byte array to hex string");
		} catch (Exception e) {
			LOGGER.error("Exception while converting byte array to hex string: {}", e.getMessage(), e);
		}
		return pp1.toString();
	}

	public static int bytesToInt(byte[] bytes, boolean bigEndian) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        if (bigEndian) {
          buffer.order(java.nio.ByteOrder.BIG_ENDIAN);
        } else {
          buffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        }
        return buffer.getInt();
    }
	
	public static byte[] intToByteArray(int value) {
        byte[] byteArray = new byte[4];
        byteArray[0] = (byte) (value >> 24);
        byteArray[1] = (byte) (value >> 16);
        byteArray[2] = (byte) (value >> 8);
        byteArray[3] = (byte) (value);
        return byteArray;
    }

	public static ByteBuffer prependByteArray(byte[] existingBytes, ByteBuffer receivedByteBuffer) {
        int headerSize = existingBytes.length;
        int receivedDataSize = receivedByteBuffer.remaining();
        ByteBuffer buffer = ByteBuffer.allocate(headerSize + receivedDataSize);

        buffer.put(existingBytes);
        buffer.put(receivedByteBuffer);
        buffer.flip();

        return buffer;
    }
	
	public static ByteBuffer getByteBuffer(byte[] existingBytes, byte[] receivedBytes, int count) {
		
		byte[] dataCopy = new byte[count];
		System.arraycopy(receivedBytes, 0, dataCopy, 0, count);
		
        ByteBuffer buffer = ByteBuffer.wrap(new byte[existingBytes.length + dataCopy.length]);

        buffer.put(existingBytes);
        buffer.put(dataCopy);
        buffer.flip();

        return buffer;
    }
	
	public static ByteBuffer getByteBuffer(byte[] receivedBytes, int count) {
		byte[] dataCopy = new byte[count];
		System.arraycopy(receivedBytes, 0, dataCopy, 0, count);
        ByteBuffer buffer = ByteBuffer.wrap(new byte[dataCopy.length]);

        buffer.put(dataCopy);
        buffer.flip();

        return buffer;
    }
}
