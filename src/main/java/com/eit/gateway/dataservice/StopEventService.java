package com.eit.gateway.dataservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.entity.StopEvents;
import com.eit.gateway.repository.StopEventRepository;

@Service
public class StopEventService {

	@Autowired
	StopEventRepository stopEventRepository;
	
	public void saveStopEvent(StopEvents stopEvent) {
		stopEventRepository.save(stopEvent);
	}
	
	public List<StopEvents> getStopEventByvinAndtripId(String vin,Long tripId) {
		return stopEventRepository.findByVinAndTripId(vin,tripId);
	}
	
}
