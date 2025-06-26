package com.eit.gateway.entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Table(name="activitylog" ,schema = "mvt")
//@Getter
//@Setter
public class ActivityLog {
	
	
	public ActivityLog() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ActivityLog(Long id, String logs, Long ids, String data, String displayType, String navigateDisplayTab,
			String status, String activityType, String createdBy, Date createdTime) {
		super();
		this.id = id;
		this.logs = logs;
		this.ids = ids;
		this.data = data;
		this.displayType = displayType;
		this.navigateDisplayTab = navigateDisplayTab;
		this.status = status;
		this.activityType = activityType;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "logs")
	private String logs;
	
	@Column(name = "ids")
	private Long ids;
	
	@Column(name = "data")
	private String data;
	
	@Column(name = "displaytype")
	private String displayType;
	
	@Column(name = "navigatedisplaytab")
	private String navigateDisplayTab;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "activitytype")
	private String activityType;
	
	@Column(name = "createdby")
	private String createdBy;
	
	@Column(name = "createdtime")
    @Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogs() {
		return logs;
	}

	public void setLogs(String logs) {
		this.logs = logs;
	}

	public Long getIds() {
		return ids;
	}

	public void setIds(Long ids) {
		this.ids = ids;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getNavigateDisplayTab() {
		return navigateDisplayTab;
	}

	public void setNavigateDisplayTab(String navigateDisplayTab) {
		this.navigateDisplayTab = navigateDisplayTab;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	
	
}
