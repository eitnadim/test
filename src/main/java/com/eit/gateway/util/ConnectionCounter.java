/**
 * 
 */
package com.eit.gateway.util;

/**
 * 
 */
public class ConnectionCounter {

	private String deviceType;
	private int counter;
	
	
	public ConnectionCounter(String deviceType, int counter) {
		super();
		this.deviceType = deviceType;
		this.counter = counter;
	}
	
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}

	@Override
	public String toString() {
		return "ConnectionCounter [deviceType=" + deviceType + ", counter=" + counter + "]";
	}
	
	
	
}
