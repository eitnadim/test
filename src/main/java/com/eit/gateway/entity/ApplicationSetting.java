package com.eit.gateway.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "applicationsetting", schema = "mvt")
//@Getter
//@Setter
public class ApplicationSetting {

    @Id
    @Column(name = "applicationkey", length = 200, nullable = false)
    private String applicationKey; // 🔥 Primary key

    @Column(name = "appvalue")
    private String appValue;

	public String getApplicationKey() {
		return applicationKey;
	}

	public void setApplicationKey(String applicationKey) {
		this.applicationKey = applicationKey;
	}

	public String getAppValue() {
		return appValue;
	}

	public void setAppValue(String appValue) {
		this.appValue = appValue;
	}
	
	public ApplicationSetting() {
		// TODO Auto-generated constructor stub
	}

	public ApplicationSetting(String applicationKey, String appValue) {
		super();
		this.applicationKey = applicationKey;
		this.appValue = appValue;
	}
	
	
    
    
}
