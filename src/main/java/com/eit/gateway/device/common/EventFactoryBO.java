package com.eit.gateway.device.common;

import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eit.gateway.device.meitrack.MeitrackEventBuilder;
import com.eit.gateway.device.meitrack.MeitrackP88LPacket;
import com.eit.gateway.device.teltonika.AvlData;
import com.eit.gateway.device.teltonika.TeltonikaEventBuilder;
import com.eit.gateway.entity.CompanyTrackDevice;
import com.eit.gateway.entity.LoggingEvent;
import com.eit.gateway.entity.StopEvents;
import com.eit.gateway.entity.Vehicle;
import com.eit.gateway.entity.VehicleAlerts;
import com.eit.gateway.entity.VehicleEvent;
import com.eit.gateway.service.AlertService;
import com.eit.gateway.service.LiveTrackDataHandlerService;

@Component
public class EventFactoryBO implements EventFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventFactoryBO.class);

	private static VehicleEvent vehicleEvent = null;

	private static StopEvents stopEvent = null;

	public static StopEvents getStopEvent() {
		return stopEvent;
	}

	public static void setStopEvent(StopEvents stopEvent) {
		EventFactoryBO.stopEvent = stopEvent;
	}

	@Autowired
	TeltonikaEventBuilder teltonikaEventBuilder;

	@Autowired
	MeitrackEventBuilder meitrackEventBuilder;

	@Autowired
	EventPersistenceService eventPersistenceService;

	@Autowired
	AlertService alertService;

	@Autowired
	LiveTrackDataHandlerService webSocketPublisher;

	@Override
	public VehicleEvent TelematicsEventFactory(Vehicle vehicleRec, AvlData[] avlDataArrays, long byteTrx,
			CompanyTrackDevice companyTrackDeviceRec, String rawData) {

		try {

			vehicleEvent = teltonikaEventBuilder.prepareVehicleEvents(avlDataArrays[0], vehicleRec, byteTrx,
					companyTrackDeviceRec);

			if (vehicleEvent == null) {
				return vehicleEvent;
			}
			afterVehicleEventPreparation(vehicleEvent, vehicleRec, companyTrackDeviceRec, rawData);

		} catch (Exception e) {
			LOGGER.error("TeltonikaDeviceProtocolHandler: PreparevehicleEvents: General error", e);
			return null;
		}
		return null;

	}

	@Override
	public VehicleEvent TelematicsEventFactory(Vehicle vehicleRec, MeitrackP88LPacket packetData,
			CompanyTrackDevice companyTrackDeviceRec) {
		try {

			vehicleEvent = meitrackEventBuilder.prepareVehicleEvents(packetData, vehicleRec);

			if (vehicleEvent == null) {
				return vehicleEvent;
			}

			afterVehicleEventPreparation(vehicleEvent, vehicleRec, companyTrackDeviceRec, "");

		} catch (Exception e) {
			LOGGER.error("TeltonikaDeviceProtocolHandler: PreparevehicleEvents: General error", e);
			return null;
		}
		return null;
	}

	@Override
	public void afterVehicleEventPreparation(VehicleEvent vehicleEvent, Vehicle vehicleRec,
			CompanyTrackDevice companyTrackDeviceRec, String rawData) {

		if (!rawData.equalsIgnoreCase(""))
			persistLoggingEvent(vehicleRec, rawData);

		CopyOnWriteArrayList<VehicleAlerts> tempAlertsForVins = new CopyOnWriteArrayList<VehicleAlerts>();
		// This method can be used to perform any actions after the vehicle event has
		// been prepared
		// For example, logging or additional processing
		LOGGER.info("Vehicle event prepared for vehicle: {}, device: {}", vehicleRec.getVin(),
				companyTrackDeviceRec.getManufacturerName());
		eventPersistenceService.prepareLastEventAndPresist(vehicleEvent, vehicleRec, companyTrackDeviceRec);

		// Process alerts for the vehicle event
		alertService.processAllAlerts(vehicleEvent, vehicleRec, tempAlertsForVins);

		// Send data to WebSocket
		webSocketPublisher.sendDataToWebSocket(vehicleRec, tempAlertsForVins, companyTrackDeviceRec, stopEvent);

	}

	private void persistLoggingEvent(Vehicle vehicle, String rawData) {
		try {
			LOGGER.info("Persisting logging event for vehicle: {}, raw data: {}", vehicle.getVin(), rawData);
			LoggingEvent loggingEvent = new LoggingEvent();

			loggingEvent.setImei(vehicle.getVehicleDeviceImei());
			loggingEvent.setEventTimestamp(vehicle.getVEventTimestamp());
			loggingEvent.setServerTimestamp(vehicle.getVServerTimestamp());
			loggingEvent.setRawData(rawData);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
