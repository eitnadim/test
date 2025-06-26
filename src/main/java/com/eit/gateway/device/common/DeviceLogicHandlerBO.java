package com.eit.gateway.device.common;

import com.eit.gateway.device.teltonika.AvlData;
import com.eit.gateway.entity.CompanyTrackDevice;
import com.eit.gateway.entity.Vehicle;
import com.eit.gateway.entity.VehicleEvent;

public interface DeviceLogicHandlerBO {

	VehicleEvent prepareVehicleEvents(Vehicle vehicleRec, AvlData[] avlDataArrays, long byteTrx,
			CompanyTrackDevice companyTrackDeviceRec);

}
