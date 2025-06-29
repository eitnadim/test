package com.eit.gateway.dataservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.entity.VehicleEvent;
import com.eit.gateway.repository.VehicleEventRepository;

@Service
public class VehicleEventService {

	@Autowired
	VehicleEventRepository eventRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VehicleEventService.class);


	public void saveVehicleEvent(VehicleEvent vehicleEvent) {
		LOGGER.info("Saving vehicle event: vin:{},TimeStamp:{}", vehicleEvent.getVin(),vehicleEvent.getEventTimestamp());
		eventRepository.save(vehicleEvent);
	}
}
