/**
 * 
 */
package com.eit.gateway.device;

/**
 * 
 */
public enum DeviceType {

	TELTONIKA("Teltonika","com.eit.gateway.device.teltonika.TeltonikaEventHandler",5120,30),
	MEITRACK("MeiTrack","com.eit.gateway.device.meitrack.MeiTrackEventHandler",5120,30);

	private String name;
	private String eventHandlerClassName;
	private Integer defaultPaketSize;
	private Integer defaultRedTimeoutInSeconds;
	private Class<?> eventHandlerClass;
	
	DeviceType(String name, String eventHandlerClassName,Integer defaultPaketSize,Integer defaultRedTimeoutInSeconds) {
		this.name = name;
		this.eventHandlerClassName = eventHandlerClassName;
		this.defaultPaketSize = defaultPaketSize;
		this.defaultRedTimeoutInSeconds = defaultRedTimeoutInSeconds;
	}
	
	public Class<?> getEventHandlerClass() {
        if (eventHandlerClass == null) {
            try {
            	eventHandlerClass = Class.forName(eventHandlerClassName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Event handler class not found for " + this.name(), e);
            }
        }
        return eventHandlerClass;
    }
	
	
	public static DeviceType getDeviceType(String name) {
		for (DeviceType dt:DeviceType.values()) {
			if(name.equalsIgnoreCase(dt.getName()))
				return dt;
		}
		
		return null;
	}
	
	public String getEventHandlerClasssName() {
		return eventHandlerClassName;
	}
	
	public String getName() {
		return name;
	}

	/**
	 * @return the defaultPaketSize
	 */
	public Integer getDefaultPaketSize() {
		return defaultPaketSize;
	}

	/**
	 * @return the defaultRedTimeoutInSeconds
	 */
	public Integer getDefaultRedTimeoutInSeconds() {
		return defaultRedTimeoutInSeconds;
	}

	
	
}
