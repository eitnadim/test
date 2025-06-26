package com.eit.gateway.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "vehiclealerts", schema = "mvt")
public class VehicleAlerts {
    
    public VehicleAlerts() {
        // Default constructor
    }
    
    public VehicleAlerts(Long id, String userId, String alertType, Date eventTimestamp, String vin, Date fromDate,
            String description, String smsMobile, Boolean smsStatus, String latLng, Date serverTimestamp,
            String subAlertType, Boolean showStatus, String additionalInfo, String mailDetails) {
        super();
        this.id = id;
        this.userId = userId;
        this.alertType = alertType;
        this.eventTimestamp = eventTimestamp;
        this.vin = vin;
        this.fromDate = fromDate;
        this.description = description;
        this.smsMobile = smsMobile;
        this.smsStatus = smsStatus;
        this.latLng = latLng;
        this.serverTimestamp = serverTimestamp;
        this.subAlertType = subAlertType;
        this.showStatus = showStatus;
        this.additionalInfo = additionalInfo;
        this.mailDetails = mailDetails;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "userid", columnDefinition = "TEXT")
    private String userId;

    @Column(name = "alerttype", nullable = false, length = 45)
    private String alertType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "eventtimestamp", nullable = false)
    private Date eventTimestamp;

    @Column(name = "vin", nullable = false, length = 45)
    private String vin;

    @Temporal(TemporalType.DATE)
    @Column(name = "fromdate", nullable = false)
    private Date fromDate;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "smsmobile", length = 45)
    private String smsMobile;

    @Column(name = "smsstatus")
    private Boolean smsStatus;

    @Column(name = "latlng", length = 100)
    private String latLng;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "servertimestamp")
    private Date serverTimestamp;

    @Column(name = "subalerttype", length = 45)
    private String subAlertType;

    @Column(name = "showstatus")
    private Boolean showStatus;

    @Column(name = "additionalinfo", length = 60)
    private String additionalInfo;

    @Column(name = "maildetails", length = 200)
    private String mailDetails;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public Date getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(Date eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSmsMobile() {
        return smsMobile;
    }

    public void setSmsMobile(String smsMobile) {
        this.smsMobile = smsMobile;
    }

    public Boolean getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(Boolean smsStatus) {
        this.smsStatus = smsStatus;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }

    public Date getServerTimestamp() {
        return serverTimestamp;
    }

    public void setServerTimestamp(Date serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    public String getSubAlertType() {
        return subAlertType;
    }

    public void setSubAlertType(String subAlertType) {
        this.subAlertType = subAlertType;
    }

    public Boolean getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Boolean showStatus) {
        this.showStatus = showStatus;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getMailDetails() {
        return mailDetails;
    }

    public void setMailDetails(String mailDetails) {
        this.mailDetails = mailDetails;
    }
}