package com.eit.gateway.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "geocode_address", schema = "mvt")
//@Getter 
//@Setter 
public class GeocodeAddress {
	
	public GeocodeAddress() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @Column(name = "latlng", length = 100, nullable = false)
    private String latLng;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

	public String getLatLng() {
		return latLng;
	}

	public void setLatLng(String latLng) {
		this.latLng = latLng;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
    
    
	public GeocodeAddress(String latLng, String address, Date createdDate) {
		super();
		this.latLng = latLng;
		this.address = address;
		this.createdDate = createdDate;
	}
}

