package ruleEngine;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ruleEngine.domain.encounter.EncounterDomainParser;
import ruleEngine.domain.encounter.bean.EncounterDetailBean;
import ruleEngine.domain.encounter.bean.EncounterDetailBean.CPTCode;

public class MainClass {

	public static void main(String[] args) throws Exception {
		//String rule = "param_patientClass IN [inpatient,outpatient] AND param_payer EQUAL testPayer";
		//String rule = "param_cptCodes.code IN [cpt1] AND param_cptCodes.physicianId IN [1,2]"; //OR param_patientClass IN [inpatient,outpatient]";
		//String rule = "( param_cptCodes.code IN [cpt1] AND param_cptCodes.physicianId IN [1,2] ) OR ( param_cptCodes.code IN [cpt2] AND param_cptCodes.physicianId IN [3,4] )"; 
		String rule = "param_diagnosisCodes IN [param_physician]";
		//String rule = "param_cptCodes.code";
		String[] ruleArray = rule.split(" ");
		
		/*
		 * EncounterDomainParser encDomainParser = new
		 * EncounterDomainParser(generateEncounter()); String[] data =
		 * encDomainParser.parse(ruleArray);
		 */
		
		OptimizedRuleEngine op = new OptimizedRuleEngine(ruleArray);
		System.out.println(op.execution(generateEncounter()));
	}
	
	
	private static EncounterDetailBean generateEncounter() {
		EncounterDetailBean obj = new EncounterDetailBean();
		obj.setPatientClass("inpatient");
		obj.setPayer("testpayer");
		obj.setDiagnosisCodes(Arrays.asList("code1","code2","phy1"));
		obj.setPhysician("phy1");
		CPTCode cptCode1 = new CPTCode();
		cptCode1.setCode("cpt1");
		cptCode1.setUnits(2);
		cptCode1.setPhysicianId(1);
		CPTCode cptCode2 = new CPTCode();
		cptCode2.setCode("cpt2");
		cptCode2.setUnits(2);
		cptCode2.setPhysicianId(3);
		obj.setCptCodes(new ArrayList<EncounterDetailBean.CPTCode>(Arrays.asList(cptCode1, cptCode2)));
		return obj;
	}
	
	public static void main2(String[] args) throws Exception {
		EncounterDetailBean obj = generateEncounter();
		Field[] declaredFields = CPTCode.class.getDeclaredFields();
		for(Field f : declaredFields) {
			System.out.println(f.getName()+" : "+f.getGenericType());
		}
		
		Field cptCodeField = EncounterDetailBean.class.getDeclaredField("cptCodes");
		Type genericType = cptCodeField.getGenericType();
		if (genericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) genericType;
			for(Type t : pt.getActualTypeArguments()) {
				System.out.println("--------------------------"+t);
				Field[] fields = ((Class)t).getDeclaredFields();
				for(Field f : fields) {
					System.out.println(f.getName()+" : "+f.getGenericType());
				}
			}
		}
		
	}
}
