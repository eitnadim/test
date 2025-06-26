package com.eit.gateway.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stop_details", schema = "mvt")
//@Getter 
//@Setter 
public class StopDetails {
	
	public StopDetails() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "stopid", nullable = false)
    private Long stopId;

    @Column(name = "stopname", length = 80)
    private String stopName;

    @Column(name = "routeid")
    private Long routeId;

    @Column(name = "trip_id")
    private Long tripId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "rfid", nullable = false)
    private String rfid;

    @Column(name = "latlng", nullable = false, length = 120)
    private String latLng;

    @Column(name = "circlelatlng", length = 160)
    private String circleLatLng;

    @Column(name = "lastupdby", length = 45)
    private String lastUpdBy;

    @Column(name = "lastupdt")
    private java.sql.Timestamp lastUpdT;

    @Column(name = "description", length = 120)
    private String description;

    @Column(name = "createdat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private java.sql.Timestamp createdAt;

    @Column(name = "bincapacity")
    private Long binCapacity;

    @Column(name = "userid")
    private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStopId() {
		return stopId;
	}

	public void setStopId(Long stopId) {
		this.stopId = stopId;
	}

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public String getLatLng() {
		return latLng;
	}

	public void setLatLng(String latLng) {
		this.latLng = latLng;
	}

	public String getCircleLatLng() {
		return circleLatLng;
	}

	public void setCircleLatLng(String circleLatLng) {
		this.circleLatLng = circleLatLng;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public java.sql.Timestamp getLastUpdT() {
		return lastUpdT;
	}

	public void setLastUpdT(java.sql.Timestamp lastUpdT) {
		this.lastUpdT = lastUpdT;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public java.sql.Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(java.sql.Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Long getBinCapacity() {
		return binCapacity;
	}

	public void setBinCapacity(Long binCapacity) {
		this.binCapacity = binCapacity;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public StopDetails(Long id, Long stopId, String stopName, Long routeId, Long tripId, String type, String rfid,
			String latLng, String circleLatLng, String lastUpdBy, Timestamp lastUpdT, String description,
			Timestamp createdAt, Long binCapacity, Long userId) {
		super();
		this.id = id;
		this.stopId = stopId;
		this.stopName = stopName;
		this.routeId = routeId;
		this.tripId = tripId;
		this.type = type;
		this.rfid = rfid;
		this.latLng = latLng;
		this.circleLatLng = circleLatLng;
		this.lastUpdBy = lastUpdBy;
		this.lastUpdT = lastUpdT;
		this.description = description;
		this.createdAt = createdAt;
		this.binCapacity = binCapacity;
		this.userId = userId;
	}
    
    
    
}

