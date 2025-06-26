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
@Table(name = "histroydata", schema = "mvt")
//@Getter 
//@Setter 
public class HistoryData {
	
	public HistoryData() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Bigserial equivalent in JPA
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "companyid", length = 45, nullable = false)
    private String companyId;

    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @Column(name = "oldvalue", length = 1000, nullable = false)
    private String oldValue;

    @Column(name = "macip", length = 45)
    private String macIp;

    @Column(name = "type", length = 45)
    private String type;

    @Column(name = "lastupdby", length = 45, nullable = false)
    private String lastUpdBy;

    @Column(name = "lastupdt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdT;

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

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getMacIp() {
		return macIp;
	}

	public void setMacIp(String macIp) {
		this.macIp = macIp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Date getLastUpdT() {
		return lastUpdT;
	}

	public void setLastUpdT(Date lastUpdT) {
		this.lastUpdT = lastUpdT;
	}

	public HistoryData(Long id, String companyId, String name, String oldValue, String macIp, String type,
			String lastUpdBy, Date lastUpdT) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.name = name;
		this.oldValue = oldValue;
		this.macIp = macIp;
		this.type = type;
		this.lastUpdBy = lastUpdBy;
		this.lastUpdT = lastUpdT;
	}
    
    
}

