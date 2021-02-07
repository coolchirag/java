package ruleEngine;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ruleEngine.relational.*;

public class DataType extends DoubleRelationalEngine {

	private static final Map<Class, DataTypeEnum> dataTypeMap = new HashMap<Class, DataType.DataTypeEnum>();
	
	static {
		dataTypeMap.put(String.class, DataTypeEnum.STRING);
		dataTypeMap.put(Integer.class, DataTypeEnum.LONG);
		dataTypeMap.put(Long.class, DataTypeEnum.LONG);
		dataTypeMap.put(Float.class, DataTypeEnum.DOUBLE);
		dataTypeMap.put(Double.class, DataTypeEnum.DOUBLE);
		dataTypeMap.put(Date.class, DataTypeEnum.DATE);
		
	}
	
	public enum DataTypeEnum {
		STRING(new StringRelationalEngine()),
		LONG(new LongRelationalEngine()),
		DOUBLE(new DoubleRelationalEngine()),
		DATE(new DateRelationalEngine());
		
		private RelationalEngine relationalEngine;

		private DataTypeEnum(RelationalEngine relationalEngine) {
			this.relationalEngine = relationalEngine;
		}

		public RelationalEngine getRelationalEngine() {
			return relationalEngine;
		}

		
		
	}
	
	public static DataTypeEnum getDataTypeEnum(Class className) {
		return dataTypeMap.get(className);
	}
}
