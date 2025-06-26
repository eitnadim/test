package com.eit.gateway.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "vehiclemodel", schema = "mvt")
//@Getter
//@Setter
public class VehicleModel {
	
	public VehicleModel() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @Column(name = "model", nullable = false, length = 50)
    private String model;

    @Column(name = "make", length = 45)
    private String make;

    @Column(name = "imgurl", length = 45)
    private String imgUrl;

    @Column(name = "remarks", length = 100)
    private String remarks;

    @Column(name = "lastupdby", length = 45)
    private String lastUpdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastupddt")
    private Date lastUpdDt;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public VehicleModel(String model, String make, String imgUrl, String remarks, String lastUpdBy, Date lastUpdDt) {
		super();
		this.model = model;
		this.make = make;
		this.imgUrl = imgUrl;
		this.remarks = remarks;
		this.lastUpdBy = lastUpdBy;
		this.lastUpdDt = lastUpdDt;
	}
    
    
}

