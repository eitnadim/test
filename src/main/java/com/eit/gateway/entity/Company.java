package com.eit.gateway.entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "com_company", schema = "mvt")
//@Getter
//@Setter
public class Company {
	
	

    @Id
    @Column(name = "companyid", length = 20, nullable = false)
    private String companyId;

    @Column(name = "companyname", length = 256)
    private String companyName;

    @Column(name = "companyregno", length = 45)
    private String companyRegNo;

    @Column(name = "companyregdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date companyRegDate;

    @Column(name = "datecreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(name = "createdby", length = 45)
    private String createdBy;

    @Column(name = "dateupdated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    @Column(name = "updatedby", length = 45)
    private String updatedBy;

    @Column(name = "suffix", length = 10)
    private String suffix;

    @Column(name = "companysetting", length = 300)
    private String companySetting;

    @Column(name = "isdemo")
    private Boolean isDemo;

    @Column(name = "followup")
    private Boolean followUp;

    @Column(name = "salesperson", length = 45)
    private String salesperson;

    @Column(name = "overdue")
    private Integer overdue;

    @Column(name = "remarks", length = 500)
    private String remarks;

    @Column(name = "notransmissionrptskip")
    private Boolean noTransmissionRptSkip;

    @Column(name = "isminiapps")
    private Boolean isMiniApps;

    @Column(name = "islostdeal")
    private Boolean isLostDeal;

    @Column(name = "isdeleted")
    private Boolean isDeleted;

    @Column(name = "datedeleted")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDeleted;

    @Column(name = "issspwft")
    private Boolean isSspwft;

    @Column(name = "mobileappshorterlink", length = 45)
    private String mobileAppShorterLink;

    @Column(name = "contactno", length = 20)
    private String contactNo;

    @Column(name = "emailid", length = 50)
    private String emailId;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "region", length = 40, nullable = false)
    private String region;
    
    
    public Company() {
		// TODO Auto-generated constructor stub
	}


	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getCompanyRegNo() {
		return companyRegNo;
	}


	public void setCompanyRegNo(String companyRegNo) {
		this.companyRegNo = companyRegNo;
	}


	public Date getCompanyRegDate() {
		return companyRegDate;
	}


	public void setCompanyRegDate(Date companyRegDate) {
		this.companyRegDate = companyRegDate;
	}


	public Date getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public Date getDateUpdated() {
		return dateUpdated;
	}


	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public String getSuffix() {
		return suffix;
	}


	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}


	public String getCompanySetting() {
		return companySetting;
	}


	public void setCompanySetting(String companySetting) {
		this.companySetting = companySetting;
	}


	public Boolean getIsDemo() {
		return isDemo;
	}


	public void setIsDemo(Boolean isDemo) {
		this.isDemo = isDemo;
	}


	public Boolean getFollowUp() {
		return followUp;
	}


	public void setFollowUp(Boolean followUp) {
		this.followUp = followUp;
	}


	public String getSalesperson() {
		return salesperson;
	}


	public void setSalesperson(String salesperson) {
		this.salesperson = salesperson;
	}


	public Integer getOverdue() {
		return overdue;
	}


	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public Boolean getNoTransmissionRptSkip() {
		return noTransmissionRptSkip;
	}


	public void setNoTransmissionRptSkip(Boolean noTransmissionRptSkip) {
		this.noTransmissionRptSkip = noTransmissionRptSkip;
	}


	public Boolean getIsMiniApps() {
		return isMiniApps;
	}


	public void setIsMiniApps(Boolean isMiniApps) {
		this.isMiniApps = isMiniApps;
	}


	public Boolean getIsLostDeal() {
		return isLostDeal;
	}


	public void setIsLostDeal(Boolean isLostDeal) {
		this.isLostDeal = isLostDeal;
	}


	public Boolean getIsDeleted() {
		return isDeleted;
	}


	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}


	public Date getDateDeleted() {
		return dateDeleted;
	}


	public void setDateDeleted(Date dateDeleted) {
		this.dateDeleted = dateDeleted;
	}


	public Boolean getIsSspwft() {
		return isSspwft;
	}


	public void setIsSspwft(Boolean isSspwft) {
		this.isSspwft = isSspwft;
	}


	public String getMobileAppShorterLink() {
		return mobileAppShorterLink;
	}


	public void setMobileAppShorterLink(String mobileAppShorterLink) {
		this.mobileAppShorterLink = mobileAppShorterLink;
	}


	public String getContactNo() {
		return contactNo;
	}


	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}


	public String getEmailId() {
		return emailId;
	}


	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public Company(String companyId, String companyName, String companyRegNo, Date companyRegDate, Date dateCreated,
			String createdBy, Date dateUpdated, String updatedBy, String suffix, String companySetting, Boolean isDemo,
			Boolean followUp, String salesperson, Integer overdue, String remarks, Boolean noTransmissionRptSkip,
			Boolean isMiniApps, Boolean isLostDeal, Boolean isDeleted, Date dateDeleted, Boolean isSspwft,
			String mobileAppShorterLink, String contactNo, String emailId, String address, String region) {
		super();
		this.companyId = companyId;
		this.companyName = companyName;
		this.companyRegNo = companyRegNo;
		this.companyRegDate = companyRegDate;
		this.dateCreated = dateCreated;
		this.createdBy = createdBy;
		this.dateUpdated = dateUpdated;
		this.updatedBy = updatedBy;
		this.suffix = suffix;
		this.companySetting = companySetting;
		this.isDemo = isDemo;
		this.followUp = followUp;
		this.salesperson = salesperson;
		this.overdue = overdue;
		this.remarks = remarks;
		this.noTransmissionRptSkip = noTransmissionRptSkip;
		this.isMiniApps = isMiniApps;
		this.isLostDeal = isLostDeal;
		this.isDeleted = isDeleted;
		this.dateDeleted = dateDeleted;
		this.isSspwft = isSspwft;
		this.mobileAppShorterLink = mobileAppShorterLink;
		this.contactNo = contactNo;
		this.emailId = emailId;
		this.address = address;
		this.region = region;
	}
	
	
    
    
}


