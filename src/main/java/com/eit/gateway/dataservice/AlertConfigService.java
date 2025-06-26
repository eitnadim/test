package com.eit.gateway.dataservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.entity.AlertConfig;
import com.eit.gateway.entity.VehicleAlerts;
import com.eit.gateway.repository.AlertConfigRepository;

@Service
public class AlertConfigService {

	@Autowired
	AlertConfigRepository alertConfigRepository;

	public List<AlertConfig> getAlertConfigsByVin(String vin) {
		return alertConfigRepository.findByVin(vin);
	}

}
