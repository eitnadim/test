package com.eit.gateway.dataservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.entity.VehicleEvent;
import com.eit.gateway.repository.VehicleEventRepository;

@Service
public class VehicleEventService {

	@Autowired
	VehicleEventRepository eventRepository;

	public void saveVehicleEvent(VehicleEvent vehicleEvent) {
		eventRepository.save(vehicleEvent);
	}
}
