/**
 * 
 */
package com.eit.gateway.dataservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.entity.ApplicationSetting;
import com.eit.gateway.repository.ApplicationSettingRepository;

/**
 * 
 */
@Service
public class ApplicationSettingService {

	@Autowired
	ApplicationSettingRepository repo;
	
	public List<ApplicationSetting> getAllApplicationSettings(){
		return repo.findAll();
	}
	
}
