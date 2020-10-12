package ruleEngine;

import java.util.Arrays;
import java.util.Collections;

import ruleEngine.domain.encounter.EncounterDomainParser;
import ruleEngine.domain.encounter.bean.EncounterDetailBean;
import ruleEngine.domain.encounter.bean.EncounterDetailBean.CPTCode;

public class MainClass {

	public static void main(String[] args) throws Exception {
		String rule = "param_patientClass IN [ inpatient, outpatient ] AND param_payer EQUAL testPayer";
		//String rule = "param_diagnosisCodes";
		//String rule = "param_cptCodes.code";
		String[] ruleArray = rule.split(" ");
		
		
		
		
		EncounterDomainParser encDomainParser = new EncounterDomainParser(generateEncounter());
		encDomainParser.parse(ruleArray);
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
}
