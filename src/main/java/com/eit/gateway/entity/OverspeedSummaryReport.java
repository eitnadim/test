package com.eit.gateway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "overspeedsummaryreport", schema = "mvt")
//@Getter
//@Setter 
public class OverspeedSummaryReport {
	
	public OverspeedSummaryReport() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @Column(name = "vin", nullable = false, length = 135)
    private String vin;

    @Id
    @Column(name = "fromdate", nullable = false, length = 135)
    private String fromDate;

    @Id
    @Column(name = "begin", nullable = false, length = 135)
    private String begin;

    @Id
    @Column(name = "beginlocation", nullable = false, length = 135)
    private String beginLocation;

    @Id
    @Column(name = "end_time", nullable = false, length = 135)
    private String endTime;

    @Id
    @Column(name = "endlocation", nullable = false, length = 135)
    private String endLocation;

    @Id
    @Column(name = "overspeedduration", nullable = false, length = 135)
    private String overspeedDuration;

    @Id
    @Column(name = "max", nullable = false, length = 10)
    private String max;

    @Id
    @Column(name = "min", nullable = false, length = 10)
    private String min;

    @Id
    @Column(name = "avg", nullable = false, length = 10)
    private String avg;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
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

	public String getOverspeedDuration() {
		return overspeedDuration;
	}

	public void setOverspeedDuration(String overspeedDuration) {
		this.overspeedDuration = overspeedDuration;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getAvg() {
		return avg;
	}

	public void setAvg(String avg) {
		this.avg = avg;
	}

	public OverspeedSummaryReport(String vin, String fromDate, String begin, String beginLocation, String endTime,
			String endLocation, String overspeedDuration, String max, String min, String avg) {
		super();
		this.vin = vin;
		this.fromDate = fromDate;
		this.begin = begin;
		this.beginLocation = beginLocation;
		this.endTime = endTime;
		this.endLocation = endLocation;
		this.overspeedDuration = overspeedDuration;
		this.max = max;
		this.min = min;
		this.avg = avg;
	}
    
    
    
}

