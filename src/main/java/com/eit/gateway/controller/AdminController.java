package com.eit.gateway.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eit.gateway.cache.CacheManager;
import com.eit.gateway.dataservice.CustomService;
import com.eit.gateway.dataservice.VehicleService;
import com.eit.gateway.device.common.EventFactoryBO;
import com.eit.gateway.entity.CompanyTrackDevice;
import com.eit.gateway.entity.Vehicle;
import com.eit.gateway.entity.VehicleEvent;

@RestController
@RequestMapping("/Device")
@CrossOrigin("*")
public class AdminController {

	@Autowired
	CacheManager cacheManager;

	@Autowired
	EventFactoryBO eventFactoryBO;

	@Autowired
	VehicleService vehicleService;

	@Autowired
	CustomService customService;

	@PostMapping("/simulation")
	public void simulation(@RequestBody VehicleEvent vehicleEvents, @RequestParam String device) {

		Vehicle vehicle = vehicleService.getActiveVehicleByVin(vehicleEvents.getVin());

		CompanyTrackDevice companyTrackDevice = customService.getCompanyTrackDevice(vehicle.getVin());

		eventFactoryBO.afterVehicleEventPreparation(vehicleEvents, vehicle, companyTrackDevice,"");

	}

	@GetMapping("/cacheReferes")
	public Set<String> cacheReferesImei() {

		cacheManager.loadActiveIMEINumbers();
		return CacheManager.getImeiNumbers();

	}

//	@GetMapping("/channel")
//	public Map<Integer, AsynchronousSocketChannel> channel() {
//
//		return GatewayServer.getDeviceChannelMap();
//
//	}

}
