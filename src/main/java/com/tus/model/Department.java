package com.tus.model;

public class Department {

	private String deptCode;

	private String name;

	private String location;

	private String salaryRange;

	private String manager;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSalaryRange() {
		return salaryRange;
	}

	public void setSalaryRange(String salaryRange) {
		this.salaryRange = salaryRange;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Department(String deptCode, String name, String location, String salaryRange, String manager) {
		super();
		this.deptCode = deptCode;
		this.name = name;
		this.location = location;
		this.salaryRange = salaryRange;
		this.manager = manager;
	}

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
