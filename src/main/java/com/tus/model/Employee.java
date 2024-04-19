package com.tus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String emailId;
	
	@Column(name = "mobile")
	private long mobile;
	
	@Column(name = "department")
	private String department;
	
	public Employee() {
	}

	public Employee(String name, String emailId, long mobile, String department) {
		super();
		this.name = name;
		this.emailId = emailId;
		this.mobile = mobile;
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public long getId() {
		return id;
	}

	public void setId(long mobile) {
		this.id = mobile;
	}

	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	
}
