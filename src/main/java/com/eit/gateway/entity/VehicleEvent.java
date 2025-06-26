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
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "vehicleevent", 
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_vehicleevent_vin_timestamp", 
                           columnNames = {"vin", "eventtimestamp"})
       },
       indexes = {
           @Index(name = "idx_vehicleevent_vin", columnList = "vin"),
           @Index(name = "idx_vehicleevent_timestamp", columnList = "eventtimestamp")
       })
public class VehicleEvent {
	
	public VehicleEvent() {
		// Default constructor
	}
	
    // Constructor without ID (since it's auto-generated)
    public VehicleEvent(Date eventTimestamp, Long skyId, String vin, Date serverTimestamp, Double latitude,
			Double longitude, Integer speed, Integer priority, String ioEvent, Long bytesTrx, Boolean engine,
			Long tempSensor1, Long tempSensor2, Long tempSensor3, Long tempSensor4, Long odometer, Long battery,
			Integer direction, Integer eventSource, Integer ai1, Integer ai2, Integer ai3, Integer ai4, Integer di1,
			Integer di2, Integer di3, Integer di4, String tags, String address, Long lEvent1, Long lEvent2,
			Long lEvent3, Long lEvent4, Long lEvent5, Double dEvent1, Double dEvent2, Double dEvent3, Double dEvent4,
			Double dEvent5, String vEvent1, String vEvent2, String vEvent3, String vEvent4, String vEvent5,
			String status, String processedIoEvent) {
		super();
		this.eventTimestamp = eventTimestamp;
		this.skyId = skyId;
		this.vin = vin;
		this.serverTimestamp = serverTimestamp;
		this.latitude = latitude;
		this.longitude = longitude;
		this.speed = speed;
		this.priority = priority;
		this.ioEvent = ioEvent;
		this.bytesTrx = bytesTrx;
		this.engine = engine;
		this.tempSensor1 = tempSensor1;
		this.tempSensor2 = tempSensor2;
		this.tempSensor3 = tempSensor3;
		this.tempSensor4 = tempSensor4;
		this.odometer = odometer;
		this.battery = battery;
		this.direction = direction;
		this.eventSource = eventSource;
		this.ai1 = ai1;
		this.ai2 = ai2;
		this.ai3 = ai3;
		this.ai4 = ai4;
		this.di1 = di1;
		this.di2 = di2;
		this.di3 = di3;
		this.di4 = di4;
		this.tags = tags;
		this.address = address;
		this.lEvent1 = lEvent1;
		this.lEvent2 = lEvent2;
		this.lEvent3 = lEvent3;
		this.lEvent4 = lEvent4;
		this.lEvent5 = lEvent5;
		this.dEvent1 = dEvent1;
		this.dEvent2 = dEvent2;
		this.dEvent3 = dEvent3;
		this.dEvent4 = dEvent4;
		this.dEvent5 = dEvent5;
		this.vEvent1 = vEvent1;
		this.vEvent2 = vEvent2;
		this.vEvent3 = vEvent3;
		this.vEvent4 = vEvent4;
		this.vEvent5 = vEvent5;
		this.status = status;
		this.processedIoEvent = processedIoEvent;
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "eventtimestamp", nullable = false)
    private Date eventTimestamp;
    
    @Column(name = "vin", nullable = false, length = 20)
    private String vin;
    
    @Column(name = "skyid")
    private Long skyId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "servertimestamp")
    private Date serverTimestamp;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "speed")
    private Integer speed;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "ioevent", length = 6000)
    private String ioEvent;

    @Column(name = "bytestrx")
    private Long bytesTrx;

    @Column(name = "engine")
    private Boolean engine;

    @Column(name = "tempsensor1")
    private Long tempSensor1;

    @Column(name = "tempsensor2")
    private Long tempSensor2;

    @Column(name = "tempsensor3")
    private Long tempSensor3;

    @Column(name = "tempsensor4")
    private Long tempSensor4;

    @Column(name = "odometer")
    private Long odometer;

    @Column(name = "battery")
    private Long battery;

    @Column(name = "direction")
    private Integer direction;

    @Column(name = "eventsource")
    private Integer eventSource;

    @Column(name = "ai1")
    private Integer ai1;

    @Column(name = "ai2")
    private Integer ai2;

    @Column(name = "ai3")
    private Integer ai3;

    @Column(name = "ai4")
    private Integer ai4;

    @Column(name = "di1")
    private Integer di1;

    @Column(name = "di2")
    private Integer di2;

    @Column(name = "di3")
    private Integer di3;

    @Column(name = "di4")
    private Integer di4;

    @Column(name = "tags", length = 1000)
    private String tags;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "l_event1")
    private Long lEvent1;

    @Column(name = "l_event2")
    private Long lEvent2;

    @Column(name = "l_event3")
    private Long lEvent3;

    @Column(name = "l_event4")
    private Long lEvent4;

    @Column(name = "l_event5")
    private Long lEvent5;

    @Column(name = "d_event1")
    private Double dEvent1;

    @Column(name = "d_event2")
    private Double dEvent2;

    @Column(name = "d_event3")
    private Double dEvent3;

    @Column(name = "d_event4")
    private Double dEvent4;

    @Column(name = "d_event5")
    private Double dEvent5;

    @Column(name = "v_event1", length = 200)
    private String vEvent1;

    @Column(name = "v_event2", length = 100)
    private String vEvent2;

    @Column(name = "v_event3", length = 100)
    private String vEvent3;

    @Column(name = "v_event4", length = 100)
    private String vEvent4;

    @Column(name = "v_event5", length = 100)
    private String vEvent5;

    @Column(name = "status", length = 255)
    private String status;
    
    @Column(name = "processed_ioevent")
    private String processedIoEvent; // Fixed naming convention
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessedIoEvent() {
        return processedIoEvent;
    }

    public void setProcessedIoEvent(String processedIoEvent) {
        this.processedIoEvent = processedIoEvent;
    }

    public Date getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(Date eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public Long getSkyId() {
        return skyId;
    }

    public void setSkyId(Long skyId) {
        this.skyId = skyId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Date getServerTimestamp() {
        return serverTimestamp;
    }

    public void setServerTimestamp(Date serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getIoEvent() {
        return ioEvent;
    }

    public void setIoEvent(String ioEvent) {
        this.ioEvent = ioEvent;
    }

    public Long getBytesTrx() {
        return bytesTrx;
    }

    public void setBytesTrx(Long bytesTrx) {
        this.bytesTrx = bytesTrx;
    }

    public Boolean getEngine() {
        return engine;
    }

    public void setEngine(Boolean engine) {
        this.engine = engine;
    }

    public Long getTempSensor1() {
        return tempSensor1;
    }

    public void setTempSensor1(Long tempSensor1) {
        this.tempSensor1 = tempSensor1;
    }

    public Long getTempSensor2() {
        return tempSensor2;
    }

    public void setTempSensor2(Long tempSensor2) {
        this.tempSensor2 = tempSensor2;
    }

    public Long getTempSensor3() {
        return tempSensor3;
    }

    public void setTempSensor3(Long tempSensor3) {
        this.tempSensor3 = tempSensor3;
    }

    public Long getTempSensor4() {
        return tempSensor4;
    }

    public void setTempSensor4(Long tempSensor4) {
        this.tempSensor4 = tempSensor4;
    }

    public Long getOdometer() {
        return odometer;
    }

    public void setOdometer(Long odometer) {
        this.odometer = odometer;
    }

    public Long getBattery() {
        return battery;
    }

    public void setBattery(Long battery) {
        this.battery = battery;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getEventSource() {
        return eventSource;
    }

    public void setEventSource(Integer eventSource) {
        this.eventSource = eventSource;
    }

    public Integer getAi1() {
        return ai1;
    }

    public void setAi1(Integer ai1) {
        this.ai1 = ai1;
    }

    public Integer getAi2() {
        return ai2;
    }

    public void setAi2(Integer ai2) {
        this.ai2 = ai2;
    }

    public Integer getAi3() {
        return ai3;
    }

    public void setAi3(Integer ai3) {
        this.ai3 = ai3;
    }

    public Integer getAi4() {
        return ai4;
    }

    public void setAi4(Integer ai4) {
        this.ai4 = ai4;
    }

    public Integer getDi1() {
        return di1;
    }

    public void setDi1(Integer di1) {
        this.di1 = di1;
    }

    public Integer getDi2() {
        return di2;
    }

    public void setDi2(Integer di2) {
        this.di2 = di2;
    }

    public Integer getDi3() {
        return di3;
    }

    public void setDi3(Integer di3) {
        this.di3 = di3;
    }

    public Integer getDi4() {
        return di4;
    }

    public void setDi4(Integer di4) {
        this.di4 = di4;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getlEvent1() {
        return lEvent1;
    }

    public void setlEvent1(Long lEvent1) {
        this.lEvent1 = lEvent1;
    }

    public Long getlEvent2() {
        return lEvent2;
    }

    public void setlEvent2(Long lEvent2) {
        this.lEvent2 = lEvent2;
    }

    public Long getlEvent3() {
        return lEvent3;
    }

    public void setlEvent3(Long lEvent3) {
        this.lEvent3 = lEvent3;
    }

    public Long getlEvent4() {
        return lEvent4;
    }

    public void setlEvent4(Long lEvent4) {
        this.lEvent4 = lEvent4;
    }

    public Long getlEvent5() {
        return lEvent5;
    }

    public void setlEvent5(Long lEvent5) {
        this.lEvent5 = lEvent5;
    }

    public Double getdEvent1() {
        return dEvent1;
    }

    public void setdEvent1(Double dEvent1) {
        this.dEvent1 = dEvent1;
    }

    public Double getdEvent2() {
        return dEvent2;
    }

    public void setdEvent2(Double dEvent2) {
        this.dEvent2 = dEvent2;
    }

    public Double getdEvent3() {
        return dEvent3;
    }

    public void setdEvent3(Double dEvent3) {
        this.dEvent3 = dEvent3;
    }

    public Double getdEvent4() {
        return dEvent4;
    }

    public void setdEvent4(Double dEvent4) {
        this.dEvent4 = dEvent4;
    }

    public Double getdEvent5() {
        return dEvent5;
    }

    public void setdEvent5(Double dEvent5) {
        this.dEvent5 = dEvent5;
    }

    public String getvEvent1() {
        return vEvent1;
    }

    public void setvEvent1(String vEvent1) {
        this.vEvent1 = vEvent1;
    }

    public String getvEvent2() {
        return vEvent2;
    }

    public void setvEvent2(String vEvent2) {
        this.vEvent2 = vEvent2;
    }

    public String getvEvent3() {
        return vEvent3;
    }

    public void setvEvent3(String vEvent3) {
        this.vEvent3 = vEvent3;
    }

    public String getvEvent4() {
        return vEvent4;
    }

    public void setvEvent4(String vEvent4) {
        this.vEvent4 = vEvent4;
    }

    public String getvEvent5() {
        return vEvent5;
    }

    public void setvEvent5(String vEvent5) {
        this.vEvent5 = vEvent5;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VehicleEvent{" +
                "id=" + id +
                ", eventTimestamp=" + eventTimestamp +
                ", vin='" + vin + '\'' +
                ", skyId=" + skyId +
                ", serverTimestamp=" + serverTimestamp +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", speed=" + speed +
                ", priority=" + priority +
                ", status='" + status + '\'' +
                '}';
    }
}