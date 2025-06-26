/**
 * 
 */
package com.eit.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eit.gateway.entity.DeviceDetails;


/**
 * 
 */
@Repository
public interface DeviceDetailsRepository extends JpaRepository<DeviceDetails, Long> {
	
	

}
