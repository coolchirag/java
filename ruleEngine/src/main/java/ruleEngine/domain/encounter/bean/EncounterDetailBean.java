package ruleEngine.domain.encounter.bean;

import java.util.List;
import java.util.Set;

public class EncounterDetailBean {

	public String patientClass;
	List<String> diagnosisCodes;
	protected String payer;
	private String physician;
	private List<CPTCode> cptCodes;
	
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

	public static class CPTCode {
		private String code;
		private Integer units;
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
