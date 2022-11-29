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
	private String presents1;
	private String presents2;
	private String presents3;
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
	
	public String getPresents1() {
		return presents1;
	}

	public void setPresents1(String presents1) {
		this.presents1 = presents1;
	}

	public String getPresents2() {
		return presents2;
	}

	public void setPresents2(String presents2) {
		this.presents2 = presents2;
	}

	public String getPresents3() {
		return presents3;
	}

	public void setPresents3(String presents3) {
		this.presents3 = presents3;
	}

	public Integer getExcludeGroup() {
		return excludeGroup;
	}

	public void setExcludeGroup(Integer excludeGroup) {
		this.excludeGroup = excludeGroup;
	}

	@Override
	public String toString() {
		return "Entry [id=" + id + ", name=" + name + ", email=" + email + ", presents=" + presents1 + ", excludeGroup="
				+ excludeGroup + "]";
	}

}
