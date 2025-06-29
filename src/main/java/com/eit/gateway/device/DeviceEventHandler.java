package com.eit.gateway.device;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.HashMap;
import java.util.Map;

import com.eit.gateway.device.teltonika.AvlData;
import com.eit.gateway.device.teltonika.AvlDataFM4;
import com.eit.gateway.device.teltonika.AvlDataFM8E;
import com.eit.gateway.device.teltonika.AvlDataGH;
import com.eit.gateway.entity.DeviceDetails;
import com.eit.gateway.entity.Vehicle;

public abstract class DeviceEventHandler implements CompletionHandler<Integer, Void> {

	private Vehicle vehicle;
	private String imeiNo;
	private String mapKey;
	private DeviceDetails deviceDetails;
	private AsynchronousSocketChannel asyncSocketChannel;
	private ByteBuffer buffer;
	private String rawData;

	private Map<Byte, Object> codecObjects = new HashMap<Byte, Object>();

	public abstract byte[] proces() throws Exception;

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public String getMapKey() {
		return mapKey;
	}

	public void setMapKey(String mapKey) {
		this.mapKey = mapKey;
	}

	public DeviceDetails getDeviceDetails() {
		return deviceDetails;
	}

	public void setDeviceDetails(DeviceDetails deviceDetails) {
		this.deviceDetails = deviceDetails;
	}

	public AsynchronousSocketChannel getAsyncSocketChannel() {
		return asyncSocketChannel;
	}

	public void setAsyncSocketChannel(AsynchronousSocketChannel asyncSocketChannel) {
		this.asyncSocketChannel = asyncSocketChannel;
	}

	public ByteBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(ByteBuffer buffer) {
		this.buffer = buffer;
	}

	public Object getCodec(byte codecId) {
		return codecObjects.get(codecId);
	}

	/**
	 * @return the codecMaps
	 */
	public Map<Byte, Object> getCodecObjects() {
		return codecObjects;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	/**
	 * @param codecMaps the codecMaps to set
	 */
	public void addCodecObject(byte codedId, Object object) {
		codecObjects.put(codedId, object);
	}

	public void addCodecs() {
		// TODO Auto-generated method stub
		codecObjects.put((byte) 1, AvlData.getCodec());
		codecObjects.put((byte) 8, AvlDataFM4.getCodec());
		codecObjects.put((byte) 7, AvlDataGH.getCodec());
		codecObjects.put((byte) -114, AvlDataFM8E.getCodec());

	}

}
