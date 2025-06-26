package com.eit.gateway.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "vehicle_has_geozones", schema = "mvt", 
       indexes = {
           @Index(name = "idx_vehicle_has_geozones_vin", columnList = "vin"),
           @Index(name = "idx_vehicle_has_geozones_gezoneid_vin", columnList = "gezoneid, vin"),
           @Index(name = "idx_vehicle_has_geozones_gezoneid", columnList = "gezoneid")
       })
//@Getter
//@Setter
public class VehicleHasGeozones {
	
	
	public VehicleHasGeozones() {
		// TODO Auto-generated constructor stub
	}
	
	

    public VehicleHasGeozones(Long id, Long geozoneId, String vin, Boolean status, Boolean onLeave, Boolean onEnter,
			String mode, Boolean smsThrough, String mobileNumber, Boolean smsThrough1, String mobileNumber1,
			Boolean throughEmail, String emailAddress, Boolean throughEmail1, String emailAddress1, String alertDay,
			String alertTime, Date validityExp, String additionalInfo, String lastUpdatedBy, Date lastUpdatedDt,
			String notificationMode, String valid) {
		super();
		this.id = id;
		this.geozoneId = geozoneId;
		this.vin = vin;
		this.status = status;
		this.onLeave = onLeave;
		this.onEnter = onEnter;
		this.mode = mode;
		this.smsThrough = smsThrough;
		this.mobileNumber = mobileNumber;
		this.smsThrough1 = smsThrough1;
		this.mobileNumber1 = mobileNumber1;
		this.throughEmail = throughEmail;
		this.emailAddress = emailAddress;
		this.throughEmail1 = throughEmail1;
		this.emailAddress1 = emailAddress1;
		this.alertDay = alertDay;
		this.alertTime = alertTime;
		this.validityExp = validityExp;
		this.additionalInfo = additionalInfo;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
		this.notificationMode = notificationMode;
		this.valid = valid;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGeozoneId() {
		return geozoneId;
	}

	public void setGeozoneId(Long geozoneId) {
		this.geozoneId = geozoneId;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getOnLeave() {
		return onLeave;
	}

	public void setOnLeave(Boolean onLeave) {
		this.onLeave = onLeave;
	}

	public Boolean getOnEnter() {
		return onEnter;
	}

	public void setOnEnter(Boolean onEnter) {
		this.onEnter = onEnter;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Boolean getSmsThrough() {
		return smsThrough;
	}

	public void setSmsThrough(Boolean smsThrough) {
		this.smsThrough = smsThrough;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Boolean getSmsThrough1() {
		return smsThrough1;
	}

	public void setSmsThrough1(Boolean smsThrough1) {
		this.smsThrough1 = smsThrough1;
	}

	public String getMobileNumber1() {
		return mobileNumber1;
	}

	public void setMobileNumber1(String mobileNumber1) {
		this.mobileNumber1 = mobileNumber1;
	}

	public Boolean getThroughEmail() {
		return throughEmail;
	}

	public void setThroughEmail(Boolean throughEmail) {
		this.throughEmail = throughEmail;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Boolean getThroughEmail1() {
		return throughEmail1;
	}

	public void setThroughEmail1(Boolean throughEmail1) {
		this.throughEmail1 = throughEmail1;
	}

	public String getEmailAddress1() {
		return emailAddress1;
	}

	public void setEmailAddress1(String emailAddress1) {
		this.emailAddress1 = emailAddress1;
	}

	public String getAlertDay() {
		return alertDay;
	}

	public void setAlertDay(String alertDay) {
		this.alertDay = alertDay;
	}

	public String getAlertTime() {
		return alertTime;
	}

	public void setAlertTime(String alertTime) {
		this.alertTime = alertTime;
	}

	public Date getValidityExp() {
		return validityExp;
	}

	public void setValidityExp(Date validityExp) {
		this.validityExp = validityExp;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(Date lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	public String getNotificationMode() {
		return notificationMode;
	}

	public void setNotificationMode(String notificationMode) {
		this.notificationMode = notificationMode;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}



	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gezoneid", nullable = false)
    private Long geozoneId;

    @Column(name = "vin", nullable = false, length = 45)
    private String vin;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "onleave")
    private Boolean onLeave;

    @Column(name = "onenter")
    private Boolean onEnter;

    @Column(name = "mode", length = 45)
    private String mode;

    @Column(name = "smsthrough")
    private Boolean smsThrough;

    @Column(name = "mobilenumber", length = 20)
    private String mobileNumber;

    @Column(name = "smsthrough1")
    private Boolean smsThrough1;

    @Column(name = "mobilenumber1", length = 20)
    private String mobileNumber1;

    @Column(name = "throughemail")
    private Boolean throughEmail;

    @Column(name = "emailaddress", length = 100)
    private String emailAddress;

    @Column(name = "throughemail1")
    private Boolean throughEmail1;

    @Column(name = "emailaddress1", length = 100)
    private String emailAddress1;

    @Column(name = "alertday", length = 45)
    private String alertDay;

    @Column(name = "alerttime", length = 45)
    private String alertTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "validityexp")
    private Date validityExp;

    @Column(name = "additionalinfo", columnDefinition = "TEXT")
    private String additionalInfo;

    @Column(name = "lastupdby", length = 45)
    private String lastUpdatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastupddt")
    private Date lastUpdatedDt;

    @Column(name = "notificationmode", length = 250)
    private String notificationMode;

    @Column(name = "valid", length = 255)
    private String valid;

}

