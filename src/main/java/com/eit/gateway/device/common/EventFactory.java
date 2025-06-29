package com.eit.gateway.device.common;

import com.eit.gateway.device.meitrack.MeitrackP88LPacket;
import com.eit.gateway.device.teltonika.AvlData;
import com.eit.gateway.entity.CompanyTrackDevice;
import com.eit.gateway.entity.Vehicle;
import com.eit.gateway.entity.VehicleEvent;

public interface EventFactory {

	VehicleEvent TelematicsEventFactory(Vehicle vehicleRec, AvlData[] avlDataArrays, long byteTrx,
			CompanyTrackDevice companyTrackDeviceRec, String rawData);

	VehicleEvent TelematicsEventFactory(Vehicle vehicleRec, MeitrackP88LPacket packetData,
			CompanyTrackDevice companyTrackDeviceRec);

	void afterVehicleEventPreparation(VehicleEvent vehicleEvent, Vehicle vehicleRec,
			CompanyTrackDevice companyTrackDeviceRec, String rawData);

}
