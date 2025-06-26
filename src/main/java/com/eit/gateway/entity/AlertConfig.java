package com.eit.gateway.entity;

 import java.util.Date;

// ✨ change this to your package
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
@Table(
    name = "alertconfig",
    schema = "mvt",
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_vin_alerttype", columnNames = {"vin", "alerttype"})
    }
)
//@Getter
//@Setter
public class AlertConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String userid;

    @Column(length = 100, nullable = false)
    private String alerttype;

    @Column(length = 45, nullable = false)
    private String vin;

    @Column
    private String alertconfig;

    @Column(length = 45)
    private String refinterval;

    @Column
    private String intervaltype;

    @Column
    private String notification;

    @Column
    private String subscription;

    @Column
    private String alertday;

    @Column(length = 45)
    private String starttime;

    @Column(length = 45)
    private String endtime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date validityexp;

    @Column(length = 45)
    private String effectivetill;

    @Column(length = 255)
    private String inignore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAlerttype() {
		return alerttype;
	}

	public void setAlerttype(String alerttype) {
		this.alerttype = alerttype;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getAlertconfig() {
		return alertconfig;
	}

	public void setAlertconfig(String alertconfig) {
		this.alertconfig = alertconfig;
	}

	public String getRefinterval() {
		return refinterval;
	}

	public void setRefinterval(String refinterval) {
		this.refinterval = refinterval;
	}

	public String getIntervaltype() {
		return intervaltype;
	}

	public void setIntervaltype(String intervaltype) {
		this.intervaltype = intervaltype;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public String getSubscription() {
		return subscription;
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

	public String getAlertday() {
		return alertday;
	}

	public void setAlertday(String alertday) {
		this.alertday = alertday;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public Date getValidityexp() {
		return validityexp;
	}

	public void setValidityexp(Date validityexp) {
		this.validityexp = validityexp;
	}

	public String getEffectivetill() {
		return effectivetill;
	}

	public void setEffectivetill(String effectivetill) {
		this.effectivetill = effectivetill;
	}

	public String getInignore() {
		return inignore;
	}

	public void setInignore(String inignore) {
		this.inignore = inignore;
	}
    
	
	public AlertConfig() {
		// TODO Auto-generated constructor stub
	}

	public AlertConfig(Long id, String userid, String alerttype, String vin, String alertconfig, String refinterval,
			String intervaltype, String notification, String subscription, String alertday, String starttime,
			String endtime, Date validityexp, String effectivetill, String inignore) {
		super();
		this.id = id;
		this.userid = userid;
		this.alerttype = alerttype;
		this.vin = vin;
		this.alertconfig = alertconfig;
		this.refinterval = refinterval;
		this.intervaltype = intervaltype;
		this.notification = notification;
		this.subscription = subscription;
		this.alertday = alertday;
		this.starttime = starttime;
		this.endtime = endtime;
		this.validityexp = validityexp;
		this.effectivetill = effectivetill;
		this.inignore = inignore;
	}
	
	
    
    
    
}

