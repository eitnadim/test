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
@Table(name = "consolidatesummaryreport", schema = "mvt")
//@Getter 
//@Setter
public class ConsolidateSummaryReport {
	
	public ConsolidateSummaryReport() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Bigserial equivalent in JPA
    @Column(name = "id", nullable = false)
    private Long id;

    @Id
    @Column(name = "vin", length = 135, nullable = false)
    private String vin;

    @Id
    @Column(name = "fromdate")
    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @Column(name = "begin", length = 135, nullable = false)
    private String begin;

    @Column(name = "beginlocation", length = 135, nullable = false)
    private String beginLocation;

    @Column(name = "end_time", length = 135, nullable = false)
    private String endTime;

    @Column(name = "endlocation", length = 135, nullable = false)
    private String endLocation;

    @Column(name = "runningdur", length = 135)
    private String runningDur;

    @Column(name = "stopdur", length = 135)
    private String stopDur;

    @Column(name = "idledur", length = 135)
    private String idleDur;

    @Column(name = "towedur", length = 135)
    private String towDur;

    @Column(name = "odometer", length = 135)
    private String odometer;

    @Column(name = "totalodometer", length = 135)
    private String totalOdometer;

    @Column(name = "maxspeed")
    private Integer maxSpeed;

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

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getBeginLocation() {
		return beginLocation;
	}

	public void setBeginLocation(String beginLocation) {
		this.beginLocation = beginLocation;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	public String getRunningDur() {
		return runningDur;
	}

	public void setRunningDur(String runningDur) {
		this.runningDur = runningDur;
	}

	public String getStopDur() {
		return stopDur;
	}

	public void setStopDur(String stopDur) {
		this.stopDur = stopDur;
	}

	public String getIdleDur() {
		return idleDur;
	}

	public void setIdleDur(String idleDur) {
		this.idleDur = idleDur;
	}

	public String getTowDur() {
		return towDur;
	}

	public void setTowDur(String towDur) {
		this.towDur = towDur;
	}

	public String getOdometer() {
		return odometer;
	}

	public void setOdometer(String odometer) {
		this.odometer = odometer;
	}

	public String getTotalOdometer() {
		return totalOdometer;
	}

	public void setTotalOdometer(String totalOdometer) {
		this.totalOdometer = totalOdometer;
	}

	public Integer getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Integer maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public ConsolidateSummaryReport(Long id, String vin, Date fromDate, String begin, String beginLocation,
			String endTime, String endLocation, String runningDur, String stopDur, String idleDur, String towDur,
			String odometer, String totalOdometer, Integer maxSpeed) {
		super();
		this.id = id;
		this.vin = vin;
		this.fromDate = fromDate;
		this.begin = begin;
		this.beginLocation = beginLocation;
		this.endTime = endTime;
		this.endLocation = endLocation;
		this.runningDur = runningDur;
		this.stopDur = stopDur;
		this.idleDur = idleDur;
		this.towDur = towDur;
		this.odometer = odometer;
		this.totalOdometer = totalOdometer;
		this.maxSpeed = maxSpeed;
	}
    
    
    
}

