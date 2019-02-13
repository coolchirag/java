package com.chirag.unitTestFramwork.testData;

import java.util.HashSet;
import java.util.Set;

import com.chirag.unitTestFramwork.annotation.FieldType;
import com.chirag.unitTestFramwork.annotation.FieldType.Type;
import com.chirag.unitTestFramwork.bean.AbstractTestBean;
import com.chirag.unitTestFramwork.annotation.MustExist;
import com.chirag.unitTestFramwork.annotation.NoDuplicate;
import com.chirag.unitTestFramwork.annotation.NotNull;
import com.chirag.unitTestFramwork.annotation.RequestResourceMapping;

public class TestCasebean extends AbstractTestBean {
	
	TestFacilityBean request;
	
	Set<String> requiredPermission;
	@FieldType(Type.RESOURCE)
	@NoDuplicate({
		@RequestResourceMapping(requestField="request.name", resourceField="name"),
		@RequestResourceMapping(requestField="request.name", resourceField="name")
	})
	//@MustExist
	TestFacilityResosurceBean resource;
	public TestCasebean() {
		super();
		request = new TestFacilityBean();
		request.setName("hello");
		resource = new TestFacilityResosurceBean();
		resource.setName("hi");
		requiredPermission = new HashSet<>();
	}
	public TestFacilityBean getRequest() {
		return request;
	}
	public void setRequest(TestFacilityBean request) {
		this.request = request;
	}
	public Set<String> getRequiredPermission() {
		return requiredPermission;
	}
	public void setRequiredPermission(Set<String> requiredPermission) {
		this.requiredPermission = requiredPermission;
	}
	public TestFacilityResosurceBean getResource() {
		return resource;
	}
	public void setResource(TestFacilityResosurceBean resource) {
		this.resource = resource;
	}
	
	
	
	
	
}
