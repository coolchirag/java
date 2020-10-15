package ruleEngine.domain.encounter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import ruleEngine.DataType;
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
		String[] parsedData = new String[ruleExpression.length];
		int index = 0;
		for(String data : ruleExpression) {
			if(data.startsWith("param_"))
			{
				String param = data.substring(PARAM_INDICATER_LENGTH);
				String value = getParamValue(param, encDetailBean);
				System.out.println(value);
				parsedData[index] = value;
				
			}
		}
		return parsedData;
	}
	
	public static String getParamValue(String param, Object obj) throws Exception {
		
		if(param.contains(".")) {
			int spliterIndex = param.indexOf(".");
			Field field = obj.getClass().getDeclaredField(param.substring(0, spliterIndex));
			field.setAccessible(true);
			Type genericType = field.getGenericType();
			String value;
			if (genericType instanceof ParameterizedType) {
				
				value = "[";
				Collection c = (Collection) field.get(obj);
				for (Object data : c) {
					value = value + getParamValue(param.substring(spliterIndex + 1), data) + ",";
				}
				value = value.substring(0, value.length() - 1);
				value = value + "]";
			} else {
				value = getParamValue(param.substring(spliterIndex+1), field.get(obj));
			}
			
			return dataTypeAppender(genericType)+value;
		}
		
		Field field = obj.getClass().getDeclaredField(param);
		field.setAccessible(true);
		Type genericType = field.getGenericType();
		String value;
		if(genericType instanceof ParameterizedType) {
			
			value = "[";
			Collection c = (Collection) field.get(obj);
			for(Object data : c) {
				System.out.println(data.getClass());
				value = value + data + ",";
			}
			value=value.substring(0,value.length()-1);
			value=value+"]";
		} else {
			value =(String) field.get(obj);
		}
		return dataTypeAppender(genericType)+value;
	}
	
	private static String dataTypeAppender(Type dataType) {
		if(dataType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) dataType;
			dataType = pt.getActualTypeArguments()[0];
		}
		return "dataType_"+DataType.getDataTypeEnum((Class) dataType)+"_";
	}

}
