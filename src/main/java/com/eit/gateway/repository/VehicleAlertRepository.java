package com.eit.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eit.gateway.entity.VehicleAlerts;

@Repository
public interface VehicleAlertRepository extends JpaRepository<VehicleAlerts, Long> {

	
	 VehicleAlerts findFirstByVinAndAlertTypeOrderByEventTimestampDesc(String vin, String alertType);
	
}
