package com.eit.gateway.device.common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.cache.CacheManager;
import com.eit.gateway.device.teltonika.AvlData;
import com.eit.gateway.device.teltonika.IOElement;
import com.eit.gateway.device.teltonika.LongIOElement;
import com.eit.gateway.entity.CompanyTrackDevice;
import com.eit.gateway.entity.Vehicle;
import com.eit.gateway.entity.VehicleEvent;
import com.eit.gateway.util.TimeZoneUtil;

@Service
public class DeviceLogicHandler implements DeviceLogicHandlerBO {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceLogicHandler.class);

	@Autowired
	private CommonDeviceParser commonDeviceParser;

	private static final String DATE_DDHHMMSS = "yyyy-MM-dd HH:mm:ss";

	SimpleDateFormat sdfTime = new SimpleDateFormat(DATE_DDHHMMSS);
	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

	private static final String STR_FMECO3 = "FMEco3";
	private static final String STR_PRO3 = "FMPro3";
	private static final String STR_TMT250 = "TMT250";
	private static final String VERSION_ONE = "V1";
	private static final String totalOdometer = "TotalOdometer";
	private static final String FMC130 = "FMC130";
	private static final String FMB120 = "FMB120";
	private static final String FMC640 = "FMC640";
	private static final String FMM130 = "FMM130";
	private static final String FMB920 = "FMB920";
	private static final String FM2200 = "FM2200";
	private static final String FMC920 = "FMC920";

	private static final String region = "Asia/Riyadh";

	protected static final Map<String, VehicleEvent> prevvehicleEventMap = new HashMap<>();

	protected static final Map<String, Long> preOdometerMap = new HashMap<>();

	protected static final Map<String, String> preLatLangOdometerMap = new HashMap<>();

	private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

	private static Vehicle vehicle = null;
	private static VehicleEvent vehicleEvent = null;
	private static String deviceModel = "";
	private static AvlData avlData = null;
	private static String[] reverseSetting;
	private static JSONObject sensorsValue = new JSONObject();

	private static final String TEMPERATURE_SENSORS_KEY = "temperatureSensors";
	private static final String BLUETOOTH_SENSOR_KEY = "bluetoothSensor";

	private static final String DEVICE_DIGITAL_KEY = "deviceDigitalInputAndOuput";
	private static final String DEVICE_ANALOG_KEY = "deviceAnalogInput";

	private static List<Map<String, JSONObject>> tempratureSensors = new ArrayList<>();
	private static List<Map<String, JSONObject>> bluetoothSensors = new ArrayList<>();

	private static List<Map<String, JSONObject>> deviceDigitals = new ArrayList<>();

	private static List<Map<String, JSONObject>> deviceAnalogs = new ArrayList<>();

	private static JSONObject vehicleDataJson = new JSONObject();

	private static JSONObject canDataJson = new JSONObject();

	String[] aiVal = { "0" };
	String[] vehModel = { "0" };
	Date threeMonthsAgo = null;

	@Override
	public VehicleEvent prepareVehicleEvents(Vehicle vehicleRec, AvlData[] avlDataArrays, long byteTrx,
			CompanyTrackDevice companyTrackDeviceRec) {

		try {
			LOGGER.info("NUkkk check {}", companyTrackDeviceRec);
			vehicle = vehicleRec;

//			companyTrackDevice = companyTrackDeviceRec;

			deviceModel = companyTrackDeviceRec.getModel();

			avlData = avlDataArrays[0];

			// Process each AvlData record
			return processAvlDataRecords(byteTrx);

		} catch (Exception e) {
			LOGGER.error("TeltonikaDeviceProtocolHandler: PreparevehicleEvents: General error", e);
			return null;
		}

	}

	// Process all AVL data records
	private VehicleEvent processAvlDataRecords(long byteTrx) {

		reverseSetting = parseReverseSetting(vehicle);

		try {
			// Create and populate vehicle event
			vehicleEvent = commonDeviceParser.createVehicleEvent(avlData, vehicle, byteTrx, region, deviceModel);
			if (vehicleEvent == null) {
				return vehicleEvent;
			}
			processCoordinates();

			// Process IO elements for the vehicle event
			processIOElements();

			processIODetailsForSensors();
			
			vehicleEvent.setTags(sensorsValue.toString());
			
			
			prevvehicleEventMap.put(vehicleEvent.getVin(), vehicleEvent);

		} catch (Exception e) {
			LOGGER.error("TeltonikaDeviceProtocolHandler: Error processing AVL data record", e);
		}

		return vehicleEvent;
	}


	public void processCoordinates() {
		try {

			Double longitude = Double.valueOf("99.999999");
			Double latitude = Double.valueOf("99.999999");
			String longitudeAsString = String.valueOf(avlData.getGpsElement().getX());
			String latitudeAsString = String.valueOf(avlData.getGpsElement().getY());
			if ((longitudeAsString != null) && (longitudeAsString.length() > 8) && (latitudeAsString != null)
					&& (latitudeAsString.length() > 8)) {

				int longitudeLength = longitudeAsString.length() - 7;
				int latitudeLength = latitudeAsString.length() - 7;

				longitude = Double.parseDouble(longitudeAsString.substring(0, longitudeLength) + "."
						+ longitudeAsString.substring(longitudeLength));

				latitude = Double.parseDouble(latitudeAsString.substring(0, latitudeLength) + "."
						+ latitudeAsString.substring(latitudeLength));

			} else {

				LOGGER.warn("Invalid coordinate format: longitude={}, latitude={}", longitudeAsString,
						latitudeAsString);
			}
			vehicleEvent.setLongitude(longitude);
			vehicleEvent.setLatitude(latitude);
		} catch (NumberFormatException e) {
			LOGGER.error("Error parsing coordinates: {}", e.getMessage());
			vehicleEvent.setLongitude(Double.valueOf("99.999999"));
			vehicleEvent.setLatitude(Double.valueOf("99.999999"));
		}

	}

	// Parse reverse setting
	private String[] parseReverseSetting(Vehicle vehicle) {
		if (vehicle.getReverseSetting() != null) {
			return vehicle.getReverseSetting().split("\\|");
		}
		return null;
	}

	// Process IO elements from AVL data
	private void processIOElements() {
		IOElement ioElement = avlData.getInputOutputElement();
		StringBuilder ioProperty = new StringBuilder("");

		if (ioElement instanceof LongIOElement) {
			LongIOElement longIOElement = (LongIOElement) ioElement;
			int[] propertyIDs = longIOElement.getAvailableLongProperties();

			if (propertyIDs.length > 0) {
				// Process first property
				ioProperty.append(propertyIDs[0]).append("=").append(longIOElement.getLongProperty(propertyIDs[0])[1]);

				// Process remaining properties
				for (int index = 1; index < propertyIDs.length; index++) {
					int propertyID = propertyIDs[index];
					processIOProperty(propertyID, longIOElement, ioProperty);
				}
			}

		}
		String iodatas = ioProperty.toString();
		vehicleEvent.setIoEvent(iodatas);

	}

	private void processIOProperty(int propertyID, LongIOElement longIOElement, StringBuilder ioProperty) {
		switch (propertyID) {
		case 132:
			processSecurityState(propertyID, longIOElement, ioProperty);
			break;
		case 67:
			processBatteryStatus(propertyID, longIOElement, ioProperty);
			break;
		case 109:
			processRFIDForWMS(propertyID, longIOElement);
			break;
		default:
// Default handling for other property IDs
			ioProperty.append(",").append(propertyID).append("=").append(longIOElement.getLongProperty(propertyID)[1]);
			break;
		}
	}

//Process security state (property ID 132)
	private void processSecurityState(int propertyID, LongIOElement longIOElement, StringBuilder ioProperty) {
		try {
			String hexValue = hexStringToASCIIString(String.valueOf(longIOElement.getLongProperty(propertyID)[1]));
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(hexValue.getBytes()));
			in.readByte();

			StringBuilder securityState = new StringBuilder();

// Process first flag byte
			int flag1 = in.readByte();
			securityState.append("keyIgnition:").append((flag1 & 0x01) == 0 ? "0" : "1").append(";ignition:")
					.append((flag1 & 0x02) == 0 ? "0" : "1").append(";ignitionOn:")
					.append((flag1 & 0x04) == 0 ? "0" : "1").append(";webasto:")
					.append((flag1 & 0x08) == 0 ? "0" : "1");

// Process second flag byte
			int flag2 = in.readByte();
			securityState.append("parking:").append((flag2 & 0x01) == 0 ? "0" : "1").append(";handBreak:")
					.append((flag2 & 0x10) == 0 ? "0" : "1").append(";footBreak:")
					.append((flag2 & 0x20) == 0 ? "0" : "1").append(";engineWorking:")
					.append((flag2 & 0x40) == 0 ? "0" : "1").append(";reverse:")
					.append((flag2 & 0x80) == 0 ? "0" : "1");

// Process third flag byte
			int flag3 = in.readByte();
			securityState.append("frontLeftDoor:").append((flag3 & 0x01) == 0 ? "0" : "1").append(";frontLRightDoor:")
					.append((flag3 & 0x02) == 0 ? "0" : "1").append(";rearLeftDoor:")
					.append((flag3 & 0x04) == 0 ? "0" : "1").append(";rearRightDoor:")
					.append((flag3 & 0x08) == 0 ? "0" : "1").append(";engineCover:")
					.append((flag3 & 0x10) == 0 ? "0" : "1").append(";trunkCover:")
					.append((flag3 & 0x20) == 0 ? "0" : "1");

			ioProperty.append(",").append(propertyID).append("=").append(securityState.toString());
		} catch (Exception e) {
			LOGGER.error("Error processing security state", e);
		}
	}

//Process battery status (property ID 67)
	private void processBatteryStatus(int propertyID, LongIOElement longIOElement, StringBuilder ioProperty) {
		long batteryValue = longIOElement.getLongProperty(propertyID)[1];
		ioProperty.append(",").append(propertyID).append("=").append(batteryValue);

// Process battery status for specific device models
		if (deviceModel.equalsIgnoreCase(FMC640) || deviceModel.equalsIgnoreCase(FMB120)
				|| deviceModel.equalsIgnoreCase(FMC130) || deviceModel.equalsIgnoreCase(FMC920)) {

			if (batteryValue < 8231 && batteryValue > 8199)
				ioProperty.append(",").append("600=LowBattery1");
			else if (batteryValue < 8200 && batteryValue > 7899)
				ioProperty.append(",").append("600=LowBattery2");
			else if (batteryValue < 7900)
				ioProperty.append(",").append("600=LowBattery3");
			else if (batteryValue > 8230)
				ioProperty.append(",").append("600=Normal");
		}
	}

//Process RFID for WMS (property ID 109)
	private void processRFIDForWMS(int propertyID, LongIOElement longIOElement) {
		try {
			String hex = longIOElement.getBigIntProperty(propertyID)[1].toString(16);
			StringBuilder ascii = new StringBuilder();

			for (int j = 0; j < hex.length(); j += 2) {
				String hexPair = hex.substring(j, j + 2);
				int decimal = Integer.parseInt(hexPair, 16);
				ascii.append((char) decimal);
			}

			sensorsValue.put("rfidForWMS", ascii.toString());
		} catch (Exception e) {
			LOGGER.error("Error processing RFID for WMS", e);
		}
	}

	private void processIODetailsForSensors() {
		try {

			String[] ioDetailsfromvehicleEvent = vehicleEvent.getIoEvent().split(",");
			for (String ioDetail : ioDetailsfromvehicleEvent) {
				String[] inputTypefromvehicleEvent = ioDetail.split("=");

				if (inputTypefromvehicleEvent.length <= 1) {
					return; // Skip invalid entries
				}

				String ioType = inputTypefromvehicleEvent[0];
				String ioValue = inputTypefromvehicleEvent[1];

				// Process power alarm statuses
				processPowerAlarmStatus(ioType, ioValue);

				// Set ioConfig Value
//				createSensorMapWithIO(new JSONObject(vehicle.getIoConfig()));

				// Process all other IO types
				processIoSensorsType(ioType, ioValue);

				processStandardIOEvents(ioType, ioValue);

				if (!canDataJson.isEmpty()) {
					vehicleDataJson.put("canData", canDataJson);
				}
				if (!sensorsValue.isEmpty()) {
					vehicleDataJson.put("Sensor", sensorsValue);
				}

			}

			if (!vehicleDataJson.isEmpty()) {
				vehicleEvent.setProcessedIoEvent(vehicleDataJson.toString());
			}

		} catch (Exception e) {
			LOGGER.error("processIODetailsForSensors: avlData String General", e);
		}

	}

	private void createSensorMapWithIO(JSONObject ioConfig) {
		try {
			if (!ioConfig.isEmpty()) {

				if (ioConfig.has(TEMPERATURE_SENSORS_KEY)
						&& !ioConfig.getJSONArray(TEMPERATURE_SENSORS_KEY).isEmpty()) {

					processTempratureSensors(ioConfig.getJSONArray(TEMPERATURE_SENSORS_KEY));
				}
				if (ioConfig.has(BLUETOOTH_SENSOR_KEY) && !ioConfig.getJSONArray(BLUETOOTH_SENSOR_KEY).isEmpty()) {

					processBluetoothSensors(ioConfig.getJSONArray(BLUETOOTH_SENSOR_KEY));
				}
				if (ioConfig.has(DEVICE_DIGITAL_KEY) && !ioConfig.getJSONArray(DEVICE_DIGITAL_KEY).isEmpty()) {

					processDeviceDigitals(ioConfig.getJSONArray(DEVICE_DIGITAL_KEY));
				}
				if (ioConfig.has(DEVICE_ANALOG_KEY) && !ioConfig.getJSONArray(DEVICE_ANALOG_KEY).isEmpty()) {

					processDeviceAnalog(ioConfig.getJSONArray(DEVICE_ANALOG_KEY));
				}

			}

		} catch (Exception e) {
			// TODO: handle exception

		}

	}

	private void processStandardIOEvents(String key, String value) throws ParseException {

		switch (key) {

		case "239":
			vehicleDataJson.put("ignition", value);
			break;

		case "240":
			if (deviceModel.equalsIgnoreCase(STR_TMT250)) {
				processSpecialTMT250Event(value);
			}
			break;
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
		case "199":
			vehicleDataJson.put("tripOdometer", Long.parseLong(value));
			break;

		// Signal Strength
		case "27":
		case "21":
			processSignalStrength(key, value);
			break;

		// Power Supply
		case "29":
			if (value != null && Integer.parseInt(value) > 6000)
				vehicleDataJson.put("powerSupplyVoltage", "ON");
			else
				vehicleDataJson.put("powerSupplyVoltage", "OFF");
			break;

		case "66":
			processPowerSupply(value);
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
			processBatteryVoltage(key, value);
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
			processHeartbeatData(value);
			break;
		}
	}

	/**
	 * Process signal strength information
	 */
	private void processSignalStrength(String key, String value) {
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
	private void processPowerSupply(String value) {
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
	private void processBatteryVoltage(String key, String value) {
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
	private void processHeartbeatData(String value) throws ParseException {

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

		long diffSeconds = (hbdeventTimeStamp.getTime() - vehicleEvent.getEventTimestamp().getTime()) / 1000;
		if (hbdeventTimeStamp.after(vehicleEvent.getEventTimestamp()) && diffSeconds > 120) {
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

	private void processDeviceAnalog(JSONArray jsonArray) {
		try {
			Map<String, JSONObject> analog = new HashMap<>();
			for (Object obj : jsonArray) {
				JSONObject aiData = new JSONObject(obj);

				analog.put(aiData.get("analogCategory").toString(), aiData);

			}
			deviceDigitals.add(analog);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void processDeviceDigitals(JSONArray jsonArray) {
		try {
			Map<String, JSONObject> digital = new HashMap<>();
			for (Object obj : jsonArray) {
				JSONObject diData = new JSONObject(obj);

				digital.put(diData.get("inputName").toString(), diData);

			}
			deviceDigitals.add(digital);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void processBluetoothSensors(JSONArray jsonArray) {
		try {
			Map<String, JSONObject> blueTemp = new HashMap<>();
			for (Object obj : jsonArray) {
				JSONObject blueTempData = new JSONObject(obj);

				blueTemp.put(blueTempData.get("io").toString(), blueTempData);

			}
			bluetoothSensors.add(blueTemp);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void processTempratureSensors(JSONArray jsonArray) {
		try {
			Map<String, JSONObject> temp = new HashMap<>();
			for (Object obj : jsonArray) {
				JSONObject tempData = new JSONObject(obj);

				temp.put(tempData.get("io").toString(), tempData);

			}
			tempratureSensors.add(temp);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * Process power alarm status based on device model and IO type
	 */
	private void processPowerAlarmStatus(String ioType, String ioValue) {
		try {
			if (ioType.equalsIgnoreCase("252") && deviceModel.equalsIgnoreCase(FMB120)) {
				if (ioValue.equalsIgnoreCase("1")) {
					sensorsValue.put("PowerCutAlarm", "true");
				} else if (ioValue.equalsIgnoreCase("0")) {
					sensorsValue.put("PowerOnAlarm", "true");
				}
			} else if (ioType.equalsIgnoreCase("66") && (deviceModel.equalsIgnoreCase(FMC640)
					|| deviceModel.equalsIgnoreCase(FMC130) || deviceModel.equalsIgnoreCase(FMC920))) {

				try {
					int ioValueInt = Integer.parseInt(ioValue);
					if (ioValueInt < 5001) {
						sensorsValue.put("PowerCutAlarm", "true");
					} else if (ioValueInt > 5000) {
						sensorsValue.put("PowerOnAlarm", "true");
					}
				} catch (NumberFormatException e) {
					LOGGER.error("Error parsing IO value for power alarm status: {}", ioValue, e);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error processing power alarm status", e);
		}
	}

	private void processIoSensorsType(String ioType, String ioValue) {
		try {

			switch (ioType) {
			case "72":
				processTemperatureSensor(ioType, ioValue, "TEMP1");
				break;

			case "73":
				processTemperatureSensor(ioType, ioValue, "TEMP2");
				break;

			case "74":
				processTemperatureSensor(ioType, ioValue, "TEMP3");
				break;

			case "75":
				processTemperatureSensor(ioType, ioValue, "TEMP4");
				break;

			case "25":
				processBLETemperature(ioType, ioValue, "BLEtemp1");
				break;

			case "26":
				processBLETemperature(ioType, ioValue, "BLEtemp2");
				break;

			case "27":
				processBLETemperature(ioType, ioValue, "BLEtemp3");
				break;

			case "28":
				processBLETemperature(ioType, ioValue, "BLEtemp4");
				break;

			case "86":
				processBLEHumidity(ioValue, "BLEHumidity1");
				break;

			case "104":
				processBLEHumidity(ioValue, "BLEHumidity2");
				break;

			case "106":
				processBLEHumidity(ioValue, "BLEHumidity3");
				break;

			case "108":
				processBLEHumidity(ioValue, "BLEHumidity4");
				break;

			case "66":
				processBatteryEvent(ioValue);
				break;

			case "9":
				processAI1Event(ioValue);
				break;

			case "10":
				vehicleEvent.setAi2(Integer.parseInt(ioValue));
				break;

			case "11":
				vehicleEvent.setAi3(Integer.parseInt(ioValue));
				break;

			case "19":
				vehicleEvent.setAi4(Integer.parseInt(ioValue));
				break;

			case "1":
				processEngineEvent(ioValue);
				break;

			case "2":
				processDI2Event(ioValue);
				break;

			case "3":
				processDI3Event(ioValue);
				break;

			case "4":
				processDI4Event(ioValue);
				break;

			case "29":
				sensorsValue.put("BLEBatVolt1", Long.parseLong(ioValue));
				break;

			case "20":
				sensorsValue.put("BLEBatVolt2", Long.parseLong(ioValue));
				break;

			case "22":
				sensorsValue.put("BLEBatVolt3", Long.parseLong(ioValue));
				break;

			case "23":
				sensorsValue.put("BLEBatVolt4", Long.parseLong(ioValue));
				break;

			case "16":
				processOdometerEvent(ioValue);
				break;

			default:
				// No default action
				break;
			}
		} catch (Exception e) {
			LOGGER.error("Error processing IO type: {}, value: {}", ioType, ioValue, e);
		}
	}

	/**
	 * Process temperature sensor data
	 */
	private void processTemperatureSensor(String ioType, String ioValue, String sensorKey) {

		if (tempratureSensors.isEmpty()) {
			LOGGER.error("No TempratureSensors in Vin::{} ", vehicle.getVin());
		} else {
			try {
				long longValue = Long.parseLong(ioValue);

				switch (ioType) {
				case "72":
					vehicleEvent.setTempSensor1(longValue / 10);
					break;
				case "73":
					vehicleEvent.setTempSensor2(longValue / 10);
					break;
				case "74":
					vehicleEvent.setTempSensor3(longValue / 10);
					break;
				case "75":
					vehicleEvent.setTempSensor4(longValue / 10);
					break;
				default:
					// Should not reach here as only specific types call this method
					break;
				}

				if (CacheManager.getPreference("tempCalc", vehicle.getCompanyId()).equalsIgnoreCase("multiply0.01")) {
					sensorsValue.put(sensorKey, decimalFormat.format((double) longValue * 0.01));
				} else {
					sensorsValue.put(sensorKey, (double) longValue / 10);
				}

			} catch (Exception e) {
				LOGGER.error("Error processing temperature sensor data: {}", ioType, e);
			}
		}

	}

	/**
	 * Process BLE temperature data
	 */
	private void processBLETemperature(String ioType, String ioValue, String sensorKey) {
		try {
			if (!bluetoothSensors.isEmpty()) {
				LOGGER.error("No TempratureSensors in Vin::{} ", vehicle.getVin());
			} else {

				long longValue = Long.parseLong(ioValue);

				if (CacheManager.getPreference("tempCalc", vehicle.getCompanyId()).equalsIgnoreCase("multiply0.01")) {
					sensorsValue.put(sensorKey, decimalFormat.format((double) longValue * 0.01));
				} else {
					sensorsValue.put(sensorKey, (double) longValue / 10);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error processing BLE temperature data: {}", ioType, e);
		}
	}

	/**
	 * Process BLE humidity data
	 */
	private void processBLEHumidity(String ioValue, String sensorKey) {
		try {
			long longValue = Long.parseLong(ioValue);
			sensorsValue.put(sensorKey, (double) longValue / 10);
		} catch (Exception e) {
			LOGGER.error("Error processing BLE humidity data for: {}", sensorKey, e);
		}
	}

	/**
	 * Process battery event
	 */
	private void processBatteryEvent(String ioValue) {
		try {
			long longValue = Long.parseLong(ioValue);
			vehicleEvent.setBattery(longValue);
			vehicleEvent.setAi3((int) longValue);
		} catch (Exception e) {
			LOGGER.error("Error processing battery event", e);
		}
	}

	/**
	 **
	 * Process AI1 event (analog input 1)
	 */
	private void processAI1Event(String ioValue) {
		try {
			int intValue = Integer.parseInt(ioValue);
			vehicleEvent.setAi1(intValue);

			if (vehicle.getIconUrl().equalsIgnoreCase("BATTERY")) {
				int voltage = intValue;
				double percentage = 0.0;

				if (voltage >= 15000) {
					percentage = 100.0;
				} else if (voltage > 10000) {
					percentage = (((double) (voltage - 10000) / (15000 - 10000)) * 100);
				}

				double formattedAi1 = voltage / 1000.0;
				String formattedValue = String.format("%.3f", formattedAi1);
				String formattedPercentage = String.format("%.1f", percentage);

				sensorsValue.put("VOLT", formattedValue);
				sensorsValue.put("SOC", formattedPercentage);

//	            fleetTrackingDeviceListenerBO.insertserviceforbms(vehicleEvent, formattedValue, formattedPercentage);
			}
		} catch (Exception e) {
			LOGGER.error("Error processing AI1 event", e);
		}
	}

	/**
	 * Process engine event
	 */
	private void processEngineEvent(String ioValue) {
		try {
			boolean isEngineOn = ioValue.trim().equalsIgnoreCase("1");
			vehicleEvent.setEngine(isEngineOn);
			vehicleEvent.setDi1(Integer.parseInt(ioValue));
		} catch (Exception e) {
			LOGGER.error("Error processing engine event", e);
		}
	}

	/**
	 * Process digital input 2 (DI2) event
	 */
	private void processDI2Event(String ioValue) {
		try {
			if (vehicle.getReverseSetting() != null && reverseSetting[0].equalsIgnoreCase("1")) {
				if (Boolean.TRUE.equals(vehicleEvent.getEngine())) {
					if (Arrays.asList(vehModel).contains(vehicle.getVehicleModelModel())
							&& (vehicle.getVehicleTypeVehicleType()).equals(VERSION_ONE)) {

						if (Integer.parseInt(aiVal[0]) > (vehicleEvent.getAi1())) {
							vehicleEvent.setDi2(1);
						} else {
							vehicleEvent.setDi2(0);
						}
					} else {
						int ioValueInt = Integer.parseInt(ioValue);
						if (ioValueInt == 1) {
							vehicleEvent.setDi2(0);
						} else {
							vehicleEvent.setDi2(1);
						}
					}
				} else {
					vehicleEvent.setDi2(0);
				}
			} else {
				vehicleEvent.setDi2(Integer.parseInt(ioValue));
			}
		} catch (Exception e) {
			LOGGER.error("Error processing DI2 event", e);
		}
	}

	/**
	 * Process digital input 3 (DI3) event
	 */
	private void processDI3Event(String ioValue) {
		try {
			if (vehicle.getReverseSetting() != null && reverseSetting[1].equalsIgnoreCase("1")) {
				if (Boolean.TRUE.equals(vehicleEvent.getEngine())) {
					if (Arrays.asList(vehModel).contains(vehicle.getVehicleModelModel())
							&& (vehicle.getVehicleTypeVehicleType()).equals(VERSION_ONE)) {

						if (Integer.parseInt(aiVal[0]) > (vehicleEvent.getAi1())) {
							vehicleEvent.setDi3(1);
						} else {
							vehicleEvent.setDi3(0);
						}
					} else {
						int ioValueInt = Integer.parseInt(ioValue);
						if (ioValueInt == 1) {
							vehicleEvent.setDi3(0);
						} else {
							vehicleEvent.setDi3(1);
						}
					}
				} else {
					vehicleEvent.setDi3(0);
				}
			} else {
				vehicleEvent.setDi3(Integer.parseInt(ioValue));
			}
		} catch (Exception e) {
			LOGGER.error("Error processing DI3 event", e);
		}
	}

	/**
	 * Process digital input 4 (DI4) event
	 */
	private void processDI4Event(String ioValue) {
		try {
			if (vehicle.getReverseSetting() != null && reverseSetting[2].equalsIgnoreCase("1")) {
				if (Boolean.TRUE.equals(vehicleEvent.getEngine())) {
					if (Arrays.asList(vehModel).contains(vehicle.getVehicleModelModel())
							&& (vehicle.getVehicleTypeVehicleType()).equals(VERSION_ONE)) {

						if (Integer.parseInt(aiVal[0]) > (vehicleEvent.getAi1())) {
							vehicleEvent.setDi4(1);
						} else {
							vehicleEvent.setDi4(0);
						}
					} else {
						int ioValueInt = Integer.parseInt(ioValue);
						if (ioValueInt == 1) {
							vehicleEvent.setDi4(0);
						} else {
							vehicleEvent.setDi4(1);
						}
					}
				} else {
					vehicleEvent.setDi4(0);
				}
			} else {
				vehicleEvent.setDi4(Integer.parseInt(ioValue));
			}
		} catch (Exception e) {
			LOGGER.error("Error processing DI4 event", e);
		}
	}

	/**
	 * Process odometer event
	 */
	private void processOdometerEvent(String ioValue) {
		try {
			long odometerValue = Long.parseLong(ioValue);
			sensorsValue.put(totalOdometer, odometerValue);

			// Add your preOdometerMap logic here
			if (!deviceModel.equalsIgnoreCase(STR_TMT250)) {
				Map<String, Long> preOdometerMap = getPreOdometerMap(); // You'll need to implement this method

				if (preOdometerMap.get(vehicleEvent.getVin()) != null) {
					Long currOdo = odometerValue - preOdometerMap.get(vehicleEvent.getVin());
					vehicleEvent.setOdometer(currOdo);
					preOdometerMap.put(vehicleEvent.getVin(), odometerValue);
				} else {
					preOdometerMap.put(vehicleEvent.getVin(), odometerValue);
					vehicleEvent.setOdometer(0L);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error processing odometer event", e);
		}
	}
	
	/**
	 * Get pre-odometer map - implementation would depend on how this is stored in
	 * your application
	 */
	private Map<String, Long> getPreOdometerMap() {
		// Return your preOdometerMap or implement how you retrieve it
		return new HashMap<>(); // Placeholder
	}

	/**
	 * Process special TMT250 event
	 */
	private void processSpecialTMT250Event(String ioValue) {
		try {
			boolean isEngineOn = ioValue.trim().equalsIgnoreCase("1");
			vehicleEvent.setEngine(isEngineOn);
			vehicleEvent.setDi1(Integer.parseInt(ioValue));

			// The speed logic would go here but requires avlData which is not available in
			// this context
			// You would need to modify the method signature to include avlData or handle
			// this differently
		} catch (Exception e) {
			LOGGER.error("Error processing special TMT250 event", e);
		}
	}


	public static String hexStringToASCIIString(String hexCode) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < hexCode.length() - 1; i += 2) {
			// grab the hex in pairs
			// convert hex to decimal
			int decimal = Integer.parseInt(hexCode.substring(i, i + 2), 16);
			// convert the decimal to character
			sb.append((char) decimal);
		}
		return sb.toString();
	}

}
