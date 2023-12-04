package com.xing.christmas.raffle;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
	@ElementCollection
	@Column(length = 10_000)
	private List<String> presents = new ArrayList<>();
	@Column(length = 10_000)
	private String nextPresent;
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

	public Integer getExcludeGroup() {
		return excludeGroup;
	}

	public void setExcludeGroup(Integer excludeGroup) {
		this.excludeGroup = excludeGroup;
	}

	public List<String> getPresents() {
		return presents;
	}

	public void setPresents(List<String> presents) {
		this.presents = presents;
	}

	public void addPresent(String present) {
		this.presents.add(present);
	}

	public String getNextPresent() {
		return nextPresent;
	}

	public void setNextPresent(String nextPresent) {
		this.nextPresent = nextPresent;
	}

	@Override
	public String toString() {
		return "Entry [id=" + id + ", name=" + name + ", email=" + email + ", presents=" + presents + ", nextPresent="
				+ nextPresent + ", excludeGroup=" + excludeGroup + "]";
	}

}
