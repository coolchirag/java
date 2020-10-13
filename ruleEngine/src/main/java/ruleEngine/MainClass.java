package ruleEngine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;

import ruleEngine.domain.encounter.EncounterDomainParser;
import ruleEngine.domain.encounter.bean.EncounterDetailBean;
import ruleEngine.domain.encounter.bean.EncounterDetailBean.CPTCode;

public class MainClass {

	public static void main(String[] args) throws Exception {
		//String rule = "param_patientClass IN [ inpatient, outpatient ] AND param_payer EQUAL testPayer";
		String rule = "param_diagnosisCodes";
		//String rule = "param_cptCodes.code";
		String[] ruleArray = rule.split(" ");
		
		EncounterDomainParser encDomainParser = new EncounterDomainParser(generateEncounter());
		String[] data = encDomainParser.parse(ruleArray);
	}
	
	private static EncounterDetailBean generateEncounter() {
		EncounterDetailBean obj = new EncounterDetailBean();
		obj.setPatientClass("inpatient");
		obj.setPayer("testpayer");
		obj.setDiagnosisCodes(Arrays.asList("code1","code2"));
		obj.setPhysician("phy1");
		CPTCode cptCode = new CPTCode();
		cptCode.setCode("cpt1");
		cptCode.setUnits(2);
		obj.setCptCodes(Collections.singletonList(cptCode));
		return obj;
	}
	
	public static void main2(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String data1 = "5";
		String data2 = "6";
		Class c = Integer.class;
		//((c) data1)
		Method[] methods = c.getMethods();
		Method method = c.getMethod("equals", Object.class);
		method.invoke(data1, data2);
		System.out.println(methods);
	}
}
