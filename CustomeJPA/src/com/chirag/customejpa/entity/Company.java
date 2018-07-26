package com.chirag.customejpa.entity;

import java.util.List;

import com.chirag.customejpa.annotations.ChildReferenceInfo;
import com.chirag.customejpa.annotations.ChildReferenceInfos;



public class Company {

	private int id;

	@ChildReferenceInfos({@ChildReferenceInfo(refPropertyName="compnayToEmpMap", columnName = "companyName")})
	private String companyName;
	
	private String city;
	
	private List<Employee> employeeList;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName + ", city=" + city + ", employeeList="
				+ employeeList + "]";
	}
	
}
