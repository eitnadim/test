package com.eit.gateway.entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "companytrackdevice", schema = "mvt")
//@Getter
//@Setter
public class CompanyTrackDevice {
	
	public CompanyTrackDevice() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @Column(name = "imeino", nullable = false)
    private String imeiNo;

    @Column(name = "manufacturername")
    private String manufacturerName;

    @Column(name = "model")
    private String model;

    @Column(name = "dop")
    @Temporal(TemporalType.DATE)
    private Date dop;

    @Column(name = "companyid")
    private String companyId;

    @Column(name = "lastupdby")
    private String lastUpdBy;

    @Column(name = "lastupddt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdDt;

    @Column(name = "devicetype", columnDefinition = "TEXT")
    private String deviceType;

    @Column(name = "warrantyexpirydate")
    @Temporal(TemporalType.DATE)
    private Date warrantyExpiryDate;

    @Column(name = "remainder", length = 100, nullable = false)
    private String remainder = "";  // Default value

    @Column(name = "additionaldate")
    @Temporal(TemporalType.DATE)
    private Date additionalDate;

    @Column(name = "serialno", length = 30)
    private String serialNo;

    @Column(name = "simno", length = 20, nullable = false)
    private String simNo = "";  // Default value

    @Column(name = "device_status", length = 30)
    private String deviceStatus = "";  // Default value

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getDop() {
		return dop;
	}

	public void setDop(Date dop) {
		this.dop = dop;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Date getLastUpdDt() {
		return lastUpdDt;
	}

	public void setLastUpdDt(Date lastUpdDt) {
		this.lastUpdDt = lastUpdDt;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Date getWarrantyExpiryDate() {
		return warrantyExpiryDate;
	}

	public void setWarrantyExpiryDate(Date warrantyExpiryDate) {
		this.warrantyExpiryDate = warrantyExpiryDate;
	}

	public String getRemainder() {
		return remainder;
	}

	public void setRemainder(String remainder) {
		this.remainder = remainder;
	}

	public Date getAdditionalDate() {
		return additionalDate;
	}

	public void setAdditionalDate(Date additionalDate) {
		this.additionalDate = additionalDate;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public CompanyTrackDevice(String imeiNo, String manufacturerName, String model, Date dop, String companyId,
			String lastUpdBy, Date lastUpdDt, String deviceType, Date warrantyExpiryDate, String remainder,
			Date additionalDate, String serialNo, String simNo, String deviceStatus) {
		super();
		this.imeiNo = imeiNo;
		this.manufacturerName = manufacturerName;
		this.model = model;
		this.dop = dop;
		this.companyId = companyId;
		this.lastUpdBy = lastUpdBy;
		this.lastUpdDt = lastUpdDt;
		this.deviceType = deviceType;
		this.warrantyExpiryDate = warrantyExpiryDate;
		this.remainder = remainder;
		this.additionalDate = additionalDate;
		this.serialNo = serialNo;
		this.simNo = simNo;
		this.deviceStatus = deviceStatus;
	}
    
    
}

