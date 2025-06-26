/**
 * 
 */
package com.eit.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eit.gateway.entity.CompanySettings;


/**
 * 
 */
@Repository
public interface CompanySettingsRepository extends JpaRepository<CompanySettings, String> {
	
}
