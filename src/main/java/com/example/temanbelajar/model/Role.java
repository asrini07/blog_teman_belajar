package com.example.temanbelajar.model;

import java.util.Date;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "role", uniqueConstraints = { @UniqueConstraint(columnNames = {"role_title"}) })
public class Role extends AuditModel implements GrantedAuthority{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_role_id")
    private Integer id;

    @Column(name="role_title")
	private String roletitle;

    @Column(length = 150, nullable = false)
    @Size(min = 3, max = 150)
    @NotBlank(message = "name kosong")
    private String name;

    @Column(length = 150, nullable = false)
    @Size(min = 3, max = 150)
    @NotBlank(message = "desc kosong")
    private String description;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "role",fetch = FetchType.LAZY)
	@JsonManagedReference(value = "role-rolemember")
	Set <RoleMember> roleMember;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "roleApps",fetch = FetchType.LAZY)
	@JsonManagedReference(value = "role-roleappsmenu")
	Set<RoleAppsMenu> roleAppsmenu;

    // @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    // @JoinTable(
    //     name = "role_menu", 
    //     joinColumns = { @JoinColumn(name = "role_id") }, 
    //     inverseJoinColumns = { @JoinColumn(name = "menu_id") }
    // )
    // private List<Menu> menu = new ArrayList<>();

    public Role()
	{
		
	}
	
	
	public Role(int id, String roletitle, String description, Set<RoleMember> roleMember, Set<RoleAppsMenu> roleAppsmenu) {
		super();
		this.id = id;
		this.roletitle = roletitle;
		this.description = description;
		// this.create_time = create_time;
		// this.update_time = update_time;
		// this.delete_time = delete_time;
		// this.is_deleted = is_deleted;
		this.roleMember = roleMember;
		this.roleAppsmenu = roleAppsmenu;
    }
    
    @Override
	public String getAuthority() {
		return this.getRoletitle();
	}

    
}