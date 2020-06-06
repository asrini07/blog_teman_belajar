package com.example.temanbelajar.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "author")
@Getter
@Setter
@ToString
public class Author extends AuditModel implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    @NotBlank(message = "Firstname must not be empty")
    @Size(min = 3, max = 45, message = "Firstname min 3 and max 45 character")
    private String first_name;
    
    @Column(length = 45)
    @NotBlank(message = "Lastname must not be empty")
    @Size(min = 3, max = 45, message = "Lastname min 3 and max 45 character")
    private String last_name;

    @Column(length = 45, unique = true, nullable = false)
    @NotBlank(message = "Username must not be empty")
    @Size(min = 3, max = 45, message = "Username min 3 and max 45 character")
    private String username;

    @Column(length = 150, nullable = false)
    @NotBlank(message = "Password must not be empty")
    @Size(min = 3, max = 150)
    private String password;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	@JsonManagedReference(value = "user-rolemember")
    Set<RoleMember> roleMember;
	
	// public void setAuthorities(Collection<Role> authorities) {
	// 	this.authorities = authorities;
	// }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_member", joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_role_id", referencedColumnName = "user_role_id"))
    @OrderBy
    @JsonIgnore
    private Collection<Role> authorities;

    @Setter
	@Transient
	private String role = "NONE";

	public Integer getRoleId() {
		Integer roleId = 0;

		try {
			for (RoleMember roleMember: this.getRoleMember()) {
				if (roleId == 0) {
					roleId = roleMember.getRole().getId();
				}
			}
		} catch (Exception e) {
			roleId = 0;
		}
		
		return roleId;
	}


    
    public String getRole() {
		String role = "NONE";
		try {
		
			
			for(RoleMember roleMember: this.getRoleMember()) {
				if ( role.equals("NONE")) {
					role = roleMember.getRole().getRoletitle();
				}
			}
		} catch (Exception e) {
			role = "NONE";
		}
		
		return role;
    }
    
    
    public void setAuthorities(Collection<Role> authorities) {
		this.authorities = authorities;
	}
    
	public Set<RoleMember> getRoleMember() {
		return roleMember;
	}

	public void setRoleMember(Set<RoleMember> roleMember) {
		this.roleMember = roleMember;
	}

	

    // @Override
	// public Collection<? extends GrantedAuthority> getAuthorities() {
	// 	return this.authorities;
	// }

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

  
}