package com.eit.gateway.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "vehicletype", schema = "mvt")
//@Getter
//@Setter
public class VehicleType {
	public VehicleType() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @Column(name = "vehicletype", nullable = false, length = 45)
    private String vehicleType;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "lastupdby", length = 45)
    private String lastUpdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastupddt")
    private Date lastUpdDt;

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public VehicleType(String vehicleType, String description, String lastUpdBy, Date lastUpdDt) {
		super();
		this.vehicleType = vehicleType;
		this.description = description;
		this.lastUpdBy = lastUpdBy;
		this.lastUpdDt = lastUpdDt;
	}
    
    
}

