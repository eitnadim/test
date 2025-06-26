package com.eit.gateway.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "route", schema = "mvt")
//@Getter 
//@Setter
public class Route {
	
	public Route() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "routeid", nullable = false)
    private Long routeId;

    @Column(name = "routename", length = 80)
    private String routeName;

    @Column(name = "startlocation", length = 80)
    private String startLocation;

    @Column(name = "endlocation", length = 80)
    private String endLocation;

    @Column(name = "totaldistance")
    private Double totalDistance;

    @Column(name = "vin", length = 65)
    private String vin;

    @Column(name = "startlatlng", nullable = false, length = 120)
    private String startLatLng;

    @Column(name = "endlatlng", nullable = false, length = 120)
    private String endLatLng;

    @Column(name = "lastupdby", length = 45)
    private String lastUpdBy;

    @Column(name = "lastupdt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private java.sql.Timestamp lastUpdT;

    @Column(name = "description", length = 120)
    private String description;

    @Column(name = "userid")
    private Long userId;

    @Column(name = "status", length = 30, columnDefinition = "character varying(30) DEFAULT NULL")
    private String status;

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	public Double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getStartLatLng() {
		return startLatLng;
	}

	public void setStartLatLng(String startLatLng) {
		this.startLatLng = startLatLng;
	}

	public String getEndLatLng() {
		return endLatLng;
	}

	public void setEndLatLng(String endLatLng) {
		this.endLatLng = endLatLng;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Route(Long routeId, String routeName, String startLocation, String endLocation, Double totalDistance,
			String vin, String startLatLng, String endLatLng, String lastUpdBy, Timestamp lastUpdT, String description,
			Long userId, String status) {
		super();
		this.routeId = routeId;
		this.routeName = routeName;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.totalDistance = totalDistance;
		this.vin = vin;
		this.startLatLng = startLatLng;
		this.endLatLng = endLatLng;
		this.lastUpdBy = lastUpdBy;
		this.lastUpdT = lastUpdT;
		this.description = description;
		this.userId = userId;
		this.status = status;
	}
    
    
}

