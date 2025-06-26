package com.eit.gateway.dataservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.entity.VehicleAlerts;
import com.eit.gateway.repository.VehicleAlertRepository;

@Service
public class VehicleAlertService {

	@Autowired
	VehicleAlertRepository vehicleAlertRepository;

	public List<VehicleAlerts> saveAlerts(List<VehicleAlerts> vehicleAlerts) {
		return vehicleAlertRepository.saveAll(vehicleAlerts);
	}
	
	public VehicleAlerts getLastEventAlert(String vin,String alertType) {
		return vehicleAlertRepository.findFirstByVinAndAlertTypeOrderByEventTimestampDesc(vin,alertType);
	}

}