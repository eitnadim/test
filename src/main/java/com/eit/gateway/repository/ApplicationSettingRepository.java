/**
 * 
 */
package com.eit.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eit.gateway.entity.ApplicationSetting;


/**
 * 
 */
@Repository
public interface ApplicationSettingRepository extends JpaRepository<ApplicationSetting, String> {
	
}
