package com.eit.gateway.entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "companysimcard", schema = "mvt")
//@Getter
//@Setter
public class CompanySimCard {
	public CompanySimCard() {
		// TODO Auto-generated constructor stub
	}
	
	

    public CompanySimCard(String simcardNo, String companyId, Date dop, Date expiryDate, String simcardModelsProvider,
			String simcardModelsPlan, Integer pincodes, String serialNo, String pukCode, String supportNo,
			String additionalSimCard1, String additionalProvider1, String lastUpdBy, Date lastUpdDt, String status,
			String remainder) {
		super();
		this.simcardNo = simcardNo;
		this.companyId = companyId;
		this.dop = dop;
		this.expiryDate = expiryDate;
		this.simcardModelsProvider = simcardModelsProvider;
		this.simcardModelsPlan = simcardModelsPlan;
		this.pincodes = pincodes;
		this.serialNo = serialNo;
		this.pukCode = pukCode;
		this.supportNo = supportNo;
		this.additionalSimCard1 = additionalSimCard1;
		this.additionalProvider1 = additionalProvider1;
		this.lastUpdBy = lastUpdBy;
		this.lastUpdDt = lastUpdDt;
		this.status = status;
		this.remainder = remainder;
	}



	@Id
    @Column(name = "simcardno", length = 45, nullable = false)
    private String simcardNo;

    @Column(name = "companyid", length = 45, nullable = false)
    private String companyId;

    @Column(name = "dop")
    @Temporal(TemporalType.DATE)
    private Date dop;

    @Column(name = "expirydate")
    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @Column(name = "simcardmodels_provider", length = 45, nullable = false)
    private String simcardModelsProvider;

    @Column(name = "simcardmodels_plan", length = 45, nullable = false)
    private String simcardModelsPlan;

    @Column(name = "pincodes")
    private Integer pincodes;

    @Column(name = "serialno", length = 45)
    private String serialNo;

    @Column(name = "pukcode", length = 45)
    private String pukCode;

    @Column(name = "supportno", length = 45)
    private String supportNo;

    @Column(name = "additional_simcard1", length = 20)
    private String additionalSimCard1;

    @Column(name = "additional_provider1", length = 20)
    private String additionalProvider1;

    @Column(name = "lastupdby", length = 45)
    private String lastUpdBy;

    @Column(name = "lastupddt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdDt;

    @Column(name = "status", length = 20)
    private String status = "active";  // Default value

    @Column(name = "remainder", length = 100, nullable = false)
    private String remainder = "";  // Default value

	public String getSimcardNo() {
		return simcardNo;
	}

	public void setSimcardNo(String simcardNo) {
		this.simcardNo = simcardNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Date getDop() {
		return dop;
	}

	public void setDop(Date dop) {
		this.dop = dop;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getSimcardModelsProvider() {
		return simcardModelsProvider;
	}

	public void setSimcardModelsProvider(String simcardModelsProvider) {
		this.simcardModelsProvider = simcardModelsProvider;
	}

	public String getSimcardModelsPlan() {
		return simcardModelsPlan;
	}

	public void setSimcardModelsPlan(String simcardModelsPlan) {
		this.simcardModelsPlan = simcardModelsPlan;
	}

	public Integer getPincodes() {
		return pincodes;
	}

	public void setPincodes(Integer pincodes) {
		this.pincodes = pincodes;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getPukCode() {
		return pukCode;
	}

	public void setPukCode(String pukCode) {
		this.pukCode = pukCode;
	}

	public String getSupportNo() {
		return supportNo;
	}

	public void setSupportNo(String supportNo) {
		this.supportNo = supportNo;
	}

	public String getAdditionalSimCard1() {
		return additionalSimCard1;
	}

	public void setAdditionalSimCard1(String additionalSimCard1) {
		this.additionalSimCard1 = additionalSimCard1;
	}

	public String getAdditionalProvider1() {
		return additionalProvider1;
	}

	public void setAdditionalProvider1(String additionalProvider1) {
		this.additionalProvider1 = additionalProvider1;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemainder() {
		return remainder;
	}

	public void setRemainder(String remainder) {
		this.remainder = remainder;
	}
    
    
    
}
