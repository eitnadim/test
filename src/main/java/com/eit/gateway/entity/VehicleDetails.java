package com.eit.gateway.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle_details", schema = "mvt",
       indexes = {
           @Index(name = "idx_vehicle_imei", columnList = "vehicledeviceimei"),
           @Index(name = "idx_vehicle_userid", columnList = "userid"),
           @Index(name = "idx_vehicle_plateno", columnList = "plateno")
       })
public class VehicleDetails {
	
	public VehicleDetails() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @Column(name = "vin", nullable = false, length = 20)
    private String vin;

    @Column(name = "userid", length = 255)
    private String userid;

    @Column(name = "plateno", length = 200)
    private String plateno;

    @Column(name = "callsgincode", length = 150)
    private String callsgincode;

    @Column(name = "vehicledeviceimei", length = 100)
    private String vehicleDeviceImei;

    @Column(name = "secondaryimei", length = 100)
    private String secondaryImei;

    @Column(name = "vehicletype_vehicletype", length = 500, nullable = false)
    private String vehicleType;

    @Column(name = "vehiclemodel_model", length = 500, nullable = false)
    private String vehicleModel;

    @Column(name = "assert_type", length = 500, nullable = false)
    private String assertType;

    @Column(name = "year", length = 50, nullable = false)
    private String year;

    @Column(name = "vehiclegroup", length = 150)
    private String vehicleGroup;

    @Column(name = "color", length = 150)
    private String color;

    @Column(name = "operatorname", length = 60)
    private String operatorName;

    @Column(name = "expectedmileage")
    private Integer expectedMileage;

    @Column(name = "contractor", length = 45)
    private String contractor;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "lastupdby", length = 45)
    private String lastUpdatedBy;

    @Column(name = "lastupddt")
    private LocalDateTime lastUpdatedDt;

    // Getters and Setters
    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPlateno() {
        return plateno;
    }

    public void setPlateno(String plateno) {
        this.plateno = plateno;
    }

    public String getCallsgincode() {
        return callsgincode;
    }

    public void setCallsgincode(String callsgincode) {
        this.callsgincode = callsgincode;
    }

    public String getVehicleDeviceImei() {
        return vehicleDeviceImei;
    }

    public void setVehicleDeviceImei(String vehicleDeviceImei) {
        this.vehicleDeviceImei = vehicleDeviceImei;
    }

    public String getSecondaryImei() {
        return secondaryImei;
    }

    public void setSecondaryImei(String secondaryImei) {
        this.secondaryImei = secondaryImei;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getAssertType() {
        return assertType;
    }

    public void setAssertType(String assertType) {
        this.assertType = assertType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVehicleGroup() {
        return vehicleGroup;
    }

    public void setVehicleGroup(String vehicleGroup) {
        this.vehicleGroup = vehicleGroup;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Integer getExpectedMileage() {
        return expectedMileage;
    }

    public void setExpectedMileage(Integer expectedMileage) {
        this.expectedMileage = expectedMileage;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public LocalDateTime getLastUpdatedDt() {
        return lastUpdatedDt;
    }

    public void setLastUpdatedDt(LocalDateTime lastUpdatedDt) {
        this.lastUpdatedDt = lastUpdatedDt;
    }

	public VehicleDetails(String vin, String userid, String plateno, String callsgincode, String vehicleDeviceImei,
			String secondaryImei, String vehicleType, String vehicleModel, String assertType, String year,
			String vehicleGroup, String color, String operatorName, Integer expectedMileage, String contractor,
			LocalDateTime createdAt, String lastUpdatedBy, LocalDateTime lastUpdatedDt) {
		super();
		this.vin = vin;
		this.userid = userid;
		this.plateno = plateno;
		this.callsgincode = callsgincode;
		this.vehicleDeviceImei = vehicleDeviceImei;
		this.secondaryImei = secondaryImei;
		this.vehicleType = vehicleType;
		this.vehicleModel = vehicleModel;
		this.assertType = assertType;
		this.year = year;
		this.vehicleGroup = vehicleGroup;
		this.color = color;
		this.operatorName = operatorName;
		this.expectedMileage = expectedMileage;
		this.contractor = contractor;
		this.createdAt = createdAt;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}
    
    
    
}

