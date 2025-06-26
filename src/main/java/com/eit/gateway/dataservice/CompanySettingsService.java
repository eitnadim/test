/**
 * 
 */
package com.eit.gateway.dataservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.entity.CompanySettings;
import com.eit.gateway.repository.CompanySettingsRepository;

/**
 * 
 */
@Service
public class CompanySettingsService {

	@Autowired
	CompanySettingsRepository repo;
	
	public List<CompanySettings> getAllCompanySettings(){
		return repo.findAll();
	}
	
}
