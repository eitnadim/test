/**
 * 
 */
package com.eit.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eit.gateway.entity.CompanyTrackDevice;


/**
 * 
 */
@Repository
public interface CompanyTrackDeviceRepository extends JpaRepository<CompanyTrackDevice, String> {
	
}
