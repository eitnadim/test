package com.eit.gateway.entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "companytrackdevice_has_companysimcard", schema = "mvt")
//@Getter
//@Setter
public class CompanyTrackDeviceHasCompanySimCard {
	
	public CompanyTrackDeviceHasCompanySimCard() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @Column(name = "imeino", nullable = false)
    private String imeiNo;

    @Column(name = "simcardno", nullable = false)
    private String simcardNo;

    @Column(name = "additional_simcard1", length = 45)
    private String additionalSimcard1;

    @Column(name = "efffrom")
    @Temporal(TemporalType.DATE)
    private Date effFrom;

    @Column(name = "effto")
    @Temporal(TemporalType.DATE)
    private Date effTo;

    @Column(name = "lastupdby", length = 45)
    private String lastUpdBy;

    @Column(name = "lastupddt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdDt;

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public String getSimcardNo() {
		return simcardNo;
	}

	public void setSimcardNo(String simcardNo) {
		this.simcardNo = simcardNo;
	}

	public String getAdditionalSimcard1() {
		return additionalSimcard1;
	}

	public void setAdditionalSimcard1(String additionalSimcard1) {
		this.additionalSimcard1 = additionalSimcard1;
	}

	public Date getEffFrom() {
		return effFrom;
	}

	public void setEffFrom(Date effFrom) {
		this.effFrom = effFrom;
	}

	public Date getEffTo() {
		return effTo;
	}

	public void setEffTo(Date effTo) {
		this.effTo = effTo;
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

	public CompanyTrackDeviceHasCompanySimCard(String imeiNo, String simcardNo, String additionalSimcard1, Date effFrom,
			Date effTo, String lastUpdBy, Date lastUpdDt) {
		super();
		this.imeiNo = imeiNo;
		this.simcardNo = simcardNo;
		this.additionalSimcard1 = additionalSimcard1;
		this.effFrom = effFrom;
		this.effTo = effTo;
		this.lastUpdBy = lastUpdBy;
		this.lastUpdDt = lastUpdDt;
	}
    
    
}

