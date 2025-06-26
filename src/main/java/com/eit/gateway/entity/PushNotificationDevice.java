package com.eit.gateway.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "com_pushnotificationdevices", schema = "mvt")
//@Getter
//@Setter
public class PushNotificationDevice {
	
	public PushNotificationDevice() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @Column(name = "appname", length = 45, nullable = false)
    private String appName;

    @Id
    @Column(name = "imei", length = 200, nullable = false)
    private String imei;

    @Column(name = "device_id", length = 500, nullable = false)
    private String deviceId;

    @Column(name = "os", length = 45, nullable = false)
    private String os;

    @Column(name = "comp_id", length = 20, nullable = false)
    private String compId;

    @Column(name = "user_id", length = 45, nullable = false)
    private String userId;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "logintime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginTime;

    @Column(name = "logouttime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date logoutTime;

    @Column(name = "push_lng", length = 45)
    private String pushLng;

    @Column(name = "pushnotenable", nullable = true)
    private Boolean pushNotEnable = true;  // Default value

    @Column(name = "pushsetting", nullable = false)
    private Boolean pushSetting = false;  // Default value

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getPushLng() {
		return pushLng;
	}

	public void setPushLng(String pushLng) {
		this.pushLng = pushLng;
	}

	public Boolean getPushNotEnable() {
		return pushNotEnable;
	}

	public void setPushNotEnable(Boolean pushNotEnable) {
		this.pushNotEnable = pushNotEnable;
	}

	public Boolean getPushSetting() {
		return pushSetting;
	}

	public void setPushSetting(Boolean pushSetting) {
		this.pushSetting = pushSetting;
	}

	public PushNotificationDevice(String appName, String imei, String deviceId, String os, String compId, String userId,
			Boolean status, Date loginTime, Date logoutTime, String pushLng, Boolean pushNotEnable,
			Boolean pushSetting) {
		super();
		this.appName = appName;
		this.imei = imei;
		this.deviceId = deviceId;
		this.os = os;
		this.compId = compId;
		this.userId = userId;
		this.status = status;
		this.loginTime = loginTime;
		this.logoutTime = logoutTime;
		this.pushLng = pushLng;
		this.pushNotEnable = pushNotEnable;
		this.pushSetting = pushSetting;
	}
    
    
    

}

