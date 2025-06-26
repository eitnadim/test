package com.eit.gateway.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "vehicle_has_operator", schema = "mvt")
//@Getter
//@Setter
public class VehicleHasOperator {
	
	public VehicleHasOperator() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "efffrom", nullable = false)
    private Date effFrom;

    @Column(name = "vehicle_vin", nullable = false, length = 20)
    private String vehicleVin;

    @Column(name = "operator_operatorid", nullable = false, length = 50)
    private String operatorOperatorId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "effto")
    private Date effTo;

    @Column(name = "lastupdby", length = 45)
    private String lastUpdatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastupddt")
    private Date lastUpdatedDt;

	public Date getEffFrom() {
		return effFrom;
	}

	public void setEffFrom(Date effFrom) {
		this.effFrom = effFrom;
	}

	public String getVehicleVin() {
		return vehicleVin;
	}

	public void setVehicleVin(String vehicleVin) {
		this.vehicleVin = vehicleVin;
	}

	public String getOperatorOperatorId() {
		return operatorOperatorId;
	}

	public void setOperatorOperatorId(String operatorOperatorId) {
		this.operatorOperatorId = operatorOperatorId;
	}

	public Date getEffTo() {
		return effTo;
	}

	public void setEffTo(Date effTo) {
		this.effTo = effTo;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(Date lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	public VehicleHasOperator(Date effFrom, String vehicleVin, String operatorOperatorId, Date effTo,
			String lastUpdatedBy, Date lastUpdatedDt) {
		super();
		this.effFrom = effFrom;
		this.vehicleVin = vehicleVin;
		this.operatorOperatorId = operatorOperatorId;
		this.effTo = effTo;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedDt = lastUpdatedDt;
	}
    
    
}

