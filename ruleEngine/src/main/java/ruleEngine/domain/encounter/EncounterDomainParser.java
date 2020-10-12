package ruleEngine.domain.encounter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ruleEngine.domain.DomainParser;
import ruleEngine.domain.encounter.bean.EncounterDetailBean;

public class EncounterDomainParser implements DomainParser {

	private static final String PARAM_INDICATOR = "param_";
	private static final Integer PARAM_INDICATER_LENGTH = PARAM_INDICATOR.length();
	/*
	 * private static final Map<String, String> fieldToNameMap = new HashMap<String,
	 * String>(); static { fieldToNameMap.put("patientClass", "patientClass");
	 * fieldToNameMap.put("diagnosisCodes", "diagnosisCodes");
	 * 
	 * fieldToNameMap.put("payer", "payer"); fieldToNameMap.put("physician",
	 * "physician"); fieldToNameMap.put("cptCodeStr", "cptCodes.code");
	 * fieldToNameMap.put("cptCodeUnits", "cptCodes.units"); fieldToNameMap.put("",
	 * "");}
	 */
	
	private EncounterDetailBean encDetailBean;
	
	
	
	public EncounterDomainParser(EncounterDetailBean encDetailBean) {
		super();
		this.encDetailBean = encDetailBean;
	}



	public String[] parse(String[] ruleExpression) throws Exception {
		for(String data : ruleExpression) {
			if(data.startsWith("param_"))
			{
				String param = data.substring(PARAM_INDICATER_LENGTH);
				String value = getParamValue(param, encDetailBean);
				System.out.println(value);
				data = value;
			}
		}
		return null;
	}
	
	private String getParamValue(String param, Object obj) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		/*
		 * System.out.println(
		 * "-------------------------------------------------------------"); for(Field f
		 * : obj.getClass().getDeclaredFields()) {
		 * System.out.println(f.getName()+" : "+f.getType()+" : "+f.getGenericType());
		 * if (f.getGenericType() instanceof ParameterizedType) { ParameterizedType pt =
		 * (ParameterizedType) f.getGenericType();
		 * System.out.println("RawType : "+pt.getRawType());
		 * System.out.println("Owner type : "+pt.getOwnerType());
		 * System.out.println("actual type args:"); for (Type t :
		 * pt.getActualTypeArguments()) { System.out.println("    " + t); }
		 * 
		 * } }
		 */
		if(param.contains(".")) {
			int spliterIndex = param.indexOf(".");
			Field field = obj.getClass().getDeclaredField(param.substring(0, spliterIndex));
			field.setAccessible(true);
			Type genericType = field.getGenericType();
			String value;
			if(genericType instanceof ParameterizedType) {
				value="[";
				Collection c = (Collection) field.get(obj);
				for(Object data : c) {
					value = value + getParamValue(param.substring(spliterIndex+1), data)+",";
				}
				value=value.substring(0,value.length()-1);
				value=value+"]";
			} else {
				value = getParamValue(param.substring(spliterIndex+1), field.get(obj));
			}
			
			return value;
		}
		
		Field field = obj.getClass().getDeclaredField(param);
		field.setAccessible(true);
		Type genericType = field.getGenericType();
		String value;
		if(genericType instanceof ParameterizedType) {
			value = "[";
			Collection c = (Collection) field.get(obj);
			for(Object data : c) {
				value = value + data + ",";
			}
			value=value.substring(0,value.length()-1);
			value=value+"]";
		} else {
			value = (String) field.get(obj);
		}
		return value;
	}

}
