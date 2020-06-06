package com.example.temanbelajar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class UserKey implements Serializable {

    @Column(name = "author_id")
	private long author_id;
	
	@Column(name="user_role_id")
    private int role_id;
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (role_id ^ (role_id >>> 32));
		result = prime * result + (int) (author_id ^ (author_id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserKey other = (UserKey) obj;
		if (role_id != other.role_id)
			return false;
		if (author_id != other.author_id)
			return false;
		return true;
	}

    
}