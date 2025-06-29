/**
 * 
 */
package com.eit.gateway.dataservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.entity.Vehicle;
import com.eit.gateway.repository.VehicleRepository;
import com.eit.gateway.util.Constants;

/**
 * 
 */
@Service
public class VehicleService {

	@Autowired
	VehicleRepository repo;

	public Vehicle getActiveVehicleByImeiNo(String imei) {
		return repo.findByVehicleDeviceImeiAndActive(imei, Boolean.TRUE);
	}
	
	public Vehicle getActiveVehicleByVin(String vin) {
		return repo.findByvinAndActive(vin, Boolean.TRUE);
	}

	public void saveVehicle(Vehicle vehicle) {
		repo.save(vehicle);
	}

}
