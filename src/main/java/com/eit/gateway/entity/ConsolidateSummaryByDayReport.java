package com.eit.gateway.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "consolidatesummarybydayreport", schema = "mvt")
//@Getter
//@Setter
public class ConsolidateSummaryByDayReport {
	
	public ConsolidateSummaryByDayReport() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @Column(name = "fromdate")
    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @Id
    @Column(name = "vin", length = 135, nullable = false)
    private String vin;

    @Column(name = "begin", length = 135)
    private String begin;

    @Column(name = "beginlocation", length = 135)
    private String beginLocation;

    @Column(name = "end_time", length = 135)
    private String endTime;

    @Column(name = "endlocation", length = 135)
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

    @Column(name = "maxspeed")
    private Integer maxSpeed;

    @Column(name = "avgspeed")
    private Integer avgSpeed;

    @Column(name = "minspeed")
    private Integer minSpeed;

    @Column(name = "totalodometer", length = 135)
    private String totalOdometer;

    @Column(name = "additionalinformation", length = 15000)
    private String additionalInformation;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
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

	public Integer getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Integer maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Integer getAvgSpeed() {
		return avgSpeed;
	}

	public void setAvgSpeed(Integer avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	public Integer getMinSpeed() {
		return minSpeed;
	}

	public void setMinSpeed(Integer minSpeed) {
		this.minSpeed = minSpeed;
	}

	public String getTotalOdometer() {
		return totalOdometer;
	}

	public void setTotalOdometer(String totalOdometer) {
		this.totalOdometer = totalOdometer;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public ConsolidateSummaryByDayReport(Date fromDate, String vin, String begin, String beginLocation, String endTime,
			String endLocation, String runningDur, String stopDur, String idleDur, String towDur, String odometer,
			Integer maxSpeed, Integer avgSpeed, Integer minSpeed, String totalOdometer, String additionalInformation) {
		super();
		this.fromDate = fromDate;
		this.vin = vin;
		this.begin = begin;
		this.beginLocation = beginLocation;
		this.endTime = endTime;
		this.endLocation = endLocation;
		this.runningDur = runningDur;
		this.stopDur = stopDur;
		this.idleDur = idleDur;
		this.towDur = towDur;
		this.odometer = odometer;
		this.maxSpeed = maxSpeed;
		this.avgSpeed = avgSpeed;
		this.minSpeed = minSpeed;
		this.totalOdometer = totalOdometer;
		this.additionalInformation = additionalInformation;
	}
    
    
    
}

