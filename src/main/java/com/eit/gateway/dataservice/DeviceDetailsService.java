/**
 * 
 */
package com.eit.gateway.dataservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.entity.DeviceDetails;
import com.eit.gateway.repository.DeviceDetailsRepository;

/**
 * 
 */
@Service
public class DeviceDetailsService {

	@Autowired
	DeviceDetailsRepository repo;
	
	public List<DeviceDetails> getAllDeviceDetails(){
		return repo.findAll();
	}
	
}
