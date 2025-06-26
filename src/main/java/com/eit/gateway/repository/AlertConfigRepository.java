package com.eit.gateway.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eit.gateway.entity.AlertConfig;

@Repository
public interface AlertConfigRepository extends JpaRepository<AlertConfig, Long> {

	
	List<AlertConfig> findByVin(String vin);
	

}
