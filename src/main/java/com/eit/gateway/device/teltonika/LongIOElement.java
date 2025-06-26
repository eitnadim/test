package com.eit.gateway.device.teltonika;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;


/**
 * @author Ernestas Vaiciukevicius (ernestas.vaiciukevicius@teltonika.lt)
 * 
 *         <p>
 *         IOElement with support of "long" (8 byte) property values.
 *         </p>
 */
public class LongIOElement extends IOElement {

	private Hashtable<Integer, Long> longProperties = new Hashtable();
	
	private Hashtable<Integer, BigInteger> bigIntProperties = new Hashtable();

	public long[] getLongProperty(int id) {
		Long longValue =  longProperties.get(Integer.valueOf(id));
		long[] ret = null;

		if (longValue != null) {
			ret = new long[] { id, longValue.longValue() };
		} else {
			int[] intRet = super.getProperty(id);
			if (intRet != null) {
				ret = new long[] { intRet[0], intRet[1] };
			}
		}

		return ret;
	}

	public void addLongProperty(long[] prop) {
		if (prop[0] < Integer.MIN_VALUE || prop[0] > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Wrong id value:" + prop[0]);
		}

		longProperties.put(Integer.valueOf((int) prop[0]), Long.valueOf(prop[1]));
	}

	public BigInteger[] getBigIntProperty(int id) {

		BigInteger[] ret = new BigInteger[] { BigInteger.valueOf(id) , bigIntProperties.get(id) };
		return ret;
	}

	public void addBigIntProperty(BigInteger[] prop) {
		

		bigIntProperties.put(Integer.valueOf(prop[0].intValue()), prop[1]);
	}

	public void removeLongProperty(int id) {
		longProperties.remove(Integer.valueOf(id));
	}

	public int[] getAvailableLongProperties() {
		int[] intRet = super.getAvailableProperties();
		int[] ret = null;

		if (intRet == null) {
			intRet = new int[0];
		}

		// Iterating to see the values of HashMap

//		int str;
//		Set<Integer> set = longProperties.keySet();
//		Iterator<Integer> itr = set.iterator();
//
//		while (itr.hasNext()) {
//			str = itr.next();
//
//		}

		try {
			synchronized (longProperties) {

				try {
					
					//new work for wms
					ret = new int[intRet.length + longProperties.size()+bigIntProperties.size()];

					System.arraycopy(intRet, 0, ret, 0, intRet.length);

					int ind = intRet.length;
					Enumeration<Integer> longPropertyEnumeration = longProperties.keys();
					while (longPropertyEnumeration.hasMoreElements()) {
						Integer key = (Integer) longPropertyEnumeration.nextElement();

						ret[ind++] = key.intValue();
					}
					
					// new work for wms
					Enumeration<Integer> bigIntPropertyEnumeration = bigIntProperties.keys();
					while (bigIntPropertyEnumeration.hasMoreElements()) {
						Integer key = (Integer) bigIntPropertyEnumeration.nextElement();

						ret[ind++] = key.intValue();
					}
				} catch (Exception e) {

				}

			}
		} catch (Exception e) {

		}

		return ret;
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();

//		for (Integer id : longProperties.keySet()) {
//			stringBuffer.append("[" + id + "=" + longProperties.get(id) + "] ");
//		}
		
		for (Map.Entry<Integer, Long> id : longProperties.entrySet()) {
			stringBuffer.append("[" + id.getKey() + "=" + id.getValue() + "] ");
		}

		return super.toString() + stringBuffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(longProperties);
		return result;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof LongIOElement) {
			if (!super.equals(arg0)) {
				return false;
			}
			return longProperties.equals(((LongIOElement) arg0).longProperties);
		} else {
			if (longProperties.size() == 0) {
				return super.equals(arg0);
			} else {
				return false;
			}
		}
	}

}
