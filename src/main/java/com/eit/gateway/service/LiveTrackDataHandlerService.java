package com.eit.gateway.service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.cache.CacheManager;
import com.eit.gateway.dataservice.StopEventService;
import com.eit.gateway.entity.CompanyTrackDevice;
import com.eit.gateway.entity.StopEvents;
import com.eit.gateway.entity.Vehicle;
import com.eit.gateway.entity.VehicleAlerts;
import com.eit.gateway.util.TimeZoneUtil;

@Service
public class LiveTrackDataHandlerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LiveTrackDataHandlerService.class);

	private static final String DATE_DDHHMMSS = "yyyy-MM-dd HH:mm:ss";

	private static final Collection<?> NO_TRANS = null;

	@Autowired
	WebSocketService webSocketService;

	@Autowired
	StopEventService stopEventService;

	private static List<StopEvents> stopEvents = null;

	public void sendDataToWebSocket(Vehicle vehicle, CopyOnWriteArrayList<VehicleAlerts> tempAlertsForVins,
			 CompanyTrackDevice companyTrackDevice, StopEvents stopEvent) { // getVehicleIOData

		JSONObject vehicleDataJson = inializeJSON(vehicle);
		String vin = vehicle.getVin();
		try {

			proccessEventData(vehicleDataJson, vehicle);

			setTemperatureSensorValues(vehicleDataJson, vehicle);

			setOdometerValues(vehicleDataJson, vehicle, companyTrackDevice);

			processAnalogInputs(vehicleDataJson, vehicle);

			processDigitalInputs(vehicleDataJson, vehicle);

			processIOEvent(vehicleDataJson, vehicle);

		if (vehicle.getVehicleRemark().equalsIgnoreCase("WMS") && stopEvent != null) {

			stopEvents = stopEventService.getStopEventByvinAndtripId(stopEvent.getVin(), stopEvent.getTripId());

			if (stopEvents != null && !stopEvents.isEmpty()) {
				JSONArray stopEventsArray = new JSONArray();
				for (StopEvents stopEventItem : stopEvents) {
					JSONObject stopEventJson = new JSONObject();
					stopEventJson.put("stopid", stopEventItem.getStopId());
					stopEventJson.put("eventtimestamp", stopEventItem.getEventTimestamp());
					stopEventJson.put("status", stopEventItem.getStatus());
					stopEventJson.put("lat", stopEventItem.getLatLong().split(",")[0]);
					stopEventJson.put("lng", stopEventItem.getLatLong().split(",")[1]);
					stopEventJson.put("address", "");
					stopEventsArray.put(stopEventJson);
				}
				vehicleDataJson.put("stops", stopEventsArray);
			} else {
				vehicleDataJson.put("stops", new JSONArray());
			}
		}

			setVehicleMetadata(vehicleDataJson, vehicle, companyTrackDevice);

		} catch (Exception e) {
			LOGGER.error("Error processing Vehicle IO data for vin = {}", vin, e);
			vehicleDataJson.put("error", "Failed to process vehicle IO data");
			vehicleDataJson.put("vin", vin);
		}

		JSONObject responseJson = new JSONObject();
		responseJson.put(vin, vehicleDataJson);

		webSocketService.sendMessageToWebSocket(responseJson.toString(), vin, tempAlertsForVins, vehicle.getUserId());

	}

	private JSONObject inializeJSON(Vehicle vehicle) {

		JSONObject vehicleDataJson = new JSONObject();

		vehicleDataJson.put("plateno", vehicle.getPlateNo() != null ? vehicle.getPlateNo() : "---");
		vehicleDataJson.put("vin", vehicle.getVin());
		vehicleDataJson.put("vehicleModel",
				vehicle.getVehicleModelModel() != null ? vehicle.getVehicleModelModel() : "CAR");
		vehicleDataJson.put("isTempSensor", false);
		vehicleDataJson.put("doorStatus", -1);
		vehicleDataJson.put("doorStatus2", -1);
		vehicleDataJson.put("fuelStatus", -1);
		vehicleDataJson.put("acStatus", -1);
		vehicleDataJson.put("seatbeltStatus", -1);
		vehicleDataJson.put("runDuration", "1");
		vehicleDataJson.put("stopDuration", "1");
		vehicleDataJson.put("idleStop", "1");
		vehicleDataJson.put("toDate", "1");
		vehicleDataJson.put("overSpeedVio", "1");
		vehicleDataJson.put("noOfStops", "1");
		vehicleDataJson.put("status", "");
		vehicleDataJson.put("speed", 0);
		vehicleDataJson.put("timeStamp", TimeZoneUtil.getStrTZ(vehicle.getvEventTimestamp()));
		vehicleDataJson.put("lastUpdDate", TimeZoneUtil.getStrTZ(vehicle.getvEventTimestamp()));
		vehicleDataJson.put("endDate", TimeZoneUtil.getStrTZ(vehicle.getVServerTimestamp()));
		vehicleDataJson.put("latitude", vehicle.getVLatitude() != null ? vehicle.getVLatitude() : 0.0);
		vehicleDataJson.put("longitude", vehicle.getVLongitude() != null ? vehicle.getVLongitude() : 0.0);
		vehicleDataJson.put("engineId", vehicle.getvEngine() != null ? vehicle.getvEngine() : false);
		vehicleDataJson.put("subject", vehicle.getvIoEvent() != null ? vehicle.getvIoEvent() : "");
		vehicleDataJson.put("batteryVoltage", vehicle.getvBatteryVoltage() != null ? vehicle.getvBatteryVoltage() : 0L);
		vehicleDataJson.put("direction", vehicle.getVDirection() == null ? 0 : vehicle.getVDirection());
		vehicleDataJson.put("lockStatus", vehicle.getLockStatus() == null ? 0 : vehicle.getLockStatus());
		vehicleDataJson.put("contractor", vehicle.getContractor());
		vehicleDataJson.put("vehicleStatus", vehicle.getVehicleStatus() != null ? vehicle.getVehicleStatus() : "{}");
		vehicleDataJson.put("vehicleStatusType",
				vehicle.getVehicleStatusType() != null ? vehicle.getVehicleStatusType() : "working");
		vehicleDataJson.put("timeDifference",  (vehicle.getStatusDuration() != null ? TimeZoneUtil.formatIntoHHMMWithOutDay(Integer.parseInt(String.valueOf(vehicle.getStatusDuration()))) : "00:00"));

		vehicleDataJson.put("temperature1Val", "");
		vehicleDataJson.put("temp1Max", "");
		vehicleDataJson.put("temperature2Val", "");
		vehicleDataJson.put("temp2Max", "");
		vehicleDataJson.put("temperature3Val", "");
		vehicleDataJson.put("temp3Max", "");
		vehicleDataJson.put("temperature4Val", "");
		vehicleDataJson.put("temp4Max", "");

		vehicleDataJson.put("odometer", 0);
		vehicleDataJson.put("finalOdometer", 0);

		vehicleDataJson.put("engineHours", (TimeZoneUtil.formatIntoHHMMSSWithOutDay((int) (vehicle.getEngineHours() != null ? vehicle.getEngineHours() : 0))));

		return vehicleDataJson;

	}

	private void proccessEventData(JSONObject vehicleDataJson, Vehicle vehicle) {
		try {

			SimpleDateFormat sdfTime = new SimpleDateFormat(DATE_DDHHMMSS);

			long transmissionThresholdInSec = 899000;

			Date riyadNow = TimeZoneUtil.getDateInTimeZoneforSKT(new Date(), "Asia/Riyadh");
			long riyasNowInSec = riyadNow.getTime() / 1000;

			Date eventTimeStamp = sdfTime.parse(TimeZoneUtil.getTimeINYYYYMMddss(vehicle.getVEventTimestamp()));
			long eventTimeStampInSec = eventTimeStamp.getTime() / 1000;

			if ((riyasNowInSec - eventTimeStampInSec) >= transmissionThresholdInSec) {
				vehicleDataJson.put("status", NO_TRANS);
				vehicleDataJson.put("speed", 0);
			} else {
				vehicleDataJson.put("status", vehicle.getEventStatus());
				vehicleDataJson.put("speed", vehicle.getvSpeed());
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Event Data not added !!!!");
		}
	}

	private void setTemperatureSensorValues(JSONObject vehicleDataJson, Vehicle vehicle) {
		try {
			if (vehicle.getvTempSensor1() != null) {
				vehicleDataJson.put("temperature1Val", String.valueOf(vehicle.getvTempSensor1()));
				vehicleDataJson.put("temp1Max", String.valueOf(vehicle.getvTempSensor1()));
			}
			if (vehicle.getvTempSensor2() != null) {
				vehicleDataJson.put("temperature2Val", String.valueOf(vehicle.getvTempSensor2()));
				vehicleDataJson.put("temp2Max", String.valueOf(vehicle.getvTempSensor2()));
			}
			if (vehicle.getvTempSensor3() != null) {
				vehicleDataJson.put("temperature3Val", String.valueOf(vehicle.getvTempSensor3()));
				vehicleDataJson.put("temp3Max", String.valueOf(vehicle.getvTempSensor3()));
			}
			if (vehicle.getvTempSensor4() != null) {
				vehicleDataJson.put("temperature4Val", String.valueOf(vehicle.getvTempSensor4()));
				vehicleDataJson.put("temp4Max", String.valueOf(vehicle.getvTempSensor4()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Temperatures not added !!!!");
		}
	}

	private void setOdometerValues(JSONObject vehicleDataJson, Vehicle vehicle, CompanyTrackDevice companyTrackDevice) {

		try {
			vehicleDataJson.put("odometer",
					String.valueOf(vehicle.getTodayOdometer() != null ? vehicle.getTodayOdometer() : 0));

			String manufacturerName = companyTrackDevice.getManufacturerName();

			if (manufacturerName != null
					&& CacheManager.getPreference("odometerInDeviceRawdata", "demo").contains(manufacturerName)) {
				long totalOdometer = vehicle.getVOdometer() != null ? vehicle.getVOdometer() : 0;
				vehicleDataJson.put("finalOdometer", totalOdometer);
			} else {
				long totalOdometer = vehicle.getVOdometer() != null ? vehicle.getVOdometer() : 0;
				vehicleDataJson.put("finalOdometer", (totalOdometer / 1000));

			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Odometer values not added !!!!");
		}

	}

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

	private void processIOEvent(JSONObject vehicleDataJson, Vehicle vehicle) {
		try {
			JSONObject processedIOEvent = new JSONObject(vehicle.getProcessedIoEvent());
			vehicleDataJson.put("gps", processedIOEvent.optBoolean("gps"));
			vehicleDataJson.put("gsmSignalStrength", processedIOEvent.optString("gsmSignalStrength"));
			vehicleDataJson.put("powerSupplyVoltage", processedIOEvent.optString("powerSupplyVoltage"));
			vehicleDataJson.put("batteryVoltage", processedIOEvent.optString("batteryVoltage"));
			vehicleDataJson.put("externalBatteryPercentage", processedIOEvent.optString("externalBatteryPercentage"));
			vehicleDataJson.put("obdFuelLevel", processedIOEvent.optString("obdFuelLevel"));
			vehicleDataJson.put("lockStatus", processedIOEvent.optString("lockStatus"));
			vehicleDataJson.put("obdAmbientTemp", processedIOEvent.optString("obdAmbientTemp"));
			vehicleDataJson.put("fuelLtr", processedIOEvent.optString("fuelLtr"));
			vehicleDataJson.put("obdDistance", processedIOEvent.optString("obdDistance"));
			vehicleDataJson.put("obdECT", processedIOEvent.optString("obdECT"));
			vehicleDataJson.put("obdEFR", processedIOEvent.optString("obdEFR"));
			vehicleDataJson.put("obdRPM", processedIOEvent.optString("obdRPM"));
			vehicleDataJson.put("obdVehicleSpeed", processedIOEvent.optString("obdVehicleSpeed"));
			vehicleDataJson.put("canData", processedIOEvent.optJSONObject("canData", new JSONObject()));

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("IOEvents not added correctly !!!!");
		}
	}

	private void setVehicleMetadata(JSONObject vehicleDataJson, Vehicle vehicle,
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
}
