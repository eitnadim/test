package com.eit.gateway.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.cache.CacheManager;
import com.eit.gateway.entity.CompanyTrackDevice;
import com.eit.gateway.entity.Vehicle;
import com.eit.gateway.entity.VehicleAlerts;
import com.eit.gateway.entity.VehicleEvent;
import com.eit.gateway.util.TimeZoneUtil;

// Previously disabled to resolve circular dependency - now re-enabled
@Service
public class LiveTrackDataHandlerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LiveTrackDataHandlerService.class);


	@Autowired
	WebSocketService webSocketService;

	private static final String DATE_DDHHMMSS = "yyyy-MM-dd HH:mm:ss";


	private static final Collection<?> NO_TRANS = null;

	/**
	 * Retrieves real-time data for a vehicle as a JSON object
	 * 
	 * This method processes: - Vehicle IO configuration from the JSON stored in
	 * vehicle.ioConfig - Latest vehicle event data - Sensor values and states
	 * 
	 * @param vehicle            The vehicle entity containing IO configuration in
	 *                           JSON format
	 * @param vehicleEvent       The latest event data for the vehicle
	 * @param entryPoint         The entry point for the request
	 * @param userId             The ID of the requesting user
	 * @param companyTrackDevice The company track device information
	 * @return JSON string containing the vehicle IO data
	 */
	public void getVehicleIOData(Vehicle vehicle, VehicleEvent vehicleEvent, String entryPoint,
			CopyOnWriteArrayList<VehicleAlerts> tempAlertsForVins, CompanyTrackDevice companyTrackDevice) {
		JSONObject vehicleDataJson = new JSONObject();
		String vin = null;

		try {
			// Extract basic vehicle information
			vin = vehicle.getVin();

			// Initialize the vehicle JSON data with default values
			initializeVehicleJsonData(vehicleDataJson, vehicle);

			// Process IO configuration from JSON column
			processIOConfigFromJson(vehicleDataJson, vehicle, vehicleEvent);

			// Process event data if available
			if (vehicleEvent != null && vehicleEvent.getEventTimestamp() != null) {
				processEventData(vehicleDataJson, vehicle, vehicleEvent, companyTrackDevice);
			}

			

			// Set additional metadata
			setVehicleMetadata(vehicleDataJson, vehicle, entryPoint, companyTrackDevice);

		} catch (Exception e) {
			LOGGER.error("Error processing Vehicle IO data for vin = {}", vin, e);
			// Return a basic error JSON if processing fails
			vehicleDataJson.put("error", "Failed to process vehicle IO data");
			vehicleDataJson.put("vin", vin);
		}

		// Create the response structure
		JSONObject responseJson = new JSONObject();
		responseJson.put(vin, vehicleDataJson);
//		return responseJson.toString();
		webSocketService.sendMessageToWebSocket(responseJson.toString(), vin, tempAlertsForVins, vehicle.getUserId());
	}

	/**
	 * Initialize the vehicle JSON data with default values
	 */
	private void initializeVehicleJsonData(JSONObject vehicleDataJson, Vehicle vehicle) {
		// Set basic vehicle model information
		vehicleDataJson.put("vehicleModel",
				vehicle.getVehicleModelModel() == null ? "CAR" : vehicle.getVehicleModelModel());

		// Initialize sensor states
		vehicleDataJson.put("isTempSensor", false);
		vehicleDataJson.put("doorStatus", -1);
		vehicleDataJson.put("doorStatus2", -1);
		vehicleDataJson.put("fuelStatus", -1);
		vehicleDataJson.put("acStatus", -1);
		vehicleDataJson.put("seatbeltStatus", -1);

		// Initialize empty CAN data object
		vehicleDataJson.put("canData", new JSONObject());
	}

	/**
	 * Process IO configuration from the JSON column in vehicle table
	 */
	private void processIOConfigFromJson(JSONObject vehicleDataJson, Vehicle vehicle, VehicleEvent vehicleEvent) {
		try {
			// Get IO config from JSON column
			String ioConfigJson = vehicle.getIoConfig();
			if (ioConfigJson == null || ioConfigJson.isEmpty()) {
				return;
			}

			JSONObject ioConfig = new JSONObject(ioConfigJson);
			JSONArray ioDevices = ioConfig.optJSONArray("devices");

			if (ioDevices == null) {
				return;
			}

			// Process each IO device configuration
			for (int i = 0; i < ioDevices.length(); i++) {
				JSONObject device = ioDevices.getJSONObject(i);
				int ioId = device.optInt("ioId", -1);
				String ioName = device.optString("name", "");
				String deviceType = device.optString("type", "");

				processIODevice(vehicleDataJson, vehicle, vehicleEvent, ioId, ioName, deviceType, device);
			}

			// Process vehicle tags if available
			processVehicleTags(vehicleDataJson, vehicle);

		} catch (Exception e) {
			LOGGER.error("Error processing IO config JSON for vehicle {}", vehicle.getVin(), e);
		}
	}

	/**
	 * Process a single IO device configuration
	 */
	private void processIODevice(JSONObject vehicleDataJson, Vehicle vehicle, VehicleEvent vehicleEvent, int ioId,
			String ioName, String deviceType, JSONObject deviceConfig) {

		// Process temperature sensors
		if (deviceType.equalsIgnoreCase("temperature")) {
			processTemperatureSensor(vehicleDataJson, ioId, deviceConfig);
		}
		// Process door sensors
		else if (ioName.equalsIgnoreCase("Door Sensor")) {
			int value = getIOValue(vehicle, ioId);
			vehicleDataJson.put("doorStatus", value);
		} else if (ioName.equalsIgnoreCase("Door Sensor2")) {
			int value = getIOValue(vehicle, ioId);
			vehicleDataJson.put("doorStatus2", value);
		}
		// Process AC status
		else if (ioName.equalsIgnoreCase("Air Conditioner")) {
			int value = getIOValue(vehicle, ioId);
			vehicleDataJson.put("acStatus", value);
		}
		// Process fuel sensor
		else if (ioName.equalsIgnoreCase("Fuel Sensor")) {
			int value = getIOValue(vehicle, ioId);
			vehicleDataJson.put("fuelStatus", value);
		}
		// Process seatbelt
		else if (ioName.equalsIgnoreCase("SeatBelt")) {
			int value = getIOValue(vehicle, ioId);
			vehicleDataJson.put("seatbeltStatus", value);
		}
		// Process Bluetooth temperature sensors
		else if (deviceType.equalsIgnoreCase("bluetooth_temp")) {
			processBluetoothTemperatureSensor(vehicleDataJson, ioId, deviceConfig);
		}
	}

	/**
	 * Get IO value from the vehicle based on IO ID
	 */
	private int getIOValue(Vehicle vehicle, int ioId) {
		int value = -1;
		switch (ioId) {
		case 1:
			value = vehicle.getVDi1() == null ? -1 : vehicle.getVDi1();
			break;
		case 2:
			value = vehicle.getVDi2() == null ? -1 : vehicle.getVDi2();
			break;
		case 3:
			value = vehicle.getVDi3() == null ? -1 : vehicle.getVDi3();
			break;
		case 4:
			value = vehicle.getVDi4() == null ? -1 : vehicle.getVDi4();
			break;
		case 9:
			value = vehicle.getVAi1() == null ? -1 : vehicle.getVAi1();
			break;
		case 10:
			value = vehicle.getVAi2() == null ? -1 : vehicle.getVAi2();
			break;
		case 11:
			value = vehicle.getVAi3() == null ? -1 : vehicle.getVAi3();
			break;
		case 19:
			value = vehicle.getVAi4() == null ? -1 : vehicle.getVAi4();
			break;
		}
		return value;
	}

	/**
	 * Process temperature sensor configuration
	 */
	private void processTemperatureSensor(JSONObject vehicleDataJson, int ioId, JSONObject deviceConfig) {
		vehicleDataJson.put("isTempSensor", true);

		String deviceName = deviceConfig.optString("deviceName", "---");
		String min = deviceConfig.optString("min", "0");
		String max = deviceConfig.optString("max", "0");

		if (ioId == 76) {
			vehicleDataJson.put("temperature4Name", deviceName);
			vehicleDataJson.put("temp4Min", min);
			vehicleDataJson.put("temp4Max", max);
		} else if (ioId == 77) {
			vehicleDataJson.put("temperature1Name", deviceName);
			vehicleDataJson.put("temp1Min", min);
			vehicleDataJson.put("temp1Max", max);
		} else if (ioId == 78) {
			vehicleDataJson.put("temperature2Name", deviceName);
			vehicleDataJson.put("temp2Min", min);
			vehicleDataJson.put("temp2Max", max);
		} else if (ioId == 79) {
			vehicleDataJson.put("temperature3Name", deviceName);
			vehicleDataJson.put("temp3Min", min);
			vehicleDataJson.put("temp3Max", max);
		}
	}

	/**
	 * Process Bluetooth temperature sensor configuration
	 */
	private void processBluetoothTemperatureSensor(JSONObject vehicleDataJson, int ioId, JSONObject deviceConfig) {
		vehicleDataJson.put("isTempSensor", true);

		String deviceName = deviceConfig.optString("deviceName", "---");

		if (ioId == 25) {
			vehicleDataJson.put("blueTempName1", deviceName);
			vehicleDataJson.put("blueTemp1", "");
			vehicleDataJson.put("blueHumidity1", "");
		} else if (ioId == 26) {
			vehicleDataJson.put("blueTempName2", deviceName);
			vehicleDataJson.put("blueTemp2", "");
			vehicleDataJson.put("blueHumidity2", "");
		} else if (ioId == 27) {
			vehicleDataJson.put("blueTempName3", deviceName);
			vehicleDataJson.put("blueTemp3", "");
			vehicleDataJson.put("blueHumidity3", "");
		} else if (ioId == 28) {
			vehicleDataJson.put("blueTempName4", deviceName);
			vehicleDataJson.put("blueTemp4", "");
			vehicleDataJson.put("blueHumidity4", "");
		}
	}

	/**
	 * Process vehicle tags from the JSON in vehicle
	 */
	private void processVehicleTags(JSONObject vehicleDataJson, Vehicle vehicle) {
		String vTags = vehicle.getVTags();
		vehicleDataJson.put("v_tags", "---");

		if (vTags != null && vTags.startsWith("{")) {
			try {
				JSONObject tagsJson = new JSONObject(vTags);

				// Set tags info
				if (tagsJson.has("Satellites")) {
					vehicleDataJson.put("v_tags", tagsJson.getString("Satellites"));
				}

				// Set weight info
				if (tagsJson.has("loadWeight") || tagsJson.has("totalWeight")) {
					if (tagsJson.has("totalWeight")) {
						vehicleDataJson.put("totalWeight", tagsJson.getString("totalWeight"));
					}
					if (tagsJson.has("loadWeight")) {
						vehicleDataJson.put("loadWeight", tagsJson.getString("loadWeight"));
					}
				}

				// Process Bluetooth temperature data if exists
				processBluetoothTemperatureData(vehicleDataJson, tagsJson);

			} catch (Exception e) {
				LOGGER.error("Error processing vehicle tags for vehicle {}", vehicle.getVin(), e);
			}
		}
	}

	/**
	 * Process bluetooth temperature data from tags JSON
	 */
	private void processBluetoothTemperatureData(JSONObject vehicleDataJson, JSONObject tagsJson) {
		if (tagsJson.has("BLEtemp1")) {
			vehicleDataJson.put("blueTemp1", tagsJson.getString("BLEtemp1"));
			vehicleDataJson.put("blueHumidity1", tagsJson.getString("BLEHumidity1"));
		}

		if (tagsJson.has("BLEtemp2")) {
			vehicleDataJson.put("blueTemp2", tagsJson.getString("BLEtemp2"));
			vehicleDataJson.put("blueHumidity2", tagsJson.getString("BLEHumidity2"));
		}

		if (tagsJson.has("BLEtemp3")) {
			vehicleDataJson.put("blueTemp3", tagsJson.getString("BLEtemp3"));
			vehicleDataJson.put("blueHumidity3", tagsJson.getString("BLEHumidity3"));
		}

		if (tagsJson.has("BLEtemp4")) {
			vehicleDataJson.put("blueTemp4", tagsJson.getString("BLEtemp4"));
			vehicleDataJson.put("blueHumidity4", tagsJson.getString("BLEHumidity4"));
		}

		// For custom temperature overrides
		if (tagsJson.has("TEMP1")) {
			vehicleDataJson.put("temperature1Val", tagsJson.getString("TEMP1"));
		}
	}

	/**
	 * Process event data including timestamp, status, speed, etc.
	 */
	private void processEventData(JSONObject vehicleDataJson, Vehicle vehicle, VehicleEvent vehicleEvent,
			CompanyTrackDevice companyTrackDevice) throws ParseException {
		SimpleDateFormat sdfTime = new SimpleDateFormat(DATE_DDHHMMSS);

		// Get current time in the appropriate timezone
		Date today = TimeZoneUtil.getDateInTimeZoneforSKT(new Date(), "Asia/Riyadh");
		long todayInSec = today.getTime() / 1000;

		// Get transmission threshold from cache
		long transmissionThreshold = 899000;
//				Long.valueOf(AppSettingsCache.getPreference(NO_TRANS, vehicle.getCompanyId()));

		// Parse event timestamp
		Date eventTimeStamp = sdfTime.parse(TimeZoneUtil.getTimeINYYYYMMddss(vehicleEvent.getEventTimestamp()));
		long eventTimeStampInSec = eventTimeStamp.getTime() / 1000;
		long differenceInSec = todayInSec - eventTimeStampInSec;

		// Set speed and status
		int speed = vehicleEvent.getSpeed() == null ? 0 : vehicleEvent.getSpeed();
		vehicleDataJson.put("speed", speed);

		String status = vehicle.getEventStatus() == null ? "" : vehicle.getEventStatus();
		if (transmissionThreshold < differenceInSec) {
			vehicleDataJson.put("speed", 0);
			vehicleDataJson.put("status", NO_TRANS);
		} else {
			vehicleDataJson.put("status", status);
		}

		// Set basic vehicle information
		vehicleDataJson.put("runDuration", "1");
		vehicleDataJson.put("stopDuration", "1");
		vehicleDataJson.put("idleStop", "1");
		vehicleDataJson.put("toDate", "1");
		vehicleDataJson.put("overSpeedVio", "1");
		vehicleDataJson.put("noOfStops", "1");
		vehicleDataJson.put("vin", vehicle.getVin());
		vehicleDataJson.put("plateNo", vehicle.getPlateNo() == null ? "---" : vehicle.getPlateNo());
		vehicleDataJson.put("timeStamp", TimeZoneUtil.getStrTZ(vehicleEvent.getEventTimestamp()));
		vehicleDataJson.put("lastUpdDate", TimeZoneUtil.getStrTZ(vehicleEvent.getEventTimestamp()));
		vehicleDataJson.put("endDate", TimeZoneUtil.getStrTZ(vehicleEvent.getServerTimestamp()));

		// Set location data
		vehicleDataJson.put("latitude", vehicleEvent.getLatitude() == null ? 0.0 : vehicleEvent.getLatitude());
		vehicleDataJson.put("longitude", vehicleEvent.getLongitude() == null ? 0.0 : vehicleEvent.getLongitude());

		// Set engine status
		vehicleDataJson.put("engineId",
				String.valueOf(vehicleEvent.getEngine() == null ? false : vehicleEvent.getEngine()));

		// Set other event data
		vehicleDataJson.put("subject", vehicleEvent.getIoEvent() == null ? "" : vehicleEvent.getIoEvent());
		vehicleDataJson.put("batteryVoltage",
				String.valueOf(vehicleEvent.getBattery() == null ? 0L : vehicleEvent.getBattery()));

		// Set temperature sensor values
		setTemperatureSensorValues(vehicleDataJson, vehicleEvent);

		// Set odometer values
		setOdometerValues(vehicleDataJson, vehicle, companyTrackDevice);

		// Set engine hours
		setEngineHours(vehicleDataJson, vehicle);

		// Process analog inputs
		processAnalogInputs(vehicleDataJson, vehicle);

		// Process digital inputs
		processDigitalInputs(vehicleDataJson, vehicle);

		// Set direction and lock status
		vehicleDataJson.put("direction", vehicle.getVDirection() == null ? 0 : vehicle.getVDirection());
		vehicleDataJson.put("lockStatus", vehicle.getLockStatus() == null ? 0 : vehicle.getLockStatus());

		// Process I/O events if available
		if (vehicle.getVIoEvent() != null) {
			processIOEvents(vehicleDataJson, vehicle, vehicleEvent, eventTimeStamp, companyTrackDevice);
		} else {
			// Set basic device info if no I/O events
			if (vehicle.getVEventTimestamp() != null && vehicle.getVTimestamp() != null) {
				vehicleDataJson.put("gps", vehicle.getVGps() != null && vehicle.getVGps().equalsIgnoreCase("ON"));
				vehicleDataJson.put("gsmSignalStrength",
						String.valueOf(vehicle.getVGsm() == null ? 0 : vehicle.getVGsm()));
				vehicleDataJson.put("batteryVoltage",
						vehicle.getVBatteryVoltage() == null ? "Low" : vehicle.getVBatteryVoltage());
				vehicleDataJson.put("powerSupplyVoltage",
						vehicle.getVPowerSupply() == null ? "OFF" : vehicle.getVPowerSupply());
			}
		}

		// Set additional vehicle metadata
		vehicleDataJson.put("contractor", vehicle.getContractor());
		vehicleDataJson.put("vehicleStatus", vehicle.getVehicleStatus() != null ? vehicle.getVehicleStatus() : "{}");
		vehicleDataJson.put("vehicleStatusType",
				vehicle.getVehicleStatusType() != null ? vehicle.getVehicleStatusType() : "working");

		// Set time difference
		String timediff = vehicle.getStatusDuration() != null
				? formatIntoHHMMWithOutDay(Integer.parseInt(String.valueOf(vehicle.getStatusDuration())))
				: "00:00";
		vehicleDataJson.put("timeDifference", timediff);
	}

	/**
	 * Set temperature sensor values from event data
	 */
	private void setTemperatureSensorValues(JSONObject vehicleDataJson, VehicleEvent vehicleEvent) {
		// Set temperature sensor values if available
		if (vehicleEvent.getTempSensor1() != null) {
			vehicleDataJson.put("temperature1Val", String.valueOf(vehicleEvent.getTempSensor1()));
			vehicleDataJson.put("temp1Max", String.valueOf(vehicleEvent.getTempSensor1()));
		}

		if (vehicleEvent.getTempSensor2() != null) {
			vehicleDataJson.put("temperature2Val", String.valueOf(vehicleEvent.getTempSensor2()));
			vehicleDataJson.put("temp2Max", String.valueOf(vehicleEvent.getTempSensor2()));
		}

		if (vehicleEvent.getTempSensor3() != null) {
			vehicleDataJson.put("temperature3Val", String.valueOf(vehicleEvent.getTempSensor3()));
			vehicleDataJson.put("temp3Max", String.valueOf(vehicleEvent.getTempSensor3()));
		}

		if (vehicleEvent.getTempSensor4() != null) {
			vehicleDataJson.put("temperature4Val", String.valueOf(vehicleEvent.getTempSensor4()));
			vehicleDataJson.put("temp4Max", String.valueOf(vehicleEvent.getTempSensor4()));
		}
	}

	/**
	 * Set odometer values in vehicle data
	 */
	private void setOdometerValues(JSONObject vehicleDataJson, Vehicle vehicle, CompanyTrackDevice companyTrackDevice) {
		long odometerValue = vehicle.getTodayOdometer() != null ? vehicle.getTodayOdometer() : 0;
		vehicleDataJson.put("odometer", String.valueOf(odometerValue));
		String manufacturerName = companyTrackDevice.getManufacturerName();
		if (manufacturerName != null 
				&& CacheManager.getPreference("odometerInDeviceRawdata", "demo").contains(manufacturerName)) {
			long totalOdometer = vehicle.getVOdometer() != null ? vehicle.getVOdometer() : 0;
			vehicleDataJson.put("finalOdometer",totalOdometer);
		} else {
			long finalOdometer = vehicle.getVOdometer() != null ? vehicle.getVOdometer() : 0;
			vehicleDataJson.put("finalOdometer", (finalOdometer / 1000));
			
		}
	}

	/**
	 * Set engine hours in vehicle data
	 */
	private void setEngineHours(JSONObject vehicleDataJson, Vehicle vehicle) {
		long engineHrs = vehicle.getEngineHours() != null ? vehicle.getEngineHours() : 0;
		String engineHour = formatIntoHHMMSSWithOutDay((int) engineHrs);
		vehicleDataJson.put("engineHours", engineHour);
	}

	/**
	 * Process analog inputs
	 */
	private void processAnalogInputs(JSONObject vehicleDataJson, Vehicle vehicle) {
		float f = 0;

		if (vehicle.getVAi1() != null && !String.valueOf(vehicle.getVAi1()).equalsIgnoreCase("null")) {
			vehicleDataJson.put("aimv1", vehicle.getVAi1());
			vehicleDataJson.put("analogInput1", f + "");

			if (vehicle.getIconUrl() != null && vehicle.getIconUrl().equalsIgnoreCase("BATTERY")) {
				int voltage = vehicle.getVAi1();
				double percentage = 0.0;
				if (voltage >= 15000) {
					percentage = 100.0;
				} else if (voltage > 10000) {
					percentage = (((double) (voltage - 10000) / (15000 - 10000)) * 100);
				}
				double formattedAi1 = voltage / 1000.0;
				String formattedValue = String.format("%.3f", formattedAi1);

				String formattedPercentage = String.format("%.1f", percentage);
				vehicleDataJson.put("externalBatteryPercentage", formattedPercentage + "%");
				vehicleDataJson.put("btryVoltValue", formattedValue + " V");
			}
		}

		if (vehicle.getVAi2() != null && !String.valueOf(vehicle.getVAi2()).equalsIgnoreCase("null")) {
			vehicleDataJson.put("aimv2", vehicle.getVAi2());
			vehicleDataJson.put("analogInput2", f + "");
		}

		if (vehicle.getVAi3() != null && !String.valueOf(vehicle.getVAi3()).equalsIgnoreCase("null")) {
			vehicleDataJson.put("aimv3", vehicle.getVAi3());
			vehicleDataJson.put("analogInput3", f + "");
		}

		if (vehicle.getVAi4() != null && !String.valueOf(vehicle.getVAi4()).equalsIgnoreCase("null")) {
			vehicleDataJson.put("aimv4", vehicle.getVAi4());
			vehicleDataJson.put("analogInput4", f + "");
		}
	}

	/**
	 * Process digital inputs
	 */
	private void processDigitalInputs(JSONObject vehicleDataJson, Vehicle vehicle) {
		if (vehicle.getVDi1() != null && !String.valueOf(vehicle.getVDi1()).equalsIgnoreCase("null")) {
			vehicleDataJson.put("digitalInput1", (vehicle.getVDi1() == 1) ? "ON" : "OFF");
		}

		if (vehicle.getVDi2() != null && !String.valueOf(vehicle.getVDi2()).equalsIgnoreCase("null")) {
			vehicleDataJson.put("digitalInput2", (vehicle.getVDi2() == 1) ? "ON" : "OFF");
		}

		if (vehicle.getVDi3() != null && !String.valueOf(vehicle.getVDi3()).equalsIgnoreCase("null")) {
			vehicleDataJson.put("digitalInput3", (vehicle.getVDi3() == 1) ? "ON" : "OFF");
		}

		if (vehicle.getVDi4() != null && !String.valueOf(vehicle.getVDi4()).equalsIgnoreCase("null")) {
			vehicleDataJson.put("digitalInput4", (vehicle.getVDi4() == 1) ? "ON" : "OFF");
		}
	}

	/**
	 * Process IO events from vehicle data
	 */
	private void processIOEvents(JSONObject vehicleDataJson, Vehicle vehicle, VehicleEvent vehicleEvent,
			Date eventTimeStamp, CompanyTrackDevice companyTrackDevice) throws ParseException {
		String deviceManufacturer = companyTrackDevice.getManufacturerName();
		String ioEvent = vehicle.getVIoEvent();

		if (ioEvent == null || ioEvent.isEmpty()) {
			return;
		}

		JSONObject canDataJson = new JSONObject();

		if (deviceManufacturer != null && deviceManufacturer.equalsIgnoreCase("GE")) {
			processGEIOEvents(vehicleDataJson, ioEvent);
		} else if (deviceManufacturer != null && deviceManufacturer.equalsIgnoreCase("queclink")) {
			processQueclinkIOEvents(vehicleDataJson, ioEvent);
		} else {
			processStandardIOEvents(vehicleDataJson, ioEvent, canDataJson, vehicle, vehicleEvent, eventTimeStamp);
		}

		// Set the CAN data in the vehicle data JSON
		if (canDataJson.length() > 0) {
			vehicleDataJson.put("canData", canDataJson);
		}
	}

	/**
	 * Process GE manufacturer specific IO events
	 */
	private void processGEIOEvents(JSONObject vehicleDataJson, String ioEvent) {
		String[] ioEvents = ioEvent.split(",");
		for (String ioEventArr : ioEvents) {
			String[] ioEventPair = ioEventArr.split("=");
			if (ioEventPair.length != 2)
				continue;

			String key = ioEventPair[0];
			String value = ioEventPair[1];

			if (key.equalsIgnoreCase("4")) {
				vehicleDataJson.put("gps", value != null && value.equalsIgnoreCase("A"));
			} else if (key.equalsIgnoreCase("5")) {
				vehicleDataJson.put("gsmSignalStrength", value == null ? "0" : value);
			} else if (key.equalsIgnoreCase("601")) {
				vehicleDataJson.put("powerSupplyVoltage",
						value == null ? "OFF" : value.equalsIgnoreCase("1") ? "ON" : "OFF");
			} else if (key.equalsIgnoreCase("30")) {
				if (value != null) {
					int intValue = Integer.parseInt(value);
					if (intValue >= 4000)
						vehicleDataJson.put("batteryVoltage", "Very High");
					else if (intValue >= 3800)
						vehicleDataJson.put("batteryVoltage", "High");
					else if (intValue >= 3600)
						vehicleDataJson.put("batteryVoltage", "Medium");
					else
						vehicleDataJson.put("batteryVoltage", "Low");
				} else {
					vehicleDataJson.put("batteryVoltage", "Low");
				}
			}
		}
	}

	/**
	 * Process Queclink manufacturer specific IO events
	 */
	private void processQueclinkIOEvents(JSONObject vehicleDataJson, String ioEvent) {
		String[] ioEvents = ioEvent.split(",");
		for (String ioEventArr : ioEvents) {
			String[] ioEventPair = ioEventArr.split("=");
			if (ioEventPair.length != 2)
				continue;

			String key = ioEventPair[0];
			String value = ioEventPair[1];

			if (key.equalsIgnoreCase("6")) {
				LOGGER.debug("BatteryVoltage Update: {}", value);
				vehicleDataJson.put("externalBatteryPercentage",
						String.format("%.2f", Double.parseDouble(value)) + "V - 0%");
			}
		}
	}

	/**
	 * Process standard IO events (default case)
	 */
	private void processStandardIOEvents(JSONObject vehicleDataJson, String ioEvent, JSONObject canDataJson,
			Vehicle vehicle, VehicleEvent vehicleEvent, Date eventTimeStamp) throws ParseException {

		SimpleDateFormat sdfTime = new SimpleDateFormat(DATE_DDHHMMSS);

		String[] ioEvents = ioEvent.split(",");
		for (String ioEventArr : ioEvents) {
			String[] ioEventPair = ioEventArr.split("=");
			if (ioEventPair.length != 2)
				continue;

			String key = ioEventPair[0];
			String value = ioEventPair[1];

			switch (key) {
			// OBD Data
			case "98":
				vehicleDataJson.put("obdFuelLevel", value == null ? 0 : Integer.parseInt(value));
				break;
			case "179":
				vehicleDataJson.put("lockStatus", Integer.parseInt(value));
				break;
			case "97":
				vehicleDataJson.put("obdAmbientTemp", value == null ? 0 : Integer.parseInt(value));
				break;
			case "201":
				vehicleDataJson.put("fuelLtr", value == null ? "" : value);
				break;
			case "102":
				vehicleDataJson.put("obdDistance", value == null ? 0 : Integer.parseInt(value));
				break;
			case "96":
				vehicleDataJson.put("obdECT", value == null ? 0 : Integer.parseInt(value));
				break;
			case "100":
				vehicleDataJson.put("obdEFR", value == null ? 0 : Float.parseFloat(value));
				break;
			case "94":
				vehicleDataJson.put("obdRPM", value == null ? 0 : Float.parseFloat(value));
				break;
			case "95":
				vehicleDataJson.put("obdVehicleSpeed", value == null ? 0 : Integer.parseInt(value));
				break;

			// Signal Strength
			case "27":
			case "21":
				processSignalStrength(vehicleDataJson, key, value);
				break;

			// Power Supply
			case "29":
				if (value != null && Integer.parseInt(value) > 6000)
					vehicleDataJson.put("powerSupplyVoltage", "ON");
				else
					vehicleDataJson.put("powerSupplyVoltage", "OFF");
				break;

			case "66":
				processPowerSupply(vehicleDataJson, value);
				break;

			case "252":
				if (value != null && value.equalsIgnoreCase("1"))
					vehicleDataJson.put("powerSupplyVoltage", "ON");
				else
					vehicleDataJson.put("powerSupplyVoltage", "OFF");
				break;

			// Battery Voltage
			case "30":
			case "67":
				processBatteryVoltage(vehicleDataJson, key, value);
				break;

			// GPS Status
			case "176":
			case "69":
				processGpsStatus(vehicleDataJson, value);
				break;

			// CAN Data
			case "85":
				canDataJson.put("rpm", value);
				break;
			case "81":
				canDataJson.put("currentSpeed", value);
				break;
			case "135":
				canDataJson.put("wheelSpeed", value);
				break;
			case "84":
				canDataJson.put("fuelLitre", value);
				break;
			case "89":
				canDataJson.put("fuelPercentage", value);
				break;
			case "103":
				canDataJson.put("engineHours", String.valueOf(Integer.parseInt(value) / 60));
				break;
			case "87":
				canDataJson.put("totDistance", String.valueOf(Integer.parseInt(value) / 1000));
				break;
			case "107":
				canDataJson.put("fuelConsumed", String.valueOf(Integer.parseInt(value) / 10));
				break;
			case "82":
				canDataJson.put("pedalPosition", value);
				break;
			case "235":
				canDataJson.put("oilPressure", value);
				break;
			case "112":
				canDataJson.put("adBlueLevel", String.valueOf(Integer.parseInt(value) / 10));
				break;
			case "132":
				processSecurityState(canDataJson, value);
				break;
			case "hbd":
				processHeartbeatData(vehicleDataJson, value, eventTimeStamp, sdfTime, vehicle);
				break;
			}
		}
	}

	/**
	 * Process signal strength information
	 */
	private void processSignalStrength(JSONObject vehicleDataJson, String key, String value) {
		if (key.equalsIgnoreCase("21")) {
			if (value != null) {
				vehicleDataJson.put("gsmSignalStrength", String.valueOf(Integer.parseInt(value)));
			} else {
				vehicleDataJson.put("gsmSignalStrength", "0");
			}
		} else { // key is "27"
			if (value != null && Integer.parseInt(value) != 100 && Integer.parseInt(value) != 255) {
				vehicleDataJson.put("gsmSignalStrength", String.valueOf(Integer.parseInt(value) / 6));
			} else {
				vehicleDataJson.put("gsmSignalStrength", "0");
			}
		}
	}

	/**
	 * Process power supply information
	 */
	private void processPowerSupply(JSONObject vehicleDataJson, String value) {
		if (value != null) {
			double voltage = Integer.parseInt(value) * 0.001;
			vehicleDataJson.put("powerSupplyVoltage", String.format("%.2f", voltage));
		} else {
			vehicleDataJson.put("powerSupplyVoltage", "OFF");
		}
	}

	/**
	 * Process battery voltage information
	 */
	private void processBatteryVoltage(JSONObject vehicleDataJson, String key, String value) {
		if (key.equalsIgnoreCase("67")) {
			if (value != null) {
				int intValue = Integer.parseInt(value);
				if (intValue > 12000)
					vehicleDataJson.put("batteryVoltage", "Very High");
				else if (intValue > 10000)
					vehicleDataJson.put("batteryVoltage", "High");
				else if (intValue > 8000)
					vehicleDataJson.put("batteryVoltage", "Medium");
				else
					vehicleDataJson.put("batteryVoltage", "Low");
			} else {
				vehicleDataJson.put("batteryVoltage", "Low");
			}
		} else { // key is "30"
			if (value != null) {
				int intValue = Integer.parseInt(value);
				if (intValue >= 4000)
					vehicleDataJson.put("batteryVoltage", "Very High");
				else if (intValue >= 3800)
					vehicleDataJson.put("batteryVoltage", "High");
				else if (intValue >= 3600)
					vehicleDataJson.put("batteryVoltage", "Medium");
				else
					vehicleDataJson.put("batteryVoltage", "Low");
			} else {
				vehicleDataJson.put("batteryVoltage", "Low");
			}
		}
	}

	/**
	 * Process GPS status information
	 */
	private void processGpsStatus(JSONObject vehicleDataJson, String value) {
		if (value != null && !(value.equalsIgnoreCase("3")) && !(value.equalsIgnoreCase("4"))
				|| value.equalsIgnoreCase("1")) {
			vehicleDataJson.put("gps", true);
		} else {
			vehicleDataJson.put("gps", false);
		}
	}

	/**
	 * Process security state information from CAN data
	 */
	private void processSecurityState(JSONObject canDataJson, String value) {
		String[] securityState = value.split(";");
		for (String statePair : securityState) {
			String[] stateKeyValue = statePair.split(":");
			if (stateKeyValue.length == 2) {
				canDataJson.put(stateKeyValue[0], stateKeyValue[1]);
			}
		}
	}

	/**
	 * Process heartbeat data
	 */
	private void processHeartbeatData(JSONObject vehicleDataJson, String value, Date eventTimeStamp,
			SimpleDateFormat sdfTime, Vehicle vehicle) throws ParseException {

		String[] hbio = value.split(";");
		if (hbio.length < 3)
			return;

		Date hbdeventTimeStamp = sdfTime.parse(hbio[2]);

		if (hbio.length > 5) {
			vehicleDataJson.put("gps", hbio[1].equalsIgnoreCase("ON"));
			vehicleDataJson.put("gsmSignalStrength", hbio[3]);
			vehicleDataJson.put("batteryVoltage", hbio[5]);
			vehicleDataJson.put("powerSupplyVoltage", hbio[6]);
		}

		long diffSeconds = (hbdeventTimeStamp.getTime() - eventTimeStamp.getTime()) / 1000;
		if (hbdeventTimeStamp.after(eventTimeStamp) && diffSeconds > 120) {
			vehicleDataJson.put("timeStamp", TimeZoneUtil.getStrTZ(hbdeventTimeStamp));

			// Determine status based on heartbeat
			if (hbio.length > 4) {
				if (hbio[4].equalsIgnoreCase("true")) {
					vehicleDataJson.put("status", "Idle");
				} else {
					vehicleDataJson.put("status", "Stop");
				}
			}

			vehicleDataJson.put("speed", 0);
		}
	}

	/**
	 * Process fuel data for vehicles (especially generators)
	 */

	/**
	 * Set additional vehicle metadata
	 */
	private void setVehicleMetadata(JSONObject vehicleDataJson, Vehicle vehicle, String entryPoint,
			CompanyTrackDevice companyTrackDevice) {
		// Set icon and device info
		vehicleDataJson.put("icon", vehicle.getIconUrl() == null ? "" : vehicle.getIconUrl());
		vehicleDataJson.put("imeiNo", vehicle.getVehicleDeviceImei() == null ? "" : vehicle.getVehicleDeviceImei());
		vehicleDataJson.put("make",
				companyTrackDevice.getManufacturerName() == null ? "" : companyTrackDevice.getManufacturerName());
		vehicleDataJson.put("model", companyTrackDevice.getModel() == null ? "" : companyTrackDevice.getModel());

		// Set watch mode and operator
		vehicleDataJson.put("watchmode", vehicle.getWatchMode() == null ? 0 : vehicle.getWatchMode());
		vehicleDataJson.put("operatorName", vehicle.getOperatorName() == null ? "" : vehicle.getOperatorName());

		// Set group
		vehicleDataJson.put("group", vehicle.getVehicleGroup() == null ? "" : vehicle.getVehicleGroup());

		// Process asset information if available
		processAssetInformation(vehicleDataJson, vehicle);

		// Process employee details if WFT entry point
		if (entryPoint != null && entryPoint.equalsIgnoreCase("WFT")) {
//			processEmployeeDetails(vehicleDataJson, vehicle);
		}

		// Set contact and serial information
		vehicleDataJson.put("contactNo", vehicle.getContactNo() == null ? "" : vehicle.getContactNo());

		if (vehicle.getIoDetails() != null) {
			String[] ioDetails = vehicle.getIoDetails().split("\\*");
			vehicleDataJson.put("notes", ioDetails[0]);
			if (ioDetails.length > 1) {
				vehicleDataJson.put("serialNo", ioDetails[1]);
			} else {
				vehicleDataJson.put("serialNo", "");
			}
		} else {
			vehicleDataJson.put("notes", "");
			vehicleDataJson.put("serialNo", "");
		}

		// Set temperature sensor averages
		if (vehicle.getVTempSensor1() != null) {
			vehicleDataJson.put("temp1Avg", vehicle.getVTempSensor1().toString());
		} else {
			vehicleDataJson.put("temp1Avg", "");
		}

		if (vehicle.getTemp1LastTransmission() != null) {
			vehicleDataJson.put("temp1LastTransmission", TimeZoneUtil.getStrTZ(vehicle.getTemp1LastTransmission()));
		} else {
			vehicleDataJson.put("temp1LastTransmission", "");
		}

		// Check asset information for warranty expiry
		if (vehicle.getAssetInformation() != null) {
			try {
				JSONObject assetInfo = new JSONObject(vehicle.getAssetInformation());
				if (assetInfo.has("subscribe")) {
					vehicleDataJson.put("warrantyExpiry", Boolean.parseBoolean(assetInfo.getString("subscribe")));
				}
			} catch (Exception e) {
				LOGGER.error("Error processing asset information JSON", e);
			}
		}

		// Handle employee-type vehicles differently
		if (vehicle.getVehicleTypeVehicleType() != null
				&& vehicle.getVehicleTypeVehicleType().equalsIgnoreCase("Employee")) {
			vehicleDataJson.put("gps", true);
			vehicleDataJson.put("gsmSignalStrength", "5");
		}

		// Set SIM card number
		vehicleDataJson.put("simcardNumber",
				companyTrackDevice.getSimNo() == null ? "" : companyTrackDevice.getSimNo());
	}

	/**
	 * Process asset information if available
	 */
	private void processAssetInformation(JSONObject vehicleDataJson, Vehicle vehicle) {
		if (vehicle.getAssetInformation() == null) {
			vehicleDataJson.put("assetName", "");
			return;
		}

		try {
			JSONObject assetInfo = new JSONObject(vehicle.getAssetInformation());
			if (assetInfo.has("vName")) {
				vehicleDataJson.put("assetName", assetInfo.getString("vName"));
			} else {
				vehicleDataJson.put("assetName", "");
			}
		} catch (Exception e) {
			LOGGER.error("Error processing asset information", e);
			vehicleDataJson.put("assetName", "");
		}
	}

	/**
	 * Process employee details for WFT entry point
	 */
//	private void processEmployeeDetails(JSONObject vehicleDataJson, Vehicle vehicle) {
//		JSONObject empDetails = new JSONObject();
//
//		// Get work zone data
//		try {
//			String workZoneQuery = "SELECT STRING_AGG(DISTINCT vhf.freeformid || '#' || f.geoname, ',') AS concatenated_freeform "
//					+ "FROM vehicle v " + "LEFT JOIN vehicle_has_freeform vhf ON vhf.vin=v.vin "
//					+ "LEFT JOIN freeformgeo f ON vhf.freeformid=f.id " + "WHERE v.status IS NULL AND v.vin='"
//					+ vehicle.getVin() + "' " + "GROUP BY v.vin";
//			Query query = em.createNativeQuery(workZoneQuery);
//			Object workZoneResult = query.getSingleResult();
//
//			empDetails.put("workZone", workZoneResult != null ? workZoneResult.toString() : "");
//		} catch (Exception e) {
//			LOGGER.debug("No work zone data found for vehicle {}", vehicle.getVin());
//			empDetails.put("workZone", "");
//		}
//
//		// Get supervisor data
//		try {
//			String supervisorQuery = "SELECT STRING_AGG(DISTINCT vhu.userId, ',') AS concatenated_user_ids "
//					+ "FROM vehicle v " + "LEFT JOIN vehicle_has_user vhu ON vhu.vin=v.vin "
//					+ "WHERE v.status IS NULL AND v.vin='" + vehicle.getVin() + "' " + "GROUP BY v.vin";
//			Query query = em.createNativeQuery(supervisorQuery);
//			Object supervisorResult = query.getSingleResult();
//
//			empDetails.put("supervisor", supervisorResult != null ? supervisorResult.toString() : "");
//		} catch (Exception e) {
//			LOGGER.debug("No supervisor data found for vehicle {}", vehicle.getVin());
//			empDetails.put("supervisor", "");
//		}
//
//		// Process shift details from asset information
//		if (vehicle.getAssetInformation() != null) {
//			try {
//				JSONObject assetInfo = new JSONObject(vehicle.getAssetInformation());
//				if (assetInfo.has("EmpShiftDetails")) {
//					empDetails.put("shift", assetInfo.getString("EmpShiftDetails"));
//				} else {
//					empDetails.put("shift", "");
//				}
//			} catch (Exception e) {
//				LOGGER.error("Error processing shift details", e);
//				empDetails.put("shift", "");
//			}
//		} else {
//			empDetails.put("shift", "");
//		}
//
//		// Set notes from IO details
//		if (vehicle.getIoDetails() != null) {
//			String[] ioDetails = vehicle.getIoDetails().split("\\*");
//			empDetails.put("notes", ioDetails[0]);
//		} else {
//			empDetails.put("notes", "");
//		}
//
//		// Set rest day and employee ID
//		empDetails.put("restDay", vehicle.getRestDay() == null ? "" : vehicle.getRestDay());
//		empDetails.put("empId", vehicle.getUserId() == null ? "" : vehicle.getUserId());
//
//		// Add employee details to vehicle data
//		vehicleDataJson.put("empDetails", empDetails);
//	}

	/**
	 * Format seconds into HH:MM:SS format without day
	 */
	private String formatIntoHHMMSSWithOutDay(int seconds) {
		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		int secs = seconds % 60;

		return String.format("%02d:%02d:%02d", hours, minutes, secs);
	}

	/**
	 * Format seconds into HH:MM format without day
	 */
	private String formatIntoHHMMWithOutDay(int seconds) {
		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;

		return String.format("%02d:%02d", hours, minutes);
	}

}