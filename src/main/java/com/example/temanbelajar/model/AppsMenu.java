package com.example.temanbelajar.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "menu")
public class AppsMenu extends AuditModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="menu_id")
    private Long id;

    @Column(name="parent_menu_id")
    private Long parent_menu_id;
    
    @Column(length = 150, nullable = false)
    @Size(min = 3, max = 150)
    @NotBlank
    private String label_name;
    
    @Column(length = 150, nullable = false)
    @Size(min = 3, max = 150)
    @NotBlank
    private String hint_name;

    @Column(name="menu_link")
	private String menu_link;
	
	@Column(name="menu_icon")
	private String menu_icon;
	
	@Column(name="is_menu_active")
	private Integer menu_status;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "appsMenu",fetch = FetchType.LAZY)
	@JsonManagedReference(value = "menu-roleappsmenu")
    private Set<RoleAppsMenu> roleappsmenu;
    

}