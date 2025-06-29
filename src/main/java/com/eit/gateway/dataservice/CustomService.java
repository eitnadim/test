/**
 * 
 */
package com.eit.gateway.dataservice;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eit.gateway.entity.CompanyTrackDevice;
import com.eit.gateway.repository.CustomRepository;

/**
 * 
 */
@Service
public class CustomService {

	@Autowired
	CustomRepository customRepo;
	
	public CompanyTrackDevice getCompanyTrackDevice(String vin){
		return customRepo.findByVin(vin);
	}
	
	public Collection<String> getAllActiveIMEINumbers() {
		return customRepo.getActiveIMEINumbers();
	}
	
	public long getLasteventDuration(String vin,Date date) {
		return customRepo.getDuration(vin, date);
	}
	
	public String getOverspeedRange(String vin,String alerttype) {
		return customRepo.getOverspeedRange(vin, alerttype);
	}

	public Object[] getTripDetails(String vin, String time,String rFID) {
		return customRepo.getTripDetails(vin,time, rFID);
	}
}
