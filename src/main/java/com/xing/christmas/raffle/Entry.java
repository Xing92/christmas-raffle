package com.xing.christmas.raffle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Entry {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String presents;
	private Integer excludeGroup;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPresents() {
		return presents;
	}

	public void setPresents(String presents) {
		this.presents = presents;
	}

	public Integer getExcludeGroup() {
		return excludeGroup;
	}

	public void setExcludeGroup(Integer excludeGroup) {
		this.excludeGroup = excludeGroup;
	}

	@Override
	public String toString() {
		return "Entry [id=" + id + ", name=" + name + ", email=" + email + ", presents=" + presents + ", excludeGroup="
				+ excludeGroup + "]";
	}

}
