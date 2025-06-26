package com.eit.gateway.device.teltonika;

public class Tools {
	/**
	 * Converts byte array to hex string
	 * 
	 * @param byteArray Byte array to convert
	 * @return Returns hex string
	 */
	public static String bufferToHex(byte[] byteArray) {
		if (byteArray == null) {
			return "null";
		}

		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0, n = byteArray.length; i < n; i++) {
			String append = Integer.toHexString(byteArray[i] & 0xFF);
			if (append.length() < 2) {
				stringBuffer.append('0');
			}
			stringBuffer.append(append);
		}

		return stringBuffer.toString();
	}

	public static String toHexadecimal(byte[] bytes)
    {
        StringBuilder result = new StringBuilder();

        for (byte i : bytes) {
            int decimal = (int)i & 0XFF;
            String hex = Integer.toHexString(decimal);

            if (hex.length() % 2 == 1) {
                hex = "0" + hex;
            }

            result.append(hex);
        }
        return result.toString();
    }
	
	/**
	 * Converts hex string to byte array. Supplied string length must be even or
	 * else last char in string will be ignored
	 * 
	 * @param hexString Hex string to convert
	 * @return Returns converted byte array
	 * @throws NumberFormatException If invalid hex string is supplied
	 */
	public static byte[] hexToBuffer(String hexString) throws NumberFormatException {
		byte[] result = new byte[hexString.length() / 2];
		for (int i = 0, n = result.length; i < n; i++) {
			result[i] = (byte) Integer.parseInt(hexString.substring(i * 2, (i + 1) * 2), 16);
		}
		return result;
	}
}
