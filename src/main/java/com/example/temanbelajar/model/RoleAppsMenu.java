package com.example.temanbelajar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user_role_apps_menu")
public class RoleAppsMenu extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Role.class,fetch=FetchType.LAZY)
	@MapsId("roleId")
	@JoinColumn(name="user_role_id")
	@JsonManagedReference
	private Role roleApps;
	
	@ManyToOne(targetEntity = AppsMenu.class,fetch=FetchType.LAZY)
	@MapsId("menuId")
	@JoinColumn(name="menu_id")
	@JsonManagedReference
	private AppsMenu appsMenu;

    @Column(name="request_type")
	private String requestType;

    
}