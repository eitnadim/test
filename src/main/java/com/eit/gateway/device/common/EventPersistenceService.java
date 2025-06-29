package com.eit.gateway.device.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.cache.CacheManager;
import com.eit.gateway.dataservice.CustomService;
import com.eit.gateway.dataservice.StopEventService;
import com.eit.gateway.dataservice.VehicleEventService;
import com.eit.gateway.dataservice.VehicleService;
import com.eit.gateway.entity.CompanyTrackDevice;
import com.eit.gateway.entity.StopDetails;
import com.eit.gateway.entity.StopEvents;
import com.eit.gateway.entity.TripDetails;
import com.eit.gateway.entity.Vehicle;
import com.eit.gateway.entity.VehicleEvent;
import com.eit.gateway.util.TimeZoneUtil;

@Service
public class EventPersistenceService {

	private static Map<String, Date> engineTimeMap = new HashMap<String, Date>();

	private static final Logger LOGGER = LoggerFactory.getLogger(EventPersistenceService.class);

	private static final String OVERSPEED = "OVERSPEED";

	private static final String RFID_KEY = "rfidForWMS";
	private static final String STATUS_COLLECTED = "collected";
	private static final String TRIP_STATUS_IN_PROGRESS = "inprogress";

	@Autowired
	CustomService customService;

	@Autowired
	StopEventService stopEventsService;

	@Autowired
	VehicleService vehicleService;

	@Autowired
	VehicleEventService eventService;

	public void prepareLastEventAndPresist(VehicleEvent vehicleEvent, Vehicle vehicleData,
			CompanyTrackDevice companyTrackDevice) {

		updateVehicleDetails(vehicleData, vehicleEvent, companyTrackDevice);

		if (vehicleData.getVehicleRemark().equalsIgnoreCase("WMS")) {
			processRFIDReaderData(vehicleEvent);

		}
		vehicleService.saveVehicle(vehicleData);

		eventService.saveVehicleEvent(vehicleEvent);

	}

	private void updateVehicleDetails(Vehicle vehicleData, VehicleEvent ve, CompanyTrackDevice companyTrackDevice) {

		ve.setEngine(ve.getEngine() == null ? vehicleData.getVEngine() : ve.getEngine());

		vehicleData.setVEventTimestamp(ve.getEventTimestamp());
		vehicleData.setVServerTimestamp(ve.getServerTimestamp());
		vehicleData.setVLatitude(ve.getLatitude());
		vehicleData.setVLongitude(ve.getLongitude());
		vehicleData.setVSpeed(ve.getSpeed());
		vehicleData.setVEngine(ve.getEngine());
		vehicleData.setVBattery(ve.getBattery());
		vehicleData.setVDirection(ve.getDirection());
		vehicleData.setVTags(ve.getTags());
		vehicleData.setVIoEvent(ve.getIoEvent());

		vehicleData.setVTempSensor1(ve.getTempSensor1());
		vehicleData.setVTempSensor2(ve.getTempSensor2());
		vehicleData.setVTempSensor3(ve.getTempSensor3());
		vehicleData.setVTempSensor4(ve.getTempSensor4());

		// These columns used for temperature sensor
		vehicleData.setvDEvent1(ve.getdEvent1());
		vehicleData.setvDEvent2(ve.getdEvent2());
		vehicleData.setvDEvent3(ve.getdEvent3());
		vehicleData.setvDEvent4(ve.getdEvent4());

		vehicleData.setVBattery(ve.getBattery());
		vehicleData.setVDirection(ve.getDirection());
		vehicleData.setVEventSource(ve.getEventSource());
		vehicleData.setVAi1(ve.getAi1());
		vehicleData.setVAi2(ve.getAi2());
		vehicleData.setVAi3(ve.getAi3());
		vehicleData.setVAi4(ve.getAi4());
		vehicleData.setVDi1(ve.getDi1());
		vehicleData.setVDi2(ve.getDi2());
		vehicleData.setVDi3(ve.getDi3());
		vehicleData.setVDi4(ve.getDi4());
		vehicleData.setVTags(ve.getTags());

		vehicleData.setProcessedIoEvent(ve.getProcessedIoEvent());

		// Update odometer
		Long todayOdometer = vehicleData.getTodayOdometer() != null ? vehicleData.getTodayOdometer() : 0L;
		vehicleData.setTodayOdometer(todayOdometer + (ve.getOdometer() == null ? 0L : ve.getOdometer()));

		if (CacheManager.getPreference("odometerInDeviceRawdata", "demo")
				.contains(companyTrackDevice.getManufacturerName())) {
			vehicleData.setVOdometer(ve.getOdometer());
		}

		String status = determineVehicleStatus(vehicleData, ve);

		ve.setStatus(status);
		vehicleData.setEventStatus(status);

		// Update engine hours
		updateEngineHours(vehicleData, ve);
	}

	private void processRFIDReaderData(VehicleEvent event) {
		// Input validation
		if (event.getTags() == null || event.getTags().trim().isEmpty()) {
			LOGGER.warn("Invalid event or empty tags provided");
			return;
		}

		try {
			String tags = event.getTags().trim();

			// Check if tags is valid JSON format
			if (!tags.startsWith("{")) {
				LOGGER.debug("Tags is not JSON format for VIN: {}", event.getVin());
				return;
			}

			// Parse JSON once
			JSONObject tagsObj = new JSONObject(tags);

			// Validate JSON content
			if (tagsObj.isEmpty() || !tagsObj.has(RFID_KEY)) {
				LOGGER.debug("No RFID data found in tags for VIN: {}", event.getVin());
				return;
			}

			String rfid = tagsObj.getString(RFID_KEY);
			if (rfid == null || rfid.trim().isEmpty()) {
				LOGGER.warn("Empty RFID value for VIN: {}", event.getVin());
				return;
			}

			String eventTime = TimeZoneUtil.getDateTStr(event.getEventTimestamp());

			// Get trip details
			Object[] tripObj = customService.getTripDetails(event.getVin(), eventTime, rfid);

			if (tripObj == null || tripObj.length < 2) {
				LOGGER.warn("No trip details found for VIN: {}, RFID: {}, Time: {}", event.getVin(), rfid,
						event.getEventTimestamp());
				return;
			}

			// Extract trip and stop details
			TripDetails tripDetails = (TripDetails) tripObj[0];
			StopDetails stopDetails = (StopDetails) tripObj[1];

			if (tripDetails == null || stopDetails == null) {
				LOGGER.warn("Invalid trip or stop details for VIN: {}, RFID: {}", event.getVin(), rfid);
				return;
			}

			// Create and populate stop event
			EventFactoryBO.setStopEvent(createStopEvent(event, tripDetails, stopDetails, rfid));

			// Save stop event
			stopEventsService.saveStopEvent(EventFactoryBO.getStopEvent());

			LOGGER.info("RFID Reader Data Processed successfully for VIN: {}, RFID: {}, TripId: {}", event.getVin(),
					rfid, tripDetails.getTripId());

		} catch (Exception e) {
			LOGGER.error("Error processing RFID reader data for VIN: {}", event.getVin(), e);
		}
	}

	private StopEvents createStopEvent(VehicleEvent event, TripDetails tripDetails, StopDetails stopDetails,
			String rfid) {
		StopEvents stopEvent = new StopEvents();

		stopEvent.setRouteId(tripDetails.getRouteId());
		stopEvent.setTripId(tripDetails.getTripId());
		stopEvent.setStopId(stopDetails.getId());
		stopEvent.setVin(event.getVin());
		stopEvent.setRfid(rfid);
		stopEvent.setLatLong(event.getLatitude() + "," + event.getLongitude());
		stopEvent.setEventTimestamp(event.getEventTimestamp());
		stopEvent.setFromDate(TimeZoneUtil.getStrDZDate(event.getEventTimestamp()));
		stopEvent.setStatus(STATUS_COLLECTED);
		stopEvent.setTripStatus(TRIP_STATUS_IN_PROGRESS);

		return stopEvent;
	}

	private String determineVehicleStatus(Vehicle vehicleData, VehicleEvent ve) {
//		if (vehicleData.getVehicleTypeVehicleType().equalsIgnoreCase("Employee")) {
//			return getEmployeeStatus(vehicleComposite, ve);//Nadim
//		} else {
		return getGeneralVehicleStatus(vehicleData, ve);
//		}
	}

//	private String getEmployeeStatus(VehicleComposite vehicleComposite, Vehicleevent ve) {
//		if (!vehicleComposite.getIsemployeInsideZoneFOrWft()) {
//			return ve.getEngine() ? "Out_Of_Zone" : "Towed";
//		} else {
//			return ve.getEngine() ? "Working" : "Idle";
//		}//Nadim
//	}

	private String getGeneralVehicleStatus(Vehicle vehicleData, VehicleEvent ve) {
		if (!ve.getEngine() && ve.getSpeed() == 0) {
			return "Stop";
		} else if (ve.getEngine() && ve.getSpeed() == 0) {
			return "Idle";
		} else if (ve.getEngine() && ve.getSpeed() > 0) {
			int alertrange = getOverspeeedAlertRange(vehicleData.getVin());
			return ve.getSpeed() > alertrange ? "Overspeed" : "Running";
		} else if (!ve.getEngine() && ve.getSpeed() > 0) {
			return "Towed";
		}
		return "Unknown";
	}

	private int getOverspeeedAlertRange(String vin) {
		int overSpeedRange = 700;
		try {
			String range = customService.getOverspeedRange(vin, OVERSPEED);
			LOGGER.info("{}", range);

			JSONObject json = new JSONObject(range);

			if (!json.isEmpty()) {
				return Integer.parseInt(json.getString("speed"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return overSpeedRange;
		}
		return overSpeedRange;
	}

	private void updateEngineHours(Vehicle vehicleData, VehicleEvent ve) {
		String curVin = ve.getVin();
		if (ve.getEngine() && !engineTimeMap.containsKey(curVin)) {
			engineTimeMap.put(curVin, ve.getEventTimestamp());
		} else if (!ve.getEngine() && engineTimeMap.containsKey(curVin)) {
			long engineTime = ve.getEventTimestamp().getTime() - engineTimeMap.get(curVin).getTime();
			vehicleData.setEngineHours(
					(engineTime / 1000) + (vehicleData.getEngineHours() == null ? 0L : vehicleData.getEngineHours()));
			vehicleData.setTotalEngineHours((engineTime / 1000)
					+ (vehicleData.getTotalEngineHours() == null ? 0L : vehicleData.getTotalEngineHours()));
			engineTimeMap.remove(curVin);
		}
	}

}
