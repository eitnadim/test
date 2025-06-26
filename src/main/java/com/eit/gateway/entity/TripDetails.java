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
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "trip_deatils", schema = "mvt", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"routeid", "vin", "starttime", "endtime"})
})
//@Getter 
//@Setter 
public class TripDetails {
	
	public TripDetails() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Serial field, auto-generated
    @Column(name = "tripid", nullable = false)
    private Long tripId;

    @Column(name = "tripname", length = 80)
    private String tripName;

    @Column(name = "triptype", length = 50)
    private String tripType;

    @Column(name = "routeid", nullable = false)
    private Long routeId;

    @Column(name = "vin", nullable = false, length = 60)
    private String vin;

    @Column(name = "userid", nullable = false)
    private Long userId;

    @Column(name = "tripday", length = 80)
    private String tripDay;

    @Column(name = "tripstatus", length = 60)
    private String tripStatus;

    @Temporal(TemporalType.TIME)  // Mapping time without time zone
    @Column(name = "starttime", nullable = false)
    private Date startTime;  // Using java.util.Date for time mapping

    @Temporal(TemporalType.TIME)  // Mapping time without time zone
    @Column(name = "endtime", nullable = false)
    private Date endTime;  // Using java.util.Date for time mapping

    @Temporal(TemporalType.TIME)  // Mapping time without time zone
    @Column(name = "buffertime", nullable = false)
    private Date bufferTime;  // Using java.util.Date for time mapping

    @Column(name = "lastupdby", length = 45)
    private String lastUpdBy;

    @Temporal(TemporalType.TIMESTAMP)  // Mapping timestamp without time zone
    @Column(name = "lastupdt")
    private Date lastUpdT;  // Using java.util.Date for timestamp

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public String getTripName() {
		return tripName;
	}

	public void setTripName(String tripName) {
		this.tripName = tripName;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTripDay() {
		return tripDay;
	}

	public void setTripDay(String tripDay) {
		this.tripDay = tripDay;
	}

	public String getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(String tripStatus) {
		this.tripStatus = tripStatus;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getBufferTime() {
		return bufferTime;
	}

	public void setBufferTime(Date bufferTime) {
		this.bufferTime = bufferTime;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Date getLastUpdT() {
		return lastUpdT;
	}

	public void setLastUpdT(Date lastUpdT) {
		this.lastUpdT = lastUpdT;
	}

	public TripDetails(Long tripId, String tripName, String tripType, Long routeId, String vin, Long userId,
			String tripDay, String tripStatus, Date startTime, Date endTime, Date bufferTime, String lastUpdBy,
			Date lastUpdT) {
		super();
		this.tripId = tripId;
		this.tripName = tripName;
		this.tripType = tripType;
		this.routeId = routeId;
		this.vin = vin;
		this.userId = userId;
		this.tripDay = tripDay;
		this.tripStatus = tripStatus;
		this.startTime = startTime;
		this.endTime = endTime;
		this.bufferTime = bufferTime;
		this.lastUpdBy = lastUpdBy;
		this.lastUpdT = lastUpdT;
	}
    
    
    

}
