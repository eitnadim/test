package com.eit.gateway.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DeviceEventHandlerFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceEventHandlerFactory.class);
    
    @Autowired
    private  ApplicationContext applicationContext;
    
   
    public DeviceEventHandler createDeviceEventHandler(DeviceType dt) {
    	
        try {
            // Create an instance of the device event processor class for this device type
        	DeviceEventHandler deh = (DeviceEventHandler) dt.getEventHandlerClass().getDeclaredConstructor().newInstance();
            
            applicationContext.getAutowireCapableBeanFactory().autowireBean(deh);
            
          //  LOGGER.debug("Created device event processor for {}: {}", dt.name(), dep.getClass().getSimpleName());
            return deh;
        } catch (Exception e) {
            LOGGER.error("Failed to create device event handler for " + dt.name(), e);
            throw new RuntimeException("Failed to create device handler for " + dt.name(), e);
        }
    }
}