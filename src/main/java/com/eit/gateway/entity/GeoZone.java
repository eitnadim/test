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
@Table(name = "geozones", schema = "mvt")
//@Getter
//@Setter
public class GeoZone {
	
	public GeoZone() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Bigserial equivalent in JPA
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "companyid", length = 255)
    private String companyId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "type", length = 50, nullable = false)
    private String type;

    @Column(name = "geometry", columnDefinition = "TEXT")
    private String geometry;

    @Column(name = "additionalinfo", columnDefinition = "TEXT")
    private String additionalInfo;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT now() AT TIME ZONE 'Asia/Riyadh'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT now() AT TIME ZONE 'Asia/Riyadh'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGeometry() {
		return geometry;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public GeoZone(Long id, String companyId, String name, String type, String geometry, String additionalInfo,
			String description, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.name = name;
		this.type = type;
		this.geometry = geometry;
		this.additionalInfo = additionalInfo;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
    
    
    
}

