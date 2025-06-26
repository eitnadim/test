package com.eit.gateway.entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "companytrackdevicemodels", schema = "mvt")
//@Getter
//@Setter
public class CompanyTrackDeviceModels {

    @Id
    @Column(name = "companyid", nullable = false)
    private String companyId;

    @Id
    @Column(name = "manufacturername", nullable = false)
    private String manufacturerName;

    @Id
    @Column(name = "modelname", nullable = false)
    private String modelName;

    @Column(name = "geozone", length = 900)
    private String geoZone;

    @Column(name = "defprofileid")
    private Integer defProfileId;

    @Column(name = "trxtype", length = 45)
    private String trxType;

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

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getGeoZone() {
		return geoZone;
	}

	public void setGeoZone(String geoZone) {
		this.geoZone = geoZone;
	}

	public Integer getDefProfileId() {
		return defProfileId;
	}

	public void setDefProfileId(Integer defProfileId) {
		this.defProfileId = defProfileId;
	}

	public String getTrxType() {
		return trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
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

	public CompanyTrackDeviceModels(String companyId, String manufacturerName, String modelName, String geoZone,
			Integer defProfileId, String trxType, String lastUpdBy, Date lastUpdDt) {
		super();
		this.companyId = companyId;
		this.manufacturerName = manufacturerName;
		this.modelName = modelName;
		this.geoZone = geoZone;
		this.defProfileId = defProfileId;
		this.trxType = trxType;
		this.lastUpdBy = lastUpdBy;
		this.lastUpdDt = lastUpdDt;
	}
    
	
    
    
}

