package com.eit.gateway.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "configs", schema = "mvt")
//@Getter
//@Setter

public class Configs {
	
	public Configs() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "displaytypes", columnDefinition = "TEXT")
    private String displayTypes;

    @Column(name = "alias", columnDefinition = "TEXT")
    private String alias;

    @Column(name = "discfg", columnDefinition = "TEXT")
    private String discfg;

    @Column(name = "datas", columnDefinition = "TEXT")
    private String datas;

    @Column(name = "whereconfig", columnDefinition = "TEXT")
    private String whereConfig;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDisplayTypes() {
		return displayTypes;
	}

	public void setDisplayTypes(String displayTypes) {
		this.displayTypes = displayTypes;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDiscfg() {
		return discfg;
	}

	public void setDiscfg(String discfg) {
		this.discfg = discfg;
	}

	public String getDatas() {
		return datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}

	public String getWhereConfig() {
		return whereConfig;
	}

	public void setWhereConfig(String whereConfig) {
		this.whereConfig = whereConfig;
	}

	public Configs(Long id, String displayTypes, String alias, String discfg, String datas, String whereConfig) {
		super();
		this.id = id;
		this.displayTypes = displayTypes;
		this.alias = alias;
		this.discfg = discfg;
		this.datas = datas;
		this.whereConfig = whereConfig;
	}
    
    
}

