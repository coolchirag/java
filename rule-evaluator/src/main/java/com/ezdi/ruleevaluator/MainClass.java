package com.ezdi.ruleevaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainClass {
	

	public static void main(String[] args) throws Exception {
		StringBuilder sb = new StringBuilder();
		//String rule = "field_patientClass NOTIN [inpatient,outpatient] AND field_payer EQUALTO testPayer";
		//String rule = "field_cptCodes.code IN [cpt1] AND field_cptCodes.physicianId IN [1,2] OR field_patientClass IN [inpatient,outpatient]";
		
		//String rule = "field_test1 EQUALTO field_test2";
		String rule = "( field_cptCodes.code NOTIN [cpt1] AND field_cptCodes.physicianId IN [1,2] )"; 
		//String rule = "( field_cptCodes.code IN [cpt1] AND field_cptCodes.physicianId IN [1,2] ) OR ( field_cptCodes.code IN [cpt2] AND field_cptCodes.physicianId IN [3,4] )";
		//String rule = "( field_cptCodes.code EQUALTO cpt1 AND field_cptCodes.code NOTEQUALTO cpt1 )";
		String rule2 = "field_cptCodes.modifiers EQUALTO 50";
		//String rule = "( ( field_cptCodes.modifiers EQUALTO 50 AND field_cptCodes.code IN [cpt3] ) OR field_cptCodes.code IN [cpt3] )";
		//String rule = "( field_cptCodes.code EQUALTO cpt1 AND field_cptCodes.code ISDUPLICATE )";
		//String rule = "( field_diagnosisCodes EQUALTO phy1 AND field_diagnosisCodes ISDUPLICATE )";
		
		//String rule = "field_diagnosisCodes IN [field_physician]";
		//String rule = "field_cptCodes.code";
		
		EncounterDetailBean data = generateEncounter();
		
		RuleEvaluator obj = new RuleEvaluator(rule, sb);
		System.out.println(obj.execution(data)+", LOG : "+sb);
		
		sb = new StringBuilder();
		RuleEvaluator obj2 = new RuleEvaluator(rule2, sb);
		//System.out.println(obj2.execution(data)+", LOG : "+sb);
	}
	
	private static EncounterDetailBean generateEncounter() {
		EncounterDetailBean obj = new EncounterDetailBean();
		obj.setPatientClass("inpatient");
		obj.setPayer("testpayer2");
		obj.setDiagnosisCodes(new ArrayList<String>(Arrays.asList("code1","code1","phy1")));
		obj.setPhysician("phy1");
		obj.setTest1(5);
		EncounterDetailBean.CPTCode cptCode1 = new EncounterDetailBean.CPTCode();
		//cptCode1.setCode("cpt1");
		cptCode1.setUnits(2);
		cptCode1.setPhysicianId(1);
		cptCode1.setModifiers(new ArrayList<String>());
		EncounterDetailBean.CPTCode cptCode2 = new EncounterDetailBean.CPTCode();
		cptCode2.setCode("cpt2");
		cptCode2.setUnits(2);
		cptCode2.setPhysicianId(3);
		List<String> modifiers = new ArrayList<>();
		modifiers.add("50");
		cptCode2.setModifiers(modifiers);
		
		EncounterDetailBean.CPTCode cptCode3 = new EncounterDetailBean.CPTCode();
		cptCode3.setCode("cpt3");
		cptCode3.setUnits(2);
		cptCode3.setPhysicianId(3);
		
		obj.setCptCodes(new ArrayList<EncounterDetailBean.CPTCode>(Arrays.asList(cptCode1, cptCode2)));
		return obj;
	}
}
