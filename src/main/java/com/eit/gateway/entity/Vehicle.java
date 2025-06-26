package com.eit.gateway.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicle", schema = "mvt")
public class Vehicle {

	public Vehicle(String vin, String companyId, String userId, String plateNo, String vehicleTypeVehicleType,
			String vehicleGroup, String vehicleModelModel, String vehicleDeviceImei, Date dateOfPurchase,
			Date defaultWarrantyExpiryDate, String regNo, Date regDate, Date regExpiryDate, String insuranceNo,
			Date insuranceExpiryDate, String oprEare, String mileageInit, String mileageCur, String imgUrl,
			String iconUrl, Date additionalWarranty, Integer geoCnt, String lastUpdBy, Date lastUpdDt, String ioSetting,
			String contactNo, String fuelTankLit, String fuelTankMv, String ioDetails, String status,
			String hourMeterValue, Integer capacity, Integer expectedMileage, Boolean active, Integer lockStatus,
			String preventiveMaintenance, String scheduledMaintenance, String restDay, String commandsJson,
			Integer overallCmdStatus, String reverseSetting, String hierarchyStructure, Date vTimestamp,
			Boolean vHeartbeatEngine, String vGps, Integer vGsm, String vBatteryVoltage, String vPowerSupply,
			String vRemarks, Date vEventTimestamp, Date vServerTimestamp, Double vLatitude, Double vLongitude,
			Integer vSpeed, Integer vPriority, String vIoEvent, Long vBytesTrx, Boolean vEngine, Long vTempSensor1,
			Long vTempSensor2, Long vTempSensor3, Long vTempSensor4, Long vOdometer, Long vBattery, Integer vDirection,
			Integer vEventSource, Integer vAi1, Integer vAi2, Integer vAi3, Integer vAi4, Integer vDi1, Integer vDi2,
			Integer vDi3, Integer vDi4, String vTags, String contractor, String vehicleStatus,
			String preventiveMaintenanceType, String vehicleStatusType, Long engineHours, String groupColor,
			String eventStatus, Long statusDuration, Long totalEngineHours, String ticketDetails, Integer watchMode,
			String gensetCapacity, String initialTrans, Double vDEvent1, Double vDEvent2, Double vDEvent3,
			Double vDEvent4, String location, Long todayOdometer, String packetJson, String packetHex, String height,
			Integer speedLimit, String fatigueDuration, String region, String model, Date ignitionLastUpdate,
			String overspeedRange, String assetInformation, String operatorName, Date temp1LastTransmission,
			String vehicleReferenceKey, Date lastEventUpd, String vehicleRemark, String ioConfig, String branchId) {
		super();
		this.vin = vin;
		this.companyId = companyId;
		this.userId = userId;
		this.plateNo = plateNo;
		this.vehicleTypeVehicleType = vehicleTypeVehicleType;
		this.vehicleGroup = vehicleGroup;
		this.vehicleModelModel = vehicleModelModel;
		this.vehicleDeviceImei = vehicleDeviceImei;
		this.dateOfPurchase = dateOfPurchase;
		this.defaultWarrantyExpiryDate = defaultWarrantyExpiryDate;
		this.regNo = regNo;
		this.regDate = regDate;
		this.regExpiryDate = regExpiryDate;
		this.insuranceNo = insuranceNo;
		this.insuranceExpiryDate = insuranceExpiryDate;
		this.oprEare = oprEare;
		this.mileageInit = mileageInit;
		this.mileageCur = mileageCur;
		this.imgUrl = imgUrl;
		this.iconUrl = iconUrl;
		this.additionalWarranty = additionalWarranty;
		this.geoCnt = geoCnt;
		this.lastUpdBy = lastUpdBy;
		this.lastUpdDt = lastUpdDt;
		this.ioSetting = ioSetting;
		this.contactNo = contactNo;
		this.fuelTankLit = fuelTankLit;
		this.fuelTankMv = fuelTankMv;
		this.ioDetails = ioDetails;
		this.status = status;
		this.hourMeterValue = hourMeterValue;
		this.capacity = capacity;
		this.expectedMileage = expectedMileage;
		this.active = active;
		this.lockStatus = lockStatus;
		this.preventiveMaintenance = preventiveMaintenance;
		this.scheduledMaintenance = scheduledMaintenance;
		this.restDay = restDay;
		this.commandsJson = commandsJson;
		this.overallCmdStatus = overallCmdStatus;
		this.reverseSetting = reverseSetting;
		this.hierarchyStructure = hierarchyStructure;
		this.vTimestamp = vTimestamp;
		this.vHeartbeatEngine = vHeartbeatEngine;
		this.vGps = vGps;
		this.vGsm = vGsm;
		this.vBatteryVoltage = vBatteryVoltage;
		this.vPowerSupply = vPowerSupply;
		this.vRemarks = vRemarks;
		this.vEventTimestamp = vEventTimestamp;
		this.vServerTimestamp = vServerTimestamp;
		this.vLatitude = vLatitude;
		this.vLongitude = vLongitude;
		this.vSpeed = vSpeed;
		this.vPriority = vPriority;
		this.vIoEvent = vIoEvent;
		this.vBytesTrx = vBytesTrx;
		this.vEngine = vEngine;
		this.vTempSensor1 = vTempSensor1;
		this.vTempSensor2 = vTempSensor2;
		this.vTempSensor3 = vTempSensor3;
		this.vTempSensor4 = vTempSensor4;
		this.vOdometer = vOdometer;
		this.vBattery = vBattery;
		this.vDirection = vDirection;
		this.vEventSource = vEventSource;
		this.vAi1 = vAi1;
		this.vAi2 = vAi2;
		this.vAi3 = vAi3;
		this.vAi4 = vAi4;
		this.vDi1 = vDi1;
		this.vDi2 = vDi2;
		this.vDi3 = vDi3;
		this.vDi4 = vDi4;
		this.vTags = vTags;
		this.contractor = contractor;
		this.vehicleStatus = vehicleStatus;
		this.preventiveMaintenanceType = preventiveMaintenanceType;
		this.vehicleStatusType = vehicleStatusType;
		this.engineHours = engineHours;
		this.groupColor = groupColor;
		this.eventStatus = eventStatus;
		this.statusDuration = statusDuration;
		this.totalEngineHours = totalEngineHours;
		this.ticketDetails = ticketDetails;
		this.watchMode = watchMode;
		this.gensetCapacity = gensetCapacity;
		this.initialTrans = initialTrans;
		this.vDEvent1 = vDEvent1;
		this.vDEvent2 = vDEvent2;
		this.vDEvent3 = vDEvent3;
		this.vDEvent4 = vDEvent4;
		this.location = location;
		this.todayOdometer = todayOdometer;
		this.packetJson = packetJson;
		this.packetHex = packetHex;
		this.height = height;
		this.speedLimit = speedLimit;
		this.fatigueDuration = fatigueDuration;
		this.region = region;
		this.model = model;
		this.ignitionLastUpdate = ignitionLastUpdate;
		this.overspeedRange = overspeedRange;
		this.assetInformation = assetInformation;
		this.operatorName = operatorName;
		this.temp1LastTransmission = temp1LastTransmission;
		this.vehicleReferenceKey = vehicleReferenceKey;
		this.lastEventUpd = lastEventUpd;
		this.vehicleRemark = vehicleRemark;
		this.ioConfig = ioConfig;
		this.branchId = branchId;
	}

	@Id
	@Column(name = "vin", nullable = false, length = 20)
	private String vin;

	@Column(name = "companyid", nullable = false, length = 45)
	private String companyId;

	@Column(name = "userid")
	private String userId;

	@Column(name = "plateno", length = 200)
	private String plateNo;

	@Column(name = "vehicletype_vehicletype", length = 45)
	private String vehicleTypeVehicleType;

	@Column(name = "vehiclegroup", length = 45)
	private String vehicleGroup;

	@Column(name = "vehiclemodel_model", length = 50)
	private String vehicleModelModel;

	@Column(name = "vehicledeviceimei")
	private String vehicleDeviceImei;

	@Column(name = "dateofpurchase")
	private Date dateOfPurchase;

	@Column(name = "defaultwarrantyexpirydate")
	private Date defaultWarrantyExpiryDate;

	@Column(name = "regno", length = 45)
	private String regNo;

	@Column(name = "regdate")
	private Date regDate;

	@Column(name = "regexpirydate")
	private Date regExpiryDate;

	@Column(name = "insuranceno", length = 45)
	private String insuranceNo;

	@Column(name = "insuranceexpirydate")
	private Date insuranceExpiryDate;

	@Column(name = "opreare", length = 100)
	private String oprEare;

	@Column(name = "mileageinit", length = 25)
	private String mileageInit;

	@Column(name = "mileagecur", length = 25)
	private String mileageCur;

	@Column(name = "imgurl", length = 300)
	private String imgUrl;

	@Column(name = "iconurl", length = 300)
	private String iconUrl;

	@Column(name = "additionalwarranty")
	private Date additionalWarranty;

	@Column(name = "geocnt")
	private Integer geoCnt;

	@Column(name = "lastupdby", length = 45)
	private String lastUpdBy;

	@Column(name = "lastupddt")
	private Date lastUpdDt;

	@Column(name = "iosetting", length = 900)
	private String ioSetting;

	@Column(name = "contactno", length = 45)
	private String contactNo;

	@Column(name = "fueltanklit", length = 45)
	private String fuelTankLit;

	@Column(name = "fueltankmv", length = 45)
	private String fuelTankMv;

	@Column(name = "iodetails", length = 900)
	private String ioDetails;

	@Column(name = "status", length = 45)
	private String status;

	@Column(name = "hourmetervalue", length = 45)
	private String hourMeterValue;

	@Column(name = "capacity")
	private Integer capacity;

	@Column(name = "expectedmileage")
	private Integer expectedMileage;

	@Column(name = "active")
	private Boolean active = false;

	@Column(name = "lockstatus")
	private Integer lockStatus = 0;

	@Column(name = "preventivemaintenance", length = 45)
	private String preventiveMaintenance;

	@Column(name = "scheduledmaintenance", length = 45)
	private String scheduledMaintenance;

	@Column(name = "restday", length = 200)
	private String restDay;

	@Column(name = "commandsjson", length = 1200)
	private String commandsJson;

	@Column(name = "overallcmdstatus")
	private Integer overallCmdStatus = 1;

	@Column(name = "reversesetting", length = 25)
	private String reverseSetting;

	@Column(name = "hierarchystructure", length = 50)
	private String hierarchyStructure;

	@Column(name = "v_timestamp")
	private Date vTimestamp;

	@Column(name = "v_heartbeatengine")
	private Boolean vHeartbeatEngine;

	@Column(name = "v_gps", length = 10)
	private String vGps;

	@Column(name = "v_gsm")
	private Integer vGsm;

	@Column(name = "v_batteryvoltage", length = 20)
	private String vBatteryVoltage;

	@Column(name = "v_powersupply", length = 10)
	private String vPowerSupply;

	@Column(name = "v_remarks", length = 1200)
	private String vRemarks;

	@Column(name = "v_eventtimestamp")
	private Date vEventTimestamp;

	@Column(name = "v_servertimestamp")
	private Date vServerTimestamp;

	@Column(name = "v_latitude")
	private Double vLatitude;

	@Column(name = "v_longitude")
	private Double vLongitude;

	@Column(name = "v_speed")
	private Integer vSpeed;

	@Column(name = "v_priority")
	private Integer vPriority;

	@Column(name = "v_ioevent", length = 3000)
	private String vIoEvent;

	@Column(name = "v_bytestrx")
	private Long vBytesTrx;

	@Column(name = "v_engine")
	private Boolean vEngine;

	@Column(name = "v_tempsensor1")
	private Long vTempSensor1;

	@Column(name = "v_tempsensor2")
	private Long vTempSensor2;

	@Column(name = "v_tempsensor3")
	private Long vTempSensor3;

	@Column(name = "v_tempsensor4")
	private Long vTempSensor4;

	@Column(name = "v_odometer")
	private Long vOdometer;

	@Column(name = "v_battery")
	private Long vBattery;

	@Column(name = "v_direction")
	private Integer vDirection;

	@Column(name = "v_eventsource")
	private Integer vEventSource;

	@Column(name = "v_ai1")
	private Integer vAi1;

	@Column(name = "v_ai2")
	private Integer vAi2;

	@Column(name = "v_ai3")
	private Integer vAi3;

	@Column(name = "v_ai4")
	private Integer vAi4;

	@Column(name = "v_di1")
	private Integer vDi1;

	@Column(name = "v_di2")
	private Integer vDi2;

	@Column(name = "v_di3")
	private Integer vDi3;

	@Column(name = "v_di4")
	private Integer vDi4;

	@Column(name = "v_tags", length = 1000)
	private String vTags;

	@Column(name = "contractor", length = 45)
	private String contractor;

	@Column(name = "vehiclestatus", length = 500)
	private String vehicleStatus;

	@Column(name = "preventivemaintenancetype", length = 25)
	private String preventiveMaintenanceType;

	@Column(name = "vehiclestatustype", length = 200)
	private String vehicleStatusType;

	@Column(name = "enginehours")
	private Long engineHours;

	@Column(name = "group_color", length = 20)
	private String groupColor;

	@Column(name = "eventstatus", length = 100)
	private String eventStatus;

	@Column(name = "statusduration")
	private Long statusDuration = 0L;

	@Column(name = "totalenginehours")
	private Long totalEngineHours = 0L;

	@Column(name = "ticketdetails", length = 500)
	private String ticketDetails;

	@Column(name = "watchmode")
	private Integer watchMode = 0;

	@Column(name = "gensetcapacity", length = 500)
	private String gensetCapacity;

	@Column(name = "initialtrans", length = 50)
	private String initialTrans;

	@Column(name = "v_d_event1")
	private Double vDEvent1;

	@Column(name = "v_d_event2")
	private Double vDEvent2;

	@Column(name = "v_d_event3")
	private Double vDEvent3;

	@Column(name = "v_d_event4")
	private Double vDEvent4;

	@Column(name = "location", length = 500)
	private String location;

	@Column(name = "todayodometer")
	private Long todayOdometer = 0L;

	@Column(name = "packetjson", length = 3000)
	private String packetJson;

	@Column(name = "packethex", length = 3000)
	private String packetHex;

	@Column(name = "height", length = 50)
	private String height;

	@Column(name = "speedlimit")
	private Integer speedLimit;

	@Column(name = "fatigueduration", length = 500)
	private String fatigueDuration;

	@Column(name = "region", length = 500)
	private String region;

	@Column(name = "model", length = 500)
	private String model;

	@Column(name = "ignitionlastupdate")
	private Date ignitionLastUpdate;

	@Column(name = "overspeedrange", length = 100)
	private String overspeedRange;

	@Column(name = "assetinformation")
	private String assetInformation;

	@Column(name = "operatorname", length = 60)
	private String operatorName;

	@Column(name = "temp1lasttransmission")
	private Date temp1LastTransmission;

	@Column(name = "vehiclereferencekey", length = 50)
	private String vehicleReferenceKey;

	@Column(name = "lasteventupd")
	private Date lastEventUpd;

	@Column(name = "vehicleremark", length = 255)
	private String vehicleRemark;

	@Column(name = "io_config")
	private String ioConfig;

	@Column(name = "branchid", length = 45)
	private String branchId;

	@Column(name = "processed_ioevent")
	private String ProcessedIoEvent;

	public String getProcessedIoEvent() {
		return ProcessedIoEvent;
	}

	public void setProcessedIoEvent(String processedIoEvent) {
		ProcessedIoEvent = processedIoEvent;
	}

	// Default constructor
	public Vehicle() {
	}

	// Getters and Setters
	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getVehicleTypeVehicleType() {
		return vehicleTypeVehicleType;
	}

	public void setVehicleTypeVehicleType(String vehicleTypeVehicleType) {
		this.vehicleTypeVehicleType = vehicleTypeVehicleType;
	}

	public String getVehicleGroup() {
		return vehicleGroup;
	}

	public void setVehicleGroup(String vehicleGroup) {
		this.vehicleGroup = vehicleGroup;
	}

	public String getVehicleModelModel() {
		return vehicleModelModel;
	}

	public void setVehicleModelModel(String vehicleModelModel) {
		this.vehicleModelModel = vehicleModelModel;
	}

	public String getVehicleDeviceImei() {
		return vehicleDeviceImei;
	}

	public void setVehicleDeviceImei(String vehicleDeviceImei) {
		this.vehicleDeviceImei = vehicleDeviceImei;
	}

	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public Date getDefaultWarrantyExpiryDate() {
		return defaultWarrantyExpiryDate;
	}

	public void setDefaultWarrantyExpiryDate(Date defaultWarrantyExpiryDate) {
		this.defaultWarrantyExpiryDate = defaultWarrantyExpiryDate;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getRegExpiryDate() {
		return regExpiryDate;
	}

	public void setRegExpiryDate(Date regExpiryDate) {
		this.regExpiryDate = regExpiryDate;
	}

	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public Date getInsuranceExpiryDate() {
		return insuranceExpiryDate;
	}

	public void setInsuranceExpiryDate(Date insuranceExpiryDate) {
		this.insuranceExpiryDate = insuranceExpiryDate;
	}

	public String getOprEare() {
		return oprEare;
	}

	public void setOprEare(String oprEare) {
		this.oprEare = oprEare;
	}

	public String getMileageInit() {
		return mileageInit;
	}

	public void setMileageInit(String mileageInit) {
		this.mileageInit = mileageInit;
	}

	public String getMileageCur() {
		return mileageCur;
	}

	public void setMileageCur(String mileageCur) {
		this.mileageCur = mileageCur;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Date getAdditionalWarranty() {
		return additionalWarranty;
	}

	public void setAdditionalWarranty(Date additionalWarranty) {
		this.additionalWarranty = additionalWarranty;
	}

	public Integer getGeoCnt() {
		return geoCnt;
	}

	public void setGeoCnt(Integer geoCnt) {
		this.geoCnt = geoCnt;
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

	public String getIoSetting() {
		return ioSetting;
	}

	public void setIoSetting(String ioSetting) {
		this.ioSetting = ioSetting;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getFuelTankLit() {
		return fuelTankLit;
	}

	public void setFuelTankLit(String fuelTankLit) {
		this.fuelTankLit = fuelTankLit;
	}

	public String getFuelTankMv() {
		return fuelTankMv;
	}

	public void setFuelTankMv(String fuelTankMv) {
		this.fuelTankMv = fuelTankMv;
	}

	public String getIoDetails() {
		return ioDetails;
	}

	public void setIoDetails(String ioDetails) {
		this.ioDetails = ioDetails;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHourMeterValue() {
		return hourMeterValue;
	}

	public void setHourMeterValue(String hourMeterValue) {
		this.hourMeterValue = hourMeterValue;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getExpectedMileage() {
		return expectedMileage;
	}

	public void setExpectedMileage(Integer expectedMileage) {
		this.expectedMileage = expectedMileage;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getPreventiveMaintenance() {
		return preventiveMaintenance;
	}

	public void setPreventiveMaintenance(String preventiveMaintenance) {
		this.preventiveMaintenance = preventiveMaintenance;
	}

	public String getScheduledMaintenance() {
		return scheduledMaintenance;
	}

	public void setScheduledMaintenance(String scheduledMaintenance) {
		this.scheduledMaintenance = scheduledMaintenance;
	}

	public String getRestDay() {
		return restDay;
	}

	public void setRestDay(String restDay) {
		this.restDay = restDay;
	}

	public String getCommandsJson() {
		return commandsJson;
	}

	public void setCommandsJson(String commandsJson) {
		this.commandsJson = commandsJson;
	}

	public Integer getOverallCmdStatus() {
		return overallCmdStatus;
	}

	public void setOverallCmdStatus(Integer overallCmdStatus) {
		this.overallCmdStatus = overallCmdStatus;
	}

	public String getReverseSetting() {
		return reverseSetting;
	}

	public void setReverseSetting(String reverseSetting) {
		this.reverseSetting = reverseSetting;
	}

	public String getHierarchyStructure() {
		return hierarchyStructure;
	}

	public void setHierarchyStructure(String hierarchyStructure) {
		this.hierarchyStructure = hierarchyStructure;
	}

	public Date getVTimestamp() {
		return vTimestamp;
	}

	public void setVTimestamp(Date vTimestamp) {
		this.vTimestamp = vTimestamp;
	}

	public Boolean getVHeartbeatEngine() {
		return vHeartbeatEngine;
	}

	public void setVHeartbeatEngine(Boolean vHeartbeatEngine) {
		this.vHeartbeatEngine = vHeartbeatEngine;
	}

	public String getVGps() {
		return vGps;
	}

	public void setVGps(String vGps) {
		this.vGps = vGps;
	}

	public Integer getVGsm() {
		return vGsm;
	}

	public void setVGsm(Integer vGsm) {
		this.vGsm = vGsm;
	}

	public String getVBatteryVoltage() {
		return vBatteryVoltage;
	}

	public void setVBatteryVoltage(String vBatteryVoltage) {
		this.vBatteryVoltage = vBatteryVoltage;
	}

	public String getVPowerSupply() {
		return vPowerSupply;
	}

	public void setVPowerSupply(String vPowerSupply) {
		this.vPowerSupply = vPowerSupply;
	}

	public String getVRemarks() {
		return vRemarks;
	}

	public void setVRemarks(String vRemarks) {
		this.vRemarks = vRemarks;
	}

	public Date getVEventTimestamp() {
		return vEventTimestamp;
	}

	public void setVEventTimestamp(Date vEventTimestamp) {
		this.vEventTimestamp = vEventTimestamp;
	}

	public Date getVServerTimestamp() {
		return vServerTimestamp;
	}

	public void setVServerTimestamp(Date vServerTimestamp) {
		this.vServerTimestamp = vServerTimestamp;
	}

	public Double getVLatitude() {
		return vLatitude;
	}

	public void setVLatitude(Double vLatitude) {
		this.vLatitude = vLatitude;
	}

	public Double getVLongitude() {
		return vLongitude;
	}

	public void setVLongitude(Double vLongitude) {
		this.vLongitude = vLongitude;
	}

	public Integer getVSpeed() {
		return vSpeed;
	}

	public void setVSpeed(Integer vSpeed) {
		this.vSpeed = vSpeed;
	}

	public Integer getVPriority() {
		return vPriority;
	}

	public void setVPriority(Integer vPriority) {
		this.vPriority = vPriority;
	}

	public String getVIoEvent() {
		return vIoEvent;
	}

	public void setVIoEvent(String vIoEvent) {
		this.vIoEvent = vIoEvent;
	}

	public Long getVBytesTrx() {
		return vBytesTrx;
	}

	public void setVBytesTrx(Long vBytesTrx) {
		this.vBytesTrx = vBytesTrx;
	}

	public Boolean getVEngine() {
		return vEngine;
	}

	public void setVEngine(Boolean vEngine) {
		this.vEngine = vEngine;
	}

	public Long getVTempSensor1() {
		return vTempSensor1;
	}

	public void setVTempSensor1(Long vTempSensor1) {
		this.vTempSensor1 = vTempSensor1;
	}

	public Long getVTempSensor2() {
		return vTempSensor2;
	}

	public void setVTempSensor2(Long vTempSensor2) {
		this.vTempSensor2 = vTempSensor2;
	}

	public Long getVTempSensor3() {
		return vTempSensor3;
	}

	public void setVTempSensor3(Long vTempSensor3) {
		this.vTempSensor3 = vTempSensor3;
	}

	public Long getVTempSensor4() {
		return vTempSensor4;
	}

	public void setVTempSensor4(Long vTempSensor4) {
		this.vTempSensor4 = vTempSensor4;
	}

	public Long getVOdometer() {
		return vOdometer;
	}

	public void setVOdometer(Long vOdometer) {
		this.vOdometer = vOdometer;
	}

	public Long getVBattery() {
		return vBattery;
	}

	public void setVBattery(Long vBattery) {
		this.vBattery = vBattery;
	}

	public Integer getVDirection() {
		return vDirection;
	}

	public void setVDirection(Integer vDirection) {
		this.vDirection = vDirection;
	}

	public Integer getVEventSource() {
		return vEventSource;
	}

	public void setVEventSource(Integer vEventSource) {
		this.vEventSource = vEventSource;
	}

	public Integer getVAi1() {
		return vAi1;
	}

	public void setVAi1(Integer vAi1) {
		this.vAi1 = vAi1;
	}

	public Integer getVAi2() {
		return vAi2;
	}

	public void setVAi2(Integer vAi2) {
		this.vAi2 = vAi2;
	}

	public Integer getVAi3() {
		return vAi3;
	}

	public void setVAi3(Integer vAi3) {
		this.vAi3 = vAi3;
	}

	public Integer getVAi4() {
		return vAi4;
	}

	public void setVAi4(Integer vAi4) {
		this.vAi4 = vAi4;
	}

	public Integer getVDi1() {
		return vDi1;
	}

	public void setVDi1(Integer vDi1) {
		this.vDi1 = vDi1;
	}

	public Integer getVDi2() {
		return vDi2;
	}

	public void setVDi2(Integer vDi2) {
		this.vDi2 = vDi2;
	}

	public Integer getVDi3() {
		return vDi3;
	}

	public void setVDi3(Integer vDi3) {
		this.vDi3 = vDi3;
	}

	public Integer getVDi4() {
		return vDi4;
	}

	public void setVDi4(Integer vDi4) {
		this.vDi4 = vDi4;
	}

	public String getVTags() {
		return vTags;
	}

	public void setVTags(String vTags) {
		this.vTags = vTags;
	}

	public String getContractor() {
		return contractor;
	}

	public void setContractor(String contractor) {
		this.contractor = contractor;
	}

	public String getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public String getPreventiveMaintenanceType() {
		return preventiveMaintenanceType;
	}

	public void setPreventiveMaintenanceType(String preventiveMaintenanceType) {
		this.preventiveMaintenanceType = preventiveMaintenanceType;
	}

	public String getVehicleStatusType() {
		return vehicleStatusType;
	}

	public void setVehicleStatusType(String vehicleStatusType) {
		this.vehicleStatusType = vehicleStatusType;
	}

	public Long getEngineHours() {
		return engineHours;
	}

	public void setEngineHours(Long engineHours) {
		this.engineHours = engineHours;
	}

	public String getGroupColor() {
		return groupColor;
	}

	public void setGroupColor(String groupColor) {
		this.groupColor = groupColor;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public Long getStatusDuration() {
		return statusDuration;
	}

	public void setStatusDuration(Long statusDuration) {
		this.statusDuration = statusDuration;
	}

	public Long getTotalEngineHours() {
		return totalEngineHours;
	}

	public void setTotalEngineHours(Long totalEngineHours) {
		this.totalEngineHours = totalEngineHours;
	}

	public String getTicketDetails() {
		return ticketDetails;
	}

	public void setTicketDetails(String ticketDetails) {
		this.ticketDetails = ticketDetails;
	}

	public Integer getWatchMode() {
		return watchMode;
	}

	public void setWatchMode(Integer watchMode) {
		this.watchMode = watchMode;
	}

	public String getGensetCapacity() {
		return gensetCapacity;
	}

	public void setGensetCapacity(String gensetCapacity) {
		this.gensetCapacity = gensetCapacity;
	}

	public String getInitialTrans() {
		return initialTrans;
	}

	public void setInitialTrans(String initialTrans) {
		this.initialTrans = initialTrans;
	}

	public Double getVDEvent1() {
		return vDEvent1;
	}

	public void setVDEvent1(Double vDEvent1) {
		this.vDEvent1 = vDEvent1;
	}

	public Double getVDEvent2() {
		return vDEvent2;
	}

	public void setVDEvent2(Double vDEvent2) {
		this.vDEvent2 = vDEvent2;
	}

	public Double getVDEvent3() {
		return vDEvent3;
	}

	public void setVDEvent3(Double vDEvent3) {
		this.vDEvent3 = vDEvent3;
	}

	public Double getVDEvent4() {
		return vDEvent4;
	}

	public Date getvTimestamp() {
		return vTimestamp;
	}

	public void setvTimestamp(Date vTimestamp) {
		this.vTimestamp = vTimestamp;
	}

	public Boolean getvHeartbeatEngine() {
		return vHeartbeatEngine;
	}

	public void setvHeartbeatEngine(Boolean vHeartbeatEngine) {
		this.vHeartbeatEngine = vHeartbeatEngine;
	}

	public String getvGps() {
		return vGps;
	}

	public void setvGps(String vGps) {
		this.vGps = vGps;
	}

	public Integer getvGsm() {
		return vGsm;
	}

	public void setvGsm(Integer vGsm) {
		this.vGsm = vGsm;
	}

	public String getvBatteryVoltage() {
		return vBatteryVoltage;
	}

	public void setvBatteryVoltage(String vBatteryVoltage) {
		this.vBatteryVoltage = vBatteryVoltage;
	}

	public String getvPowerSupply() {
		return vPowerSupply;
	}

	public void setvPowerSupply(String vPowerSupply) {
		this.vPowerSupply = vPowerSupply;
	}

	public String getvRemarks() {
		return vRemarks;
	}

	public void setvRemarks(String vRemarks) {
		this.vRemarks = vRemarks;
	}

	public Date getvEventTimestamp() {
		return vEventTimestamp;
	}

	public void setvEventTimestamp(Date vEventTimestamp) {
		this.vEventTimestamp = vEventTimestamp;
	}

	public Date getvServerTimestamp() {
		return vServerTimestamp;
	}

	public void setvServerTimestamp(Date vServerTimestamp) {
		this.vServerTimestamp = vServerTimestamp;
	}

	public Double getvLatitude() {
		return vLatitude;
	}

	public void setvLatitude(Double vLatitude) {
		this.vLatitude = vLatitude;
	}

	public Double getvLongitude() {
		return vLongitude;
	}

	public void setvLongitude(Double vLongitude) {
		this.vLongitude = vLongitude;
	}

	public Integer getvSpeed() {
		return vSpeed;
	}

	public void setvSpeed(Integer vSpeed) {
		this.vSpeed = vSpeed;
	}

	public Integer getvPriority() {
		return vPriority;
	}

	public void setvPriority(Integer vPriority) {
		this.vPriority = vPriority;
	}

	public String getvIoEvent() {
		return vIoEvent;
	}

	public void setvIoEvent(String vIoEvent) {
		this.vIoEvent = vIoEvent;
	}

	public Long getvBytesTrx() {
		return vBytesTrx;
	}

	public void setvBytesTrx(Long vBytesTrx) {
		this.vBytesTrx = vBytesTrx;
	}

	public Boolean getvEngine() {
		return vEngine;
	}

	public void setvEngine(Boolean vEngine) {
		this.vEngine = vEngine;
	}

	public Long getvTempSensor1() {
		return vTempSensor1;
	}

	public void setvTempSensor1(Long vTempSensor1) {
		this.vTempSensor1 = vTempSensor1;
	}

	public Long getvTempSensor2() {
		return vTempSensor2;
	}

	public void setvTempSensor2(Long vTempSensor2) {
		this.vTempSensor2 = vTempSensor2;
	}

	public Long getvTempSensor3() {
		return vTempSensor3;
	}

	public void setvTempSensor3(Long vTempSensor3) {
		this.vTempSensor3 = vTempSensor3;
	}

	public Long getvTempSensor4() {
		return vTempSensor4;
	}

	public void setvTempSensor4(Long vTempSensor4) {
		this.vTempSensor4 = vTempSensor4;
	}

	public Long getvOdometer() {
		return vOdometer;
	}

	public void setvOdometer(Long vOdometer) {
		this.vOdometer = vOdometer;
	}

	public Long getvBattery() {
		return vBattery;
	}

	public void setvBattery(Long vBattery) {
		this.vBattery = vBattery;
	}

	public Integer getvDirection() {
		return vDirection;
	}

	public void setvDirection(Integer vDirection) {
		this.vDirection = vDirection;
	}

	public Integer getvEventSource() {
		return vEventSource;
	}

	public void setvEventSource(Integer vEventSource) {
		this.vEventSource = vEventSource;
	}

	public Integer getvAi1() {
		return vAi1;
	}

	public void setvAi1(Integer vAi1) {
		this.vAi1 = vAi1;
	}

	public Integer getvAi2() {
		return vAi2;
	}

	public void setvAi2(Integer vAi2) {
		this.vAi2 = vAi2;
	}

	public Integer getvAi3() {
		return vAi3;
	}

	public void setvAi3(Integer vAi3) {
		this.vAi3 = vAi3;
	}

	public Integer getvAi4() {
		return vAi4;
	}

	public void setvAi4(Integer vAi4) {
		this.vAi4 = vAi4;
	}

	public Integer getvDi1() {
		return vDi1;
	}

	public void setvDi1(Integer vDi1) {
		this.vDi1 = vDi1;
	}

	public Integer getvDi2() {
		return vDi2;
	}

	public void setvDi2(Integer vDi2) {
		this.vDi2 = vDi2;
	}

	public Integer getvDi3() {
		return vDi3;
	}

	public void setvDi3(Integer vDi3) {
		this.vDi3 = vDi3;
	}

	public Integer getvDi4() {
		return vDi4;
	}

	public void setvDi4(Integer vDi4) {
		this.vDi4 = vDi4;
	}

	public String getvTags() {
		return vTags;
	}

	public void setvTags(String vTags) {
		this.vTags = vTags;
	}

	public Double getvDEvent1() {
		return vDEvent1;
	}

	public void setvDEvent1(Double vDEvent1) {
		this.vDEvent1 = vDEvent1;
	}

	public Double getvDEvent2() {
		return vDEvent2;
	}

	public void setvDEvent2(Double vDEvent2) {
		this.vDEvent2 = vDEvent2;
	}

	public Double getvDEvent3() {
		return vDEvent3;
	}

	public void setvDEvent3(Double vDEvent3) {
		this.vDEvent3 = vDEvent3;
	}

	public Double getvDEvent4() {
		return vDEvent4;
	}

	public void setvDEvent4(Double vDEvent4) {
		this.vDEvent4 = vDEvent4;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getTodayOdometer() {
		return todayOdometer;
	}

	public void setTodayOdometer(Long todayOdometer) {
		this.todayOdometer = todayOdometer;
	}

	public String getPacketJson() {
		return packetJson;
	}

	public void setPacketJson(String packetJson) {
		this.packetJson = packetJson;
	}

	public String getPacketHex() {
		return packetHex;
	}

	public void setPacketHex(String packetHex) {
		this.packetHex = packetHex;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Integer getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(Integer speedLimit) {
		this.speedLimit = speedLimit;
	}

	public String getFatigueDuration() {
		return fatigueDuration;
	}

	public void setFatigueDuration(String fatigueDuration) {
		this.fatigueDuration = fatigueDuration;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getIgnitionLastUpdate() {
		return ignitionLastUpdate;
	}

	public void setIgnitionLastUpdate(Date ignitionLastUpdate) {
		this.ignitionLastUpdate = ignitionLastUpdate;
	}

	public String getOverspeedRange() {
		return overspeedRange;
	}

	public void setOverspeedRange(String overspeedRange) {
		this.overspeedRange = overspeedRange;
	}

	public String getAssetInformation() {
		return assetInformation;
	}

	public void setAssetInformation(String assetInformation) {
		this.assetInformation = assetInformation;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getTemp1LastTransmission() {
		return temp1LastTransmission;
	}

	public void setTemp1LastTransmission(Date temp1LastTransmission) {
		this.temp1LastTransmission = temp1LastTransmission;
	}

	public String getVehicleReferenceKey() {
		return vehicleReferenceKey;
	}

	public void setVehicleReferenceKey(String vehicleReferenceKey) {
		this.vehicleReferenceKey = vehicleReferenceKey;
	}

	public Date getLastEventUpd() {
		return lastEventUpd;
	}

	public void setLastEventUpd(Date lastEventUpd) {
		this.lastEventUpd = lastEventUpd;
	}

	public String getVehicleRemark() {
		return vehicleRemark;
	}

	public void setVehicleRemark(String vehicleRemark) {
		this.vehicleRemark = vehicleRemark;
	}

	public String getIoConfig() {
		return ioConfig;
	}

	public void setIoConfig(String ioConfig) {
		this.ioConfig = ioConfig;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

}
