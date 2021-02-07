package com.chirag.ruleengine.utility;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.chirag.ruleengine.relational.*;

public class OperandDataTypeUtility {

	private static final Map<Class, OperandDataTypeEnum> OPERAND_DATATYPE_MAP = new HashMap<Class, OperandDataTypeEnum>();

	static {
		OPERAND_DATATYPE_MAP.put(String.class, OperandDataTypeEnum.STRING);
		OPERAND_DATATYPE_MAP.put(Integer.class, OperandDataTypeEnum.LONG);
		OPERAND_DATATYPE_MAP.put(Long.class, OperandDataTypeEnum.LONG);
		OPERAND_DATATYPE_MAP.put(Float.class, OperandDataTypeEnum.DOUBLE);
		OPERAND_DATATYPE_MAP.put(Double.class, OperandDataTypeEnum.DOUBLE);
		OPERAND_DATATYPE_MAP.put(Date.class, OperandDataTypeEnum.DATE);
		OPERAND_DATATYPE_MAP.put(Boolean.class, OperandDataTypeEnum.BOOLEAN);

	}

	public enum OperandDataTypeEnum {
		STRING(new OperandTypeStringRelationalExpressionEvaluator()), LONG(new OperandTypeLongRelationalExpressionEvaluator()), DOUBLE(new OperandTypeDoubleRelationalExpressionEvaluator()),
		DATE(new OperandTypeDateRelationalExpressionEvaluator()), BOOLEAN(new OperandTypeBooleanRelationalExpressionEvaluator());

		private RelationalExpressionEvaluator relationalExpressionEvaluator;

		private OperandDataTypeEnum(RelationalExpressionEvaluator relationalEngine) {
			this.relationalExpressionEvaluator = relationalEngine;
		}

		public RelationalExpressionEvaluator getRelationalExpressionEvaluator() {
			return relationalExpressionEvaluator;
		}

	}

	public static OperandDataTypeEnum getDataTypeEnum(Class className) {
		return OPERAND_DATATYPE_MAP.get(className);
	}
}
