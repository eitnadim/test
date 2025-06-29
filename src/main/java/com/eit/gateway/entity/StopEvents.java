package com.eit.gateway.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "stopevents", schema = "mvt",
       uniqueConstraints = @UniqueConstraint(
           name = "uk_stopevents_vin_eventtime", 
           columnNames = {"vin", "eventtimestamp"}))
public class StopEvents {

    public StopEvents() {
        // Auto-generated constructor stub
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "vin", nullable = false, length = 60)
    private String vin;

    @Column(name = "eventtimestamp", nullable = false)
    private Date eventTimestamp;

    @Column(name = "fromdate", nullable = false)
    private Date fromDate;

    @Column(name = "tripid", nullable = false)
    private Long tripId;

    @Column(name = "routeid", nullable = false)
    private Long routeId;

    @Column(name = "stopid", nullable = false)
    private Long stopId;

    @Column(name = "status", nullable = false, length = 60)
    private String status;

    @Column(name = "tripstatus", length = 255)
    private String tripStatus;

    @Column(name = "latlong", length = 255)
    private String latLong;

    @Column(name = "rfid", nullable = false, length = 255)
    private String rfid;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Date getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(Date eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
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

    // Constructor without id (for new entities)
    public StopEvents(String vin, Date eventTimestamp, Date fromDate, Long tripId, Long routeId, 
                     Long stopId, String status, String tripStatus, String latLong, String rfid) {
        this.vin = vin;
        this.eventTimestamp = eventTimestamp;
        this.fromDate = fromDate;
        this.tripId = tripId;
        this.routeId = routeId;
        this.stopId = stopId;
        this.status = status;
        this.tripStatus = tripStatus;
        this.latLong = latLong;
        this.rfid = rfid;
    }

    // Constructor with id (for existing entities)
    public StopEvents(Long id, String vin, Date eventTimestamp, Date fromDate, Long tripId, 
                     Long routeId, Long stopId, String status, String tripStatus, String latLong, String rfid) {
        this.id = id;
        this.vin = vin;
        this.eventTimestamp = eventTimestamp;
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