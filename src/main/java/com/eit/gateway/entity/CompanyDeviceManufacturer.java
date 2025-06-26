package com.eit.gateway.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "companydevicemanufacturer", schema = "mvt")
//@Getter
//@Setter
public class CompanyDeviceManufacturer {
	
	public CompanyDeviceManufacturer() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @Column(name = "companyid", length = 45, nullable = false)
    private String companyId;

    @Id
    @Column(name = "manufacturername", length = 45, nullable = false)
    private String manufacturerName;

    @Column(name = "location", length = 45)
    private String location;

    @Column(name = "website", length = 45)
    private String website;

    @Column(name = "lastupdby", length = 45)
    private String lastUpdBy;

    @Column(name = "lastupddt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdDt;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Date getLastUpdDt() {
		return lastUpdDt;
	}

	public void setLastUpdDt(Date lastUpdDt) {
		this.lastUpdDt = lastUpdDt;
	}

	public CompanyDeviceManufacturer(String companyId, String manufacturerName, String location, String website,
			String lastUpdBy, Date lastUpdDt) {
		super();
		this.companyId = companyId;
		this.manufacturerName = manufacturerName;
		this.location = location;
		this.website = website;
		this.lastUpdBy = lastUpdBy;
		this.lastUpdDt = lastUpdDt;
	}
    
    

}

