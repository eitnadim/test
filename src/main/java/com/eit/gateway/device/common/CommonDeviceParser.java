package com.eit.gateway.device.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.eit.gateway.cache.CacheManager;
import com.eit.gateway.dataservice.CustomService;
import com.eit.gateway.dataservice.VehicleEventService;
import com.eit.gateway.dataservice.VehicleService;
import com.eit.gateway.device.teltonika.AvlData;
import com.eit.gateway.entity.CompanyTrackDevice;
import com.eit.gateway.entity.Vehicle;
import com.eit.gateway.entity.VehicleAlerts;
import com.eit.gateway.entity.VehicleEvent;
import com.eit.gateway.service.AlertService;
import com.eit.gateway.service.LiveTrackDataHandlerService;
import com.eit.gateway.util.TimeZoneUtil;

import jakarta.transaction.Transactional;

@Service
public class CommonDeviceParser {

	public static Map<String, Date> engineTimeMap = new HashMap<String, Date>();

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonDeviceParser.class);

	private static final String OVERSPEED = "OVERSPEED";

	@Autowired
	AlertService alertsManager;

	@Autowired
	private LiveTrackDataHandlerService liveTrackDataHandler;

	@Autowired
	CustomService customService;

	@Autowired
	VehicleService vehicleService;

	@Autowired
	VehicleEventService eventService;

	public VehicleEvent createVehicleEvent(AvlData avlData, Vehicle vehicle, long byteTrx, String region,
			String deviceModel) {
		VehicleEvent vehicleEvent = new VehicleEvent();

		long timeInDecimal = ((avlData.getTimestamp() / 1000) * 1000);
		LOGGER.error("Time in Decimal: {}", timeInDecimal);
		Date date = TimeZoneUtil.getDateTimeZone(new Date(timeInDecimal), region);
		date.setTime(date.getTime());
		vehicleEvent.setEventTimestamp(date);
		vehicleEvent.setServerTimestamp(TimeZoneUtil.getDateTimeZone(new Date(), region));

		boolean isNewData = vehicle.getVEventTimestamp() == null || date.after(vehicle.getVEventTimestamp());
		LOGGER.error("Timestamp::: {} is New {}", date, isNewData);
		LOGGER.error("Old Event :::: {} ", vehicle.getVEventTimestamp());

		if (!isNewData) {
			vehicleEvent = null;
			return vehicleEvent;
		}
		vehicleEvent.setSpeed((int) avlData.getGpsElement().getSpeed());
		vehicleEvent.setVin(vehicle.getVin());
		vehicleEvent.setBytesTrx(byteTrx);
		vehicleEvent.setEventSource(avlData.getTriggeredPropertyId());
		vehicleEvent.setPriority((int) avlData.getPriority());

		return vehicleEvent;
	}

	public void persistVehicleDataAsync(VehicleEvent ve, Vehicle vehicleData, CompanyTrackDevice companyTrackDevice) {
		try {

			LOGGER.error("Vin {},Imei {}", vehicleData.getVin(), vehicleData.getVehicleDeviceImei());

			// Set engine value if not present
			ve.setEngine(ve.getEngine() == null ? vehicleData.getVEngine() : ve.getEngine());

//			 Activate device if currently inactive
//			if (Boolean.FALSE.equals(vehicleData.getActive())) {
//				Vehicle vehicle = fleetDAO.getVehicle(vehicleComposite.getVehicle().getVin());
//				vehicle.setActive(true);
//				vehicle.setInitialTrans(TimeZoneUtil.getTimeINYYYYMMddss(ve.getId().getEventTimeStamp()));
//				fleetDAO.mergeVehicle(vehicle);
//			}//NAdim

			updateVehicleDetails(vehicleData, ve, companyTrackDevice);

			if (vehicleData.getVehicleRemark().equalsIgnoreCase("WMS")) {
				processRFIDReaderData(ve.getTags());

			}

			vehicleService.saveVehicle(vehicleData);

			eventService.saveVehicleEvent(ve);

			// Call async method
			processAlertsAndTrackingAsync(ve, vehicleData, companyTrackDevice);

//			return ve;

		} catch (Exception e) {
			LOGGER.error("Error persisting vehicle data for VIN: " + ve.getVin(), e);
//			return null;
		}
	}

	private void processRFIDReaderData(String tags) {
		try {

		   if(tags.startsWith("{")) {
			   if(new JSONObject(tags).isEmpty() && !new JSONObject(tags).has("rfidForWMS")) {
				  return; 
			   }
			   JSONObject tagsObj=new JSONObject(tags);
			   
			   String RFID=tagsObj.getString("rfidForWMS");
			   
			   
			   
		   }
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

//	@Async
//	@Transactional
	public void processAlertsAndTrackingAsync(VehicleEvent ve, Vehicle vehicleData,
			CompanyTrackDevice companyTrackDevice) {
		try {
			CopyOnWriteArrayList<VehicleAlerts> tempAlertsForVins = new CopyOnWriteArrayList<VehicleAlerts>();
			alertsManager.processAllAlerts(ve, vehicleData, tempAlertsForVins);
			liveTrackDataHandler.getVehicleIOData(vehicleData, ve, "VTS", tempAlertsForVins, companyTrackDevice);
		} catch (Exception e) {
			LOGGER.error("Error in async processing for VIN: " + ve.getVin(), e);
		}
	}

	private void updateVehicleDetails(Vehicle vehicleData, VehicleEvent ve, CompanyTrackDevice companyTrackDevice) {
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

}
