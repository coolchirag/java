package com.chirag.customejpa.entity;

import com.chirag.customejpa.annotations.ChildReferenceInfo;

public class Employee {

	private int id;

	private String employeeName;
	
	private Integer salary;
	
	@ChildReferenceInfo(refPropertyName="compnayToEmpMap", columnName = "companyName")
	private String cmpName;
	
	private Integer companyId;
	
	private Company compnayToEmpMap;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getCmpName() {
		return cmpName;
	}

	public void setCmpName(String cmpName) {
		this.cmpName = cmpName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Company getCompnayToEmpMap() {
		return compnayToEmpMap;
	}

	public void setCompnayToEmpMap(Company compnayToEmpMap) {
		this.compnayToEmpMap = compnayToEmpMap;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeName=" + employeeName + ", salary=" + salary + ", cmpName=" + cmpName
				+ ", companyId=" + companyId + ", compnayToEmpMap=" + compnayToEmpMap + "]";
	}
	
	
}
