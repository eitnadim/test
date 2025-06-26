package com.eit.gateway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role", schema = "mvt")
//@Getter 
//@Setter 
public class Role {
	
	public Role() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "rolename", nullable = false)
    private String roleName;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Role(Long roleId, String roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}
    
    
}

