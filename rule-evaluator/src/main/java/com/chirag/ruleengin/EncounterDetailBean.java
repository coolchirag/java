package com.chirag.ruleengin;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class EncounterDetailBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String patientClass;
	List<String> diagnosisCodes;
	protected String payer;
	private String physician;
	private List<CPTCode> cptCodes;
	private Deficiency deficiency;
	private Integer test1;
	private Integer test2;
	
	
	
	public Integer getTest1() {
		return test1;
	}
	public void setTest1(Integer test1) {
		this.test1 = test1;
	}
	public Integer getTest2() {
		return test2;
	}
	public void setTest2(Integer test2) {
		this.test2 = test2;
	}
	public String getPatientClass() {
		return patientClass;
	}
	public void setPatientClass(String patientClass) {
		this.patientClass = patientClass;
	}
	public List<String> getDiagnosisCodes() {
		return diagnosisCodes;
	}
	public void setDiagnosisCodes(List<String> diagnosisCodes) {
		this.diagnosisCodes = diagnosisCodes;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getPhysician() {
		return physician;
	}
	public void setPhysician(String physician) {
		this.physician = physician;
	}
	public List<CPTCode> getCptCodes() {
		return cptCodes;
	}
	public void setCptCodes(List<CPTCode> cptCodes) {
		this.cptCodes = cptCodes;
	}
	public Deficiency getDeficiency() {
		return deficiency;
	}
	public void setDeficiency(Deficiency deficiency) {
		this.deficiency = deficiency;
	}

	public static class Deficiency implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String code;
		private String type;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
	}
	public static class CPTCode implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String code;
		private Integer units;
		private Integer physicianId;
		private List<String> modifiers;
		
		
		
		public List<String> getModifiers() {
			return modifiers;
		}
		public void setModifiers(List<String> modifiers) {
			this.modifiers = modifiers;
		}
		public Integer getPhysicianId() {
			return physicianId;
		}
		public void setPhysicianId(Integer physicianId) {
			this.physicianId = physicianId;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public Integer getUnits() {
			return units;
		}
		public void setUnits(Integer units) {
			this.units = units;
		}
		
		
	}
	
}
