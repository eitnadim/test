package com.eit.gateway.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.dataservice.AlertConfigService;
import com.eit.gateway.dataservice.CustomService;
import com.eit.gateway.dataservice.VehicleAlertService;
import com.eit.gateway.entity.AlertConfig;
import com.eit.gateway.entity.Vehicle;
import com.eit.gateway.entity.VehicleAlerts;
import com.eit.gateway.entity.VehicleEvent;
import com.eit.gateway.util.TimeZoneUtil;

/**
 * Service responsible for managing and processing vehicle alerts. Handles
 * different alert types and conditions for fleet tracking system.
 */
@Service
public class AlertService {

	@Autowired
	private VehicleAlertService vehicleAlertService;

	@Autowired
	private AlertConfigService alertConfigService;

	@Autowired
	private CustomService customService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AlertService.class);

	// Convert String array to List

	private static final String[] alertList = { "IDLE", "STOP", "TOWED", "OVERSPEED", "HARSHACCELERATION",
			"HARSHBRAKING", "DRIFT", "PANIC", "DOOR_OPEN", "JAMMING_DETECTION", "LOW_BATTERY", "TEMPERATURE_HIGH",
			"TEMPERATURE_LOW", "FUEL_THEFT" };

	private static final Set<String> SUPPORTED_ALERT_TYPES = new HashSet<>(Arrays.asList(alertList));

	/**
	 * Alert type definitions with validation and processing logic
	 */
	public enum AlertType {
		IDLE("Engine Idle Time") {
			@Override
			public boolean isAlertConditionMet(VehicleEvent event, AlertConfig config, boolean duration) {
				return event.getEngine() != null && event.getEngine() && event.getSpeed() != null
						&& event.getSpeed() == 0 && duration;
			}

			@Override
			public String getAlertDescription(String plateNo, VehicleEvent event, AlertConfig config) {
				int interval = Integer.valueOf(config.getRefinterval());
				String eventTime = TimeZoneUtil.getTimeINYYYYMMddssa(event.getEventTimestamp());
				return "Alert%0DType : Engine Idle Time%0Dvehicle:" + plateNo + "%0DLimit:" + (interval / 60)
						+ "min %0DTime:" + eventTime;
			}

			@Override
			public String getAdditionalInfo(AlertConfig config, VehicleEvent event) {
				int interval = Integer.valueOf(config.getRefinterval());
				return "Exceeds " + (interval / 60) + "min Limit";
			}

			@Override
			public String getAlertType() {
				return "Idle";
			}
		},

		STOP("Engine Stop Time") {
			@Override
			public boolean isAlertConditionMet(VehicleEvent event, AlertConfig config, boolean duration) {
				return event.getEngine() != null && !event.getEngine() && event.getSpeed() != null
						&& event.getSpeed() == 0 && duration;
			}

			@Override
			public String getAlertDescription(String plateNo, VehicleEvent event, AlertConfig config) {
				int interval = Integer.valueOf(config.getRefinterval());
				String eventTime = TimeZoneUtil.getTimeINYYYYMMddssa(event.getEventTimestamp());
				return "Alert%0DType : Engine Stop Time%0Dvehicle:" + plateNo + "%0DLimit:" + (interval / 60)
						+ "min %0DTime:" + eventTime;
			}

			@Override
			public String getAdditionalInfo(AlertConfig config, VehicleEvent event) {
				int interval = Integer.valueOf(config.getRefinterval());
				return "Exceeds " + (interval / 60) + "min Limit";
			}

			@Override
			public String getAlertType() {
				return "Stop";
			}
		},

		TOWED("Vehicle Towed") {
			@Override
			public boolean isAlertConditionMet(VehicleEvent event, AlertConfig config, boolean duration) {
				return event.getEngine() != null && !event.getEngine() && event.getSpeed() != null
						&& event.getSpeed() > 5 && duration;
			}

			@Override
			public String getAlertDescription(String plateNo, VehicleEvent event, AlertConfig config) {
				String eventTime = TimeZoneUtil.getTimeINYYYYMMddssa(event.getEventTimestamp());
				return "Alert%0DType : Vehicle Towed%0Dvehicle:" + plateNo + "%0DSpeed: " + event.getSpeed()
						+ " kmph%0DTime:" + eventTime;
			}

			@Override
			public String getAlertType() {
				return "Towed";
			}
		},

		OVERSPEED("Overspeed") {
			@Override
			public boolean isAlertConditionMet(VehicleEvent event, AlertConfig config, boolean duration) {
				int speedLimit = new JSONObject(config.getAlertconfig()).getString("speed").equalsIgnoreCase("") ? 0
						: Integer.parseInt(new JSONObject(config.getAlertconfig()).getString("speed"));
				return event.getSpeed() != null && event.getSpeed() > speedLimit;
			}

			@Override
			public String getAlertDescription(String plateNo, VehicleEvent event, AlertConfig config) {
				String speedTime = TimeZoneUtil.getTimeINYYYYMMddssa(event.getEventTimestamp());
				int speedLimit = Integer.parseInt(config.getRefinterval());
				return "Speed: Vehicle " + plateNo + " speed is " + event.getSpeed() + "km " + event.getLatitude() + ","
						+ event.getLongitude() + " at " + speedTime + " Speed limit is " + speedLimit;
			}

			@Override
			public String getAdditionalInfo(AlertConfig config, VehicleEvent event) {
				int speedLimit = Integer.parseInt(config.getRefinterval());
				return event.getSpeed() + "KM-Speed Limit " + speedLimit + "km";
			}

			@Override
			public String getAlertType() {
				return "Overspeed";
			}

		},

		HARSHACCELERATION("Harsh Acceleration") {
			@Override
			public boolean isAlertConditionMet(VehicleEvent event, AlertConfig config, boolean duration) {
				if (event.getIoEvent() == null)
					return false;

				String[] ioEvent = event.getIoEvent().split(",");
				for (String io : ioEvent) {
					String[] ioValue = io.split("=");
					// Teltonika: IO 253=1 for harsh acceleration
					if (ioValue.length == 2 && ioValue[0].equals("253") && ioValue[1].equals("1")) {
						return true;
					}
				}
				return false;
			}

			@Override
			public String getAlertDescription(String plateNo, VehicleEvent event, AlertConfig config) {
				String eventTime = TimeZoneUtil.getTimeINYYYYMMddssa(event.getEventTimestamp());
				return "Alert%0DType : Harsh Acceleration%0DPlateNo:" + plateNo + "%0DSpeed:" + event.getSpeed()
						+ " kmph%0DTime:" + eventTime + "%0DLocation:" + event.getLatitude() + ","
						+ event.getLongitude();
			}

			@Override
			public String getAdditionalInfo(AlertConfig config, VehicleEvent event) {
				return "Speed: " + event.getSpeed() + " kmph";
			}

			@Override
			public String getAlertType() {
				return "HARSHACCELERATION";
			}
		},

		HARSHBRAKING("Harsh Braking") {
			@Override
			public boolean isAlertConditionMet(VehicleEvent event, AlertConfig config, boolean duration) {
				if (event.getIoEvent() == null)
					return false;

				String[] ioEvent = event.getIoEvent().split(",");
				for (String io : ioEvent) {
					String[] ioValue = io.split("=");
					// Teltonika: IO 253=2 for harsh braking
					if (ioValue.length == 2 && ioValue[0].equals("253") && ioValue[1].equals("2")) {
						return true;
					}
				}
				return false;
			}

			@Override
			public String getAlertDescription(String plateNo, VehicleEvent event, AlertConfig config) {
				String eventTime = TimeZoneUtil.getTimeINYYYYMMddssa(event.getEventTimestamp());
				return "Alert%0DType : Harsh Braking%0DPlateNo:" + plateNo + "%0DSpeed:" + event.getSpeed()
						+ " kmph%0DTime:" + eventTime + "%0DLocation:" + event.getLatitude() + ","
						+ event.getLongitude();
			}

			@Override
			public String getAdditionalInfo(AlertConfig config, VehicleEvent event) {
				return "Speed: " + event.getSpeed() + " kmph";
			}

			@Override
			public String getAlertType() {
				return "HARSHBRAKING";
			}
		},

		DRIFT("Harsh Cornering") {
			@Override
			public boolean isAlertConditionMet(VehicleEvent event, AlertConfig config, boolean duration) {
				if (event.getIoEvent() == null)
					return false;

				String[] ioEvent = event.getIoEvent().split(",");
				for (String io : ioEvent) {
					String[] ioValue = io.split("=");
					// Teltonika: IO 253=3 for harsh cornering
					if (ioValue.length == 2 && ioValue[0].equals("253") && ioValue[1].equals("3")) {
						return true;
					}
				}
				return false;
			}

			@Override
			public String getAlertDescription(String plateNo, VehicleEvent event, AlertConfig config) {
				String eventTime = TimeZoneUtil.getTimeINYYYYMMddssa(event.getEventTimestamp());
				return "Alert%0DType : Harsh Cornering%0DPlateNo:" + plateNo + "%0DSpeed:" + event.getSpeed()
						+ " kmph%0DTime:" + eventTime + "%0DLocation:" + event.getLatitude() + ","
						+ event.getLongitude();
			}

			@Override
			public String getAdditionalInfo(AlertConfig config, VehicleEvent event) {
				return "Speed: " + event.getSpeed() + " kmph";
			}

			@Override
			public String getAlertType() {
				return "HARSHCORNERING";
			}
		},

		PANIC("Panic Button") {
			@Override
			public boolean isAlertConditionMet(VehicleEvent event, AlertConfig config, boolean duration) {
				if (event.getIoEvent() == null)
					return false;

				String[] ioEvent = event.getIoEvent().split(",");
				for (String io : ioEvent) {
					String[] ioValue = io.split("=");
					// Teltonika: IO 200=1 for panic button
					if (ioValue.length == 2 && ioValue[0].equals("200") && ioValue[1].equals("1")) {
						return true;
					}
				}
				return false;
			}

			@Override
			public String getAlertDescription(String plateNo, VehicleEvent event, AlertConfig config) {
				String eventTime = TimeZoneUtil.getTimeINYYYYMMddssa(event.getEventTimestamp());
				return "EMERGENCY ALERT%0DType : Panic Button Pressed%0DPlateNo:" + plateNo + "%0DTime:" + eventTime
						+ "%0DLocation:" + event.getLatitude() + "," + event.getLongitude()
						+ "%0DImmediate Response Required!";
			}

			@Override
			public String getAlertType() {
				return "PANIC";
			}
		},

		DOOR_OPEN("Door Open") {
			@Override
			public boolean isAlertConditionMet(VehicleEvent event, AlertConfig config, boolean duration) {
				if (event.getIoEvent() == null)
					return false;

				String[] ioEvent = event.getIoEvent().split(",");
				for (String io : ioEvent) {
					String[] ioValue = io.split("=");
					// Check for door open IO events (can be customized based on device
					// configuration)
					if (ioValue.length == 2 && (ioValue[0].equals("1") || ioValue[0].equals("2"))
							&& ioValue[1].equals("1")) {
						return true;
					}
				}
				return false;
			}

			@Override
			public String getAlertDescription(String plateNo, VehicleEvent event, AlertConfig config) {
				String eventTime = TimeZoneUtil.getTimeINYYYYMMddssa(event.getEventTimestamp());
				return "Alert%0DType : Door Open%0DPlateNo:" + plateNo + "%0DTime:" + eventTime + "%0DLocation:"
						+ event.getLatitude() + "," + event.getLongitude();
			}

			@Override
			public String getAlertType() {
				return "DOOROPEN";
			}
		},

		JAMMING_DETECTION("Jamming Detection") {
			@Override
			public boolean isAlertConditionMet(VehicleEvent event, AlertConfig config, boolean duration) {
				if (event.getIoEvent() == null)
					return false;

				String[] ioEvent = event.getIoEvent().split(",");
				for (String io : ioEvent) {
					String[] ioValue = io.split("=");
					// Teltonika: IO 249=1 for jamming detection
					if (ioValue.length == 2 && ioValue[0].equals("249") && ioValue[1].equals("1")) {
						return true;
					}
				}
				return false;
			}

			@Override
			public String getAlertDescription(String plateNo, VehicleEvent event, AlertConfig config) {
				String eventTime = TimeZoneUtil.getTimeINYYYYMMddssa(event.getEventTimestamp());
				return "SECURITY ALERT%0DType : Signal Jamming Detected%0DPlateNo:" + plateNo + "%0DTime:" + eventTime
						+ "%0DLocation:" + event.getLatitude() + "," + event.getLongitude()
						+ "%0DPossible Security Threat!";
			}

			@Override
			public String getAlertType() {
				return "JAMMING";
			}
		},

		LOW_BATTERY("Low Battery") {
			@Override
			public boolean isAlertConditionMet(VehicleEvent event, AlertConfig config, boolean duration) {
				if (event.getIoEvent() == null)
					return false;

				String[] ioEvent = event.getIoEvent().split(",");
				for (String io : ioEvent) {
					String[] ioValue = io.split("=");
					// Check for low battery voltage (IO 66 for external voltage)
					if (ioValue.length == 2 && ioValue[0].equals("66")) {
						try {
							int voltage = Integer.parseInt(ioValue[1]);
							// Alert if voltage below 11V (11000mV)
							return voltage < 11000;
						} catch (NumberFormatException e) {
							return false;
						}
					}
				}
				return false;
			}

			@Override
			public String getAlertDescription(String plateNo, VehicleEvent event, AlertConfig config) {
				String eventTime = TimeZoneUtil.getTimeINYYYYMMddssa(event.getEventTimestamp());
				return "Alert%0DType : Low Battery Voltage%0DPlateNo:" + plateNo + "%0DTime:" + eventTime
						+ "%0DLocation:" + event.getLatitude() + "," + event.getLongitude() + "%0DRequires Attention!";
			}

			@Override
			public String getAlertType() {
				return "LOWBATTERY";
			}
		},

		TEMPERATURE_HIGH("Temperature High") {
			@Override
			public boolean isAlertConditionMet(VehicleEvent event, AlertConfig config, boolean duration) {
				// This would need to be integrated with your temperature monitoring logic
				// Check analog inputs for temperature sensors
				try {
					JSONObject alertConfigJson = new JSONObject(config.getAlertconfig());
					if (alertConfigJson.has("tempMax")) {
						float maxTemp = Float.parseFloat(alertConfigJson.getString("tempMax"));
						// Check temperature from analog inputs (implement based on your setup)
						float currentTemp = getCurrentTemperature(event);
						return currentTemp > maxTemp;
					}
				} catch (Exception e) {
					LOGGER.error("Error checking temperature high alert", e);
				}
				return false;
			}

			@Override
			public String getAlertDescription(String plateNo, VehicleEvent event, AlertConfig config) {
				String eventTime = TimeZoneUtil.getTimeINYYYYMMddssa(event.getEventTimestamp());
				float currentTemp = getCurrentTemperature(event);
				return "Alert%0DType : Temperature High%0DPlateNo:" + plateNo + "%0DTemperature:" + (int) currentTemp
						+ "°C%0DTime:" + eventTime + "%0DLocation:" + event.getLatitude() + "," + event.getLongitude();
			}

			@Override
			public String getAdditionalInfo(AlertConfig config, VehicleEvent event) {
				float currentTemp = getCurrentTemperature(event);
				return "Temperature: " + (int) currentTemp + "°C - Above threshold";
			}

			@Override
			public String getAlertType() {
				return "TEMPHIGH";
			}
		},

		TEMPERATURE_LOW("Temperature Low") {
			@Override
			public boolean isAlertConditionMet(VehicleEvent event, AlertConfig config, boolean duration) {
				try {
					JSONObject alertConfigJson = new JSONObject(config.getAlertconfig());
					if (alertConfigJson.has("tempMin")) {
						float minTemp = Float.parseFloat(alertConfigJson.getString("tempMin"));
						float currentTemp = getCurrentTemperature(event);
						return currentTemp < minTemp;
					}
				} catch (Exception e) {
					LOGGER.error("Error checking temperature low alert", e);
				}
				return false;
			}

			@Override
			public String getAlertDescription(String plateNo, VehicleEvent event, AlertConfig config) {
				String eventTime = TimeZoneUtil.getTimeINYYYYMMddssa(event.getEventTimestamp());
				float currentTemp = getCurrentTemperature(event);
				return "Alert%0DType : Temperature Low%0DPlateNo:" + plateNo + "%0DTemperature:" + (int) currentTemp
						+ "°C%0DTime:" + eventTime + "%0DLocation:" + event.getLatitude() + "," + event.getLongitude();
			}

			@Override
			public String getAdditionalInfo(AlertConfig config, VehicleEvent event) {
				float currentTemp = getCurrentTemperature(event);
				return "Temperature: " + (int) currentTemp + "°C - Below threshold";
			}

			@Override
			public String getAlertType() {
				return "TEMPLOW";
			}
		},

		FUEL_THEFT("Fuel Theft") {
			@Override
			public boolean isAlertConditionMet(VehicleEvent event, AlertConfig config, boolean duration) {
				// Implementation would depend on fuel sensor data
				// This is a placeholder for fuel level monitoring
				if (event.getIoEvent() == null)
					return false;

				String[] ioEvent = event.getIoEvent().split(",");
				for (String io : ioEvent) {
					String[] ioValue = io.split("=");
					// Check for rapid fuel level decrease (implement based on your fuel sensor
					// setup)
					if (ioValue.length == 2 && ioValue[0].equals("fuel_level")) {
						// Add logic to detect rapid fuel decrease
						// This would require tracking previous fuel levels
						return checkFuelTheft(event, config);
					}
				}
				return false;
			}

			@Override
			public String getAlertDescription(String plateNo, VehicleEvent event, AlertConfig config) {
				String eventTime = TimeZoneUtil.getTimeINYYYYMMddssa(event.getEventTimestamp());
				return "SECURITY ALERT%0DType : Possible Fuel Theft%0DPlateNo:" + plateNo + "%0DTime:" + eventTime
						+ "%0DLocation:" + event.getLatitude() + "," + event.getLongitude()
						+ "%0DRapid Fuel Level Drop Detected!";
			}

			@Override
			public String getAlertType() {
				return "FUELTHEFT";
			}
		};

		private final String description;

		AlertType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		// Default implementations that can be overridden by specific alert types
		public boolean isAlertConditionMet(VehicleEvent event, AlertConfig config, boolean duration) {
			return false;
		}

		public String getAlertDescription(String plateNo, VehicleEvent event, AlertConfig config) {
			return "Alert: " + description;
		}

		public String getAdditionalInfo(AlertConfig config, VehicleEvent event) {
			return null;
		}

		public String getAlertType() {
			return "";
		}

		// Helper method for temperature alerts (implement based on your analog input
		// setup)
		private static float getCurrentTemperature(VehicleEvent event) {
			// Implement this method based on your temperature sensor configuration
			// This is a placeholder - you'll need to extract temperature from analog inputs
			// Example: check AI1, AI2, AI3, AI4 for temperature sensor data
			if (event.getAi1() != null) {
				// Convert analog value to temperature based on your sensor calibration
				return event.getAi1() * 0.1f; // Example conversion
			}
			return 0.0f;
		}

		// Helper method for fuel theft detection (implement based on your fuel sensor
		// setup)
		private static boolean checkFuelTheft(VehicleEvent event, AlertConfig config) {
			// Implement fuel theft detection logic
			// This would require tracking fuel level changes over time
			// Return true if rapid fuel decrease is detected
			return false; // Placeholder
		}
	}

	/**
	 * Process all alert types for a given vehicle event
	 * 
	 * @param event   The vehicle event to process
	 * @param plateNo The plate number of the vehicle
	 */
	public void processAllAlerts(VehicleEvent event, Vehicle vehicleData, List<VehicleAlerts> tempAlertsForVins) {
		// Input validation
		if (event == null || event.getVin() == null) {
			LOGGER.error("Invalid event or missing VIN for alert processing");
			return;
		}

		String plateNo = vehicleData.getPlateNo();

		List<AlertConfig> alertConfigs = alertConfigService.getAlertConfigsByVin(event.getVin());
		if (alertConfigs == null || alertConfigs.isEmpty()) {
			LOGGER.debug("No alert configurations found for VIN: " + event.getVin());
			return;
		}

		for (AlertConfig alertConfig : alertConfigs) {
			if (SUPPORTED_ALERT_TYPES.contains(alertConfig.getAlerttype())) {
				try {
					AlertType alertType = AlertType.valueOf(alertConfig.getAlerttype());
					processAlert(event, alertConfig, plateNo, tempAlertsForVins, alertType, vehicleData.getUserId());
				} catch (IllegalArgumentException e) {
					LOGGER.warn("Invalid alert type: {}", alertConfig.getAlerttype());
				}
			}
		}

		if (!tempAlertsForVins.isEmpty()) {
			LOGGER.debug("Saving alerts for VIN: " + event.getVin() + ", Alerts count: " + tempAlertsForVins.size());
			vehicleAlertService.saveAlerts(tempAlertsForVins);
		}

	}

	/**
	 * Checks if enough time has passed since the last alert to trigger a new one
	 * based on the alert config interval settings
	 */
	public boolean intervalCheck(AlertConfig alertConfig, VehicleEvent event) {
		try {
			// Get the last alert for this vehicle and alert type
			VehicleAlerts lastAlert = vehicleAlertService.getLastEventAlert(alertConfig.getVin(),
					alertConfig.getAlerttype());

			// If no previous alert exists, we should allow this alert
			if (lastAlert == null) {
				LOGGER.debug("No previous alert found for VIN: " + alertConfig.getVin() + ", Alert Type: "
						+ alertConfig.getAlerttype());
				return true;

			}

			String intervalStr = alertConfig.getRefinterval();
			String intervalType = alertConfig.getIntervaltype();

			// Parse interval value
			int interval;
			try {
				interval = Integer.parseInt(intervalStr);
			} catch (NumberFormatException e) {
				LOGGER.warn("Invalid interval format: " + intervalStr);
				return false; // Invalid interval format
			}

			// Get timestamp of the last alert
			Date lastTimestamp = lastAlert.getEventTimestamp();
			Date currentTime = event.getEventTimestamp();

			// Calculate time difference in milliseconds
			long timeDifference = currentTime.getTime() - lastTimestamp.getTime();

			// Convert to appropriate unit based on intervalType
			long minimumIntervalMs = 0;
			if ("MINUTE".equalsIgnoreCase(intervalType) || "MINUTES".equalsIgnoreCase(intervalType)) {
				minimumIntervalMs = interval * 60 * 1000; // Convert minutes to milliseconds
			} else if ("HOUR".equalsIgnoreCase(intervalType) || "HOURS".equalsIgnoreCase(intervalType)) {
				minimumIntervalMs = interval * 60 * 60 * 1000; // Convert hours to milliseconds
			}

			// Return true if enough time has passed
			return timeDifference >= minimumIntervalMs;

		} catch (Exception e) {
			LOGGER.error("Error checking interval: " + e.getMessage(), e);
			return false;
		}
	}

	public VehicleAlerts processAlert(VehicleEvent event, AlertConfig alertConfig, String plateNo,
			List<VehicleAlerts> tempAlertsForVins, AlertType alertType, String userId) {
		VehicleAlerts alert = null;
		if (event == null || alertConfig == null) {
			LOGGER.debug("Invalid input parameters for alert: " + alertType);
			return null;
		}

		LOGGER.debug("Processing " + alertType.getDescription() + " Alert");

		String vin = alertConfig.getVin();

		try {
			// Check if this event meets the alert condition
			boolean duration = false;

			if (alertType == AlertType.TOWED || alertType == AlertType.IDLE || alertType == AlertType.STOP)
				duration = isDurationReached(event, alertConfig);

			if (alertType.isAlertConditionMet(event, alertConfig, duration)) {

				alert = createAlert(alertType.getAlertType(),
						alertType.getAlertDescription(plateNo, event, alertConfig), event,
						alertConfig.getNotification(), vin, alertType.getAdditionalInfo(alertConfig, event), userId);

				if (alert != null) {
					if (intervalCheck(alertConfig, event)) {
						tempAlertsForVins.add(alert);
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error("Error processing " + alertType + " alert: " + e.getMessage(), e);
		}
		return alert;
	}

	private boolean isDurationReached(VehicleEvent event, AlertConfig alertConfig) {
		try {
			LOGGER.info("isDurationReached Vin::{}", event.getVin());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long durationSeconds = customService.getLasteventDuration(event.getVin(),
					sdf.parse(TimeZoneUtil.getDate(event.getEventTimestamp())));
			
			
			if (durationSeconds != 0) {
				JSONObject alertConfigJson = new JSONObject(alertConfig.getAlertconfig());

				long interval = Long.parseLong(alertConfigJson.getString(alertConfig.getAlerttype().toLowerCase()))
						* 60;

				boolean isDurationReached = durationSeconds >= interval;

				LOGGER.debug("Duration calculated: {} seconds, {} minutes reached: {}", durationSeconds,
						alertConfigJson.getString(alertConfig.getAlerttype().toLowerCase()), isDurationReached);

				return isDurationReached;

			}
		} catch (Exception e) {
			LOGGER.error("Error calculating duration: " + e.getMessage(), e);
		}

		return false; // If timing is null, consider duration not reached
	}

	/**
	 * Common method to create alert objects
	 */
	private VehicleAlerts createAlert(String alertType, String description, VehicleEvent event, String mobile,
			String vin, String additionalInfo, String userId) {

		VehicleAlerts va = new VehicleAlerts();
		va.setAlertType(alertType);
		va.setDescription(description);
		va.setEventTimestamp(event.getEventTimestamp());
		va.setFromDate(TimeZoneUtil.getStrDZDate(event.getEventTimestamp()));
		va.setLatLng(event.getLatitude() + "," + event.getLongitude());
		va.setSmsMobile(mobile);
		va.setVin(vin);
		va.setShowStatus(false);

		if (additionalInfo != null) {
			va.setAdditionalInfo(additionalInfo);
		}

		if (userId != null) {
			va.setUserId(userId);
		}

		return va;
	}
}