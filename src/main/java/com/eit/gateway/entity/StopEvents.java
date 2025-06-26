package com.eit.gateway.entity;

import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stopevents", schema = "mvt")
//@Getter 
//@Setter 
public class StopEvents {
	
	public StopEvents() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @Column(name = "eventtimestamp", nullable = false)
    private java.sql.Timestamp eventTimestamp;

    @Column(name = "vin", nullable = false, length = 60)
    private String vin;

    @Column(name = "fromdate", nullable = false)
    private java.sql.Date fromDate;

    @Column(name = "tripid", nullable = false)
    private Long tripId;

    @Column(name = "routeid", nullable = false)
    private Long routeId;

    @Column(name = "stopid", nullable = false)
    private Long stopId;

    @Column(name = "status", nullable = false, length = 60)
    private String status;

    @Column(name = "tripstatus", length = 60)
    private String tripStatus;

    @Column(name = "latlong", length = 255)
    private String latLong;

    @Column(name = "rfid", nullable = false, length = 60)
    private String rfid;

	public java.sql.Timestamp getEventTimestamp() {
		return eventTimestamp;
	}

	public void setEventTimestamp(java.sql.Timestamp eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public java.sql.Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(java.sql.Date fromDate) {
		this.fromDate = fromDate;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public Long getStopId() {
		return stopId;
	}

	public void setStopId(Long stopId) {
		this.stopId = stopId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}

	public String getLatLong() {
		return latLong;
	}

	public void setLatLong(String latLong) {
		this.latLong = latLong;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public StopEvents(Timestamp eventTimestamp, String vin, Date fromDate, Long tripId, Long routeId, Long stopId,
			String status, String tripStatus, String latLong, String rfid) {
		super();
		this.eventTimestamp = eventTimestamp;
		this.vin = vin;
		this.fromDate = fromDate;
		this.tripId = tripId;
		this.routeId = routeId;
		this.stopId = stopId;
		this.status = status;
		this.tripStatus = tripStatus;
		this.latLong = latLong;
		this.rfid = rfid;
	}
    
    
    
}

