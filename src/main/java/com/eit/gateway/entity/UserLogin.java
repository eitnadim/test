package com.eit.gateway.entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "com_userlogin", schema = "mvt")
//@Getter
//@Setter
public class UserLogin {
	public UserLogin() {
		// TODO Auto-generated constructor stub
	}
	

    public UserLogin(Long id, String companyId, String branchId, String userId, String role, Date loginTime,
			String macIp, Date logoutTime, String lastUpdatedBy, Date lastUpdatedDate) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.branchId = branchId;
		this.userId = userId;
		this.role = role;
		this.loginTime = loginTime;
		this.macIp = macIp;
		this.logoutTime = logoutTime;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDate = lastUpdatedDate;
	}


	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment for bigserial
    private Long id;

    @Column(name = "companyid", length = 45)
    private String companyId;

    @Column(name = "branchid", length = 45)
    private String branchId;

    @Column(name = "userid", length = 45)
    private String userId;

    @Column(name = "role", length = 45)
    private String role;

    @Column(name = "logintime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginTime;

    @Column(name = "macip", length = 45)
    private String macIp;

    @Column(name = "logouttime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logoutTime;

    @Column(name = "lastupdatedby", length = 45)
    private String lastUpdatedBy;

    @Column(name = "lastupdateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getBranchId() {
		return branchId;
	}


	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public Date getLoginTime() {
		return loginTime;
	}


	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}


	public String getMacIp() {
		return macIp;
	}


	public void setMacIp(String macIp) {
		this.macIp = macIp;
	}


	public Date getLogoutTime() {
		return logoutTime;
	}


	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}


	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}


	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}


	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}


	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
    
    

}

