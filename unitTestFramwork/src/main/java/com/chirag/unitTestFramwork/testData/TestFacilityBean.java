package com.chirag.unitTestFramwork.testData;

import java.io.Serializable;

import com.chirag.unitTestFramwork.annotation.NotNull;
import com.chirag.unitTestFramwork.annotation.ValidTextSize;

public class TestFacilityBean extends FacilityMasterCreateRequest implements Serializable{

	@NotNull
	@ValidTextSize(max=5)
	private String name;

	

	
}
