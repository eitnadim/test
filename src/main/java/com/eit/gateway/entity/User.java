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
@Table(name = "com_user", schema = "mvt")
//@Getter
//@Setter
public class User {
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	

    public User(Long userId, String emailAddress, String push, String companyCompanyId, String userPassword,
			String firstName, String lastName, String address, Integer vehiclesAlloted, Boolean primaryUser,
			String contactNo, String fax, String imgUrl, String mainMenu, Date expiredDate, Boolean isMiniApps,
			Boolean isContractor, String userMenuPrivileges, String hierarchyNodes, String initialPage,
			String additionalInfo, String mapView, Long role, String createdBy, Date dateCreated, String updatedBy,
			Date dateUpdated) {
		super();
		this.userId = userId;
		this.emailAddress = emailAddress;
		this.push = push;
		this.companyCompanyId = companyCompanyId;
		this.userPassword = userPassword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.vehiclesAlloted = vehiclesAlloted;
		this.primaryUser = primaryUser;
		this.contactNo = contactNo;
		this.fax = fax;
		this.imgUrl = imgUrl;
		this.mainMenu = mainMenu;
		this.expiredDate = expiredDate;
		this.isMiniApps = isMiniApps;
		this.isContractor = isContractor;
		this.userMenuPrivileges = userMenuPrivileges;
		this.hierarchyNodes = hierarchyNodes;
		this.initialPage = initialPage;
		this.additionalInfo = additionalInfo;
		this.mapView = mapView;
		this.role = role;
		this.createdBy = createdBy;
		this.dateCreated = dateCreated;
		this.updatedBy = updatedBy;
		this.dateUpdated = dateUpdated;
	}


	@Id
    @Column(name = "userid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment with bigserial
    private Long userId;

    @Column(name = "emailaddress", length = 45, nullable = false)
    private String emailAddress;

    @Column(name = "push", length = 45)
    private String push;

    @Column(name = "company_companyid", length = 20, nullable = false)
    private String companyCompanyId;

    @Column(name = "userpasswd", length = 400, nullable = false)
    private String userPassword;

    @Column(name = "firstname", length = 45, nullable = false)
    private String firstName;

    @Column(name = "lastname", length = 45, nullable = false)
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "vehiclesalloted")
    private Integer vehiclesAlloted;

    @Column(name = "primaryuser")
    private Boolean primaryUser;

    @Column(name = "contactno", length = 45)
    private String contactNo;

    @Column(name = "fax", length = 45)
    private String fax;

    @Column(name = "imgurl", length = 300)
    private String imgUrl;

    @Column(name = "mainmenu", columnDefinition = "TEXT")
    private String mainMenu;

    @Column(name = "expireddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredDate;

    @Column(name = "isminiapps")
    private Boolean isMiniApps;

    @Column(name = "iscontractor")
    private Boolean isContractor;

    @Column(name = "usermenuprivileges", length = 100)
    private String userMenuPrivileges;

    @Column(name = "hierarchynodes", length = 5000)
    private String hierarchyNodes;

    @Column(name = "initialpage", length = 100)
    private String initialPage;

    @Column(name = "additionalinfo", length = 500)
    private String additionalInfo;

    @Column(name = "mapview", length = 50)
    private String mapView;

    @Column(name = "role", nullable = false)
    private Long role;

    @Column(name = "createdby", length = 45)
    private String createdBy;

    @Column(name = "datecreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(name = "updatedby", length = 45)
    private String updatedBy;

    @Column(name = "dateupdated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPush() {
		return push;
	}

	public void setPush(String push) {
		this.push = push;
	}

	public String getCompanyCompanyId() {
		return companyCompanyId;
	}

	public void setCompanyCompanyId(String companyCompanyId) {
		this.companyCompanyId = companyCompanyId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getVehiclesAlloted() {
		return vehiclesAlloted;
	}

	public void setVehiclesAlloted(Integer vehiclesAlloted) {
		this.vehiclesAlloted = vehiclesAlloted;
	}

	public Boolean getPrimaryUser() {
		return primaryUser;
	}

	public void setPrimaryUser(Boolean primaryUser) {
		this.primaryUser = primaryUser;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(String mainMenu) {
		this.mainMenu = mainMenu;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Boolean getIsMiniApps() {
		return isMiniApps;
	}

	public void setIsMiniApps(Boolean isMiniApps) {
		this.isMiniApps = isMiniApps;
	}

	public Boolean getIsContractor() {
		return isContractor;
	}

	public void setIsContractor(Boolean isContractor) {
		this.isContractor = isContractor;
	}

	public String getUserMenuPrivileges() {
		return userMenuPrivileges;
	}

	public void setUserMenuPrivileges(String userMenuPrivileges) {
		this.userMenuPrivileges = userMenuPrivileges;
	}

	public String getHierarchyNodes() {
		return hierarchyNodes;
	}

	public void setHierarchyNodes(String hierarchyNodes) {
		this.hierarchyNodes = hierarchyNodes;
	}

	public String getInitialPage() {
		return initialPage;
	}

	public void setInitialPage(String initialPage) {
		this.initialPage = initialPage;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getMapView() {
		return mapView;
	}

	public void setMapView(String mapView) {
		this.mapView = mapView;
	}

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
    
    
    

}

