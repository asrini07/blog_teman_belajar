package com.example.temanbelajar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.temanbelajar.view.View;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user_role_member")
@Where(clause = "is_deleted = 0")
@DynamicUpdate
public class RoleMember implements Serializable{

    private static final long serialVersionUID = -6762220426121894923L;
    
    @EmbeddedId
	UserKey id;
	
	@ManyToOne(targetEntity = Author.class,fetch=FetchType.LAZY)
	@MapsId("author_id")
	@JoinColumn(name="author_id", insertable=false,  updatable=false)
	@JsonBackReference(value = "user-rolemember")
	private  Author author;
	
	@ManyToOne(targetEntity = Role.class,fetch=FetchType.LAZY)
	@MapsId("user_role_id")
	@JoinColumn(name="user_role_id", insertable=false,  updatable=false)
	@JsonBackReference(value = "role-rolemember")
    private  Role role;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
	@JsonView(View.Internal.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at",updatable=false)
	private Date created_at;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
	@JsonView(View.Public.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updated_at;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
	@JsonView(View.Public.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="deleted_at")
	private Date deleted_at;
	
	@Column(name="is_deleted")
	private Integer is_deleted;

    public RoleMember(UserKey id, Author author, Role role, Date created_at, Date updated_at, Date deleted_at,
			Integer is_deleted) {
		this.id = id;
		this.author = author;
		this.role = role;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.deleted_at = deleted_at;
		this.is_deleted = is_deleted;
	}

	public RoleMember() {
		
	}
    
}