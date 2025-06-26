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
@Table(name = "operator", schema = "mvt")
//@Getter 
//@Setter 
public class Operator {
	
	public Operator() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Bigserial equivalent in JPA
    @Column(name = "operatorid", nullable = false)
    private Long operatorId;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "surname", length = 45)
    private String surname;

    @Column(name = "companyid", length = 45)
    private String companyId;

    @Column(name = "branchid", length = 45)
    private String branchId;

    @Column(name = "branchlocation")
    private String branchLocation;

    @Column(name = "telno", length = 45)
    private String telNo;

    @Column(name = "emailaddress", length = 45)
    private String emailAddress;

    @Column(name = "emergencycontactno", length = 45)
    private String emergencyContactNo;

    @Column(name = "licensenumber", length = 45)
    private String licenseNumber;

    @Column(name = "licenseissue")
    @Temporal(TemporalType.DATE)
    private Date licenseIssue;

    @Column(name = "licenseexpiry")
    @Temporal(TemporalType.DATE)
    private Date licenseExpiry;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "city", length = 45)
    private String city;

    @Column(name = "country", length = 45)
    private String country;

    @Column(name = "nationality", length = 45)
    private String nationality;

    @Column(name = "status", length = 45)
    private String status;

    @Column(name = "designation", length = 45)
    private String designation;

    @Column(name = "lastupdby", length = 45)
    private String lastUpdBy;

    @Column(name = "lastupddt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdDt;

    @Column(name = "tagid")
    private Long tagId;

    @Column(name = "state", length = 45)
    private String state;

    @Column(name = "postalcode")
    private Long postalCode;

    @Column(name = "dateofbirth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "dateofjoining")
    @Temporal(TemporalType.DATE)
    private Date dateOfJoining;

    @Column(name = "licencecategory", length = 45)
    private String licenceCategory;

    @Column(name = "insurencenum")
    private Long insuranceNum;

    @Column(name = "documents", columnDefinition = "TEXT")
    private String documents;

    @Column(name = "dateofleaving")
    @Temporal(TemporalType.DATE)
    private Date dateOfLeaving;

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

	public String getBranchLocation() {
		return branchLocation;
	}

	public void setBranchLocation(String branchLocation) {
		this.branchLocation = branchLocation;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmergencyContactNo() {
		return emergencyContactNo;
	}

	public void setEmergencyContactNo(String emergencyContactNo) {
		this.emergencyContactNo = emergencyContactNo;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Date getLicenseIssue() {
		return licenseIssue;
	}

	public void setLicenseIssue(Date licenseIssue) {
		this.licenseIssue = licenseIssue;
	}

	public Date getLicenseExpiry() {
		return licenseExpiry;
	}

	public void setLicenseExpiry(Date licenseExpiry) {
		this.licenseExpiry = licenseExpiry;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
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

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Long postalCode) {
		this.postalCode = postalCode;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getLicenceCategory() {
		return licenceCategory;
	}

	public void setLicenceCategory(String licenceCategory) {
		this.licenceCategory = licenceCategory;
	}

	public Long getInsuranceNum() {
		return insuranceNum;
	}

	public void setInsuranceNum(Long insuranceNum) {
		this.insuranceNum = insuranceNum;
	}

	public String getDocuments() {
		return documents;
	}

	public void setDocuments(String documents) {
		this.documents = documents;
	}

	public Date getDateOfLeaving() {
		return dateOfLeaving;
	}

	public void setDateOfLeaving(Date dateOfLeaving) {
		this.dateOfLeaving = dateOfLeaving;
	}

	public Operator(Long operatorId, String name, String surname, String companyId, String branchId,
			String branchLocation, String telNo, String emailAddress, String emergencyContactNo, String licenseNumber,
			Date licenseIssue, Date licenseExpiry, String address, String city, String country, String nationality,
			String status, String designation, String lastUpdBy, Date lastUpdDt, Long tagId, String state,
			Long postalCode, Date dateOfBirth, Date dateOfJoining, String licenceCategory, Long insuranceNum,
			String documents, Date dateOfLeaving) {
		super();
		this.operatorId = operatorId;
		this.name = name;
		this.surname = surname;
		this.companyId = companyId;
		this.branchId = branchId;
		this.branchLocation = branchLocation;
		this.telNo = telNo;
		this.emailAddress = emailAddress;
		this.emergencyContactNo = emergencyContactNo;
		this.licenseNumber = licenseNumber;
		this.licenseIssue = licenseIssue;
		this.licenseExpiry = licenseExpiry;
		this.address = address;
		this.city = city;
		this.country = country;
		this.nationality = nationality;
		this.status = status;
		this.designation = designation;
		this.lastUpdBy = lastUpdBy;
		this.lastUpdDt = lastUpdDt;
		this.tagId = tagId;
		this.state = state;
		this.postalCode = postalCode;
		this.dateOfBirth = dateOfBirth;
		this.dateOfJoining = dateOfJoining;
		this.licenceCategory = licenceCategory;
		this.insuranceNum = insuranceNum;
		this.documents = documents;
		this.dateOfLeaving = dateOfLeaving;
	}
    
    
    
}

