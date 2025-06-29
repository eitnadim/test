package com.eit.gateway.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "loggingevent", schema = "mvt")
public class LoggingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logid")
    private Long logId;

    @Column(name = "imei", nullable = false)
    private String imei;

    @Column(name = "eventtimestamp", nullable = false, unique = true)
    private Date eventTimestamp;

    @Column(name = "servertimestamp", nullable = false)
    private Date serverTimestamp;

    @Column(name = "rawdata", length = 3000, nullable = false)
    private String rawData;

    // Getters and Setters

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Date getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(Date date) {
        this.eventTimestamp = date;
    }

    public Date getServerTimestamp() {
        return serverTimestamp;
    }

    public void setServerTimestamp(Date serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }
}
