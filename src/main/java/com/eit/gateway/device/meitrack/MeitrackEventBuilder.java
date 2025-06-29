package com.eit.gateway.device.meitrack;

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eit.gateway.entity.Vehicle;
import com.eit.gateway.entity.VehicleEvent;

@Service
public class MeitrackEventBuilder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MeitrackEventBuilder.class);

	
	
	public VehicleEvent prepareVehicleEvents(MeitrackP88LPacket packet, Vehicle vehicle) {
		try {
			GPSInfo gps = packet.getGpsInfo();

			if (gps != null) {
				VehicleEvent vehicleEvent = new VehicleEvent();

				// Set GPS coordinates
				vehicleEvent.setVin(vehicle.getVin());
				vehicleEvent.setLatitude(gps.getLatitude());
				vehicleEvent.setLongitude(gps.getLongitude());
				vehicleEvent.setSpeed(gps.getSpeed());

				vehicleEvent.setIoEvent(String.valueOf(packet.getEventCode()));

				vehicleEvent.setEngine(false);

				JSONObject tags = new JSONObject();

				tags.put("stepCount", packet.getStepCount());

				tags.put("gsm", packet.getGsmSignalStrength());

				tags.put("Satellite", gps.getSatelliteCount());

				tags.put("battery", packet.getBatteryPercentage());

				tags.put("course", gps.getCourse());
				tags.put("hdop", gps.getHdop());
				tags.put("valid", gps.isValid());
				tags.put("externalPowerSupply", packet.getExternalPowerVoltage());

				vehicleEvent.setTags(tags.toString());

				vehicleEvent.setServerTimestamp(new Date());

				// Set timestamp
				Date eventTime = gps.getTimestamp() != null ? gps.getTimestamp() : packet.getTimestamp();
				vehicleEvent.setEventTimestamp(eventTime);

				// Persist the event
//				fleetTrackingDeviceListenerBO.persistDeviceData(vehicleEvent, vehicle, "");

				return vehicleEvent;
			} else {
				return null; // No GPS data to create an event
			}
		} catch (Exception e) {
			LOGGER.error("Error preparing vehicle event: " + e.getMessage(), e);
			return null;
		}
	}

	
	

}
