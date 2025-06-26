/**
 * 
 */
package com.eit.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eit.gateway.entity.Vehicle;


/**
 * 
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
	
	public Vehicle findByVehicleDeviceImeiAndActive(String imei, Boolean active);
}
