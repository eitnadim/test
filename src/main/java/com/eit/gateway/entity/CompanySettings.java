package com.eit.gateway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "com_companysettings", schema = "mvt")
//@Getter
//@Setter
//@Data
public class CompanySettings {
	
	public CompanySettings() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @Column(name = "appsettings_key", length = 405, nullable = false)
    private String appSettingsKey;

//    @Id
    @Column(name = "comvalues", nullable = false)
    private String comValues;

//    @Id
    @Column(name = "companyid", length = 20, nullable = false)
    private String companyId;

	public String getAppSettingsKey() {
		return appSettingsKey;
	}

	public void setAppSettingsKey(String appSettingsKey) {
		this.appSettingsKey = appSettingsKey;
	}

	public String getComValues() {
		return comValues;
	}

	public void setComValues(String comValues) {
		this.comValues = comValues;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public CompanySettings(String appSettingsKey, String comValues, String companyId) {
		super();
		this.appSettingsKey = appSettingsKey;
		this.comValues = comValues;
		this.companyId = companyId;
	}
    
    
    

}

