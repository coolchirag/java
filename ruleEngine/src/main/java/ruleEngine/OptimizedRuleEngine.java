package ruleEngine;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ruleEngine.DataType.DataTypeEnum;
import ruleEngine.domain.encounter.EncounterDomainParser;
import ruleEngine.exception.InvalidExpressionException;
import ruleEngine.relational.RelationalEngine;
import ruleEngine.utility.MultiValueMap;
import ruleEngine.utility.Utility;

public class OptimizedRuleEngine {

	private static final String PARAM_INDICATOR = "param_";
	private static final Integer PARAM_INDICATER_LENGTH = PARAM_INDICATOR.length();

	private static final String AND = "AND";
	private static final String OR = "OR";

	private String[] rules;
	private Integer index = 0;

	public OptimizedRuleEngine(String[] rules) {
		super();
		this.rules = rules;
	}

	public boolean execution(Object data) throws Exception {
		boolean result = false;
		Object copiedData = Utility.deepCopy(data);
		while (index < rules.length) {
			// result = false;
			String rule = rules[index];
			if (isParam(rule)) {
				String value1Param = rule.substring(PARAM_INDICATER_LENGTH);
				index++;
				String relationalOperator = rules[index];
				String value2 = null;
				if (!isRelationalOperator(relationalOperator)) {
					throw new InvalidExpressionException(
							"Invalid expression at index : " + index + ", expression : " + rule);
				} else if (RelationalEngine.dualParamOperations.contains(relationalOperator)) {
					index++;
					value2 = rules[index];
					if (isParam(value2)) {
						if (value2.startsWith("[")) {
							value2 = getParamValue(value2.substring(PARAM_INDICATER_LENGTH + 1, value2.length() - 1),
									data);
							value2 = "[" + value2 + "]";
						} else {
							value2 = getParamValue(value2.substring(PARAM_INDICATER_LENGTH), data);
						}

					}
					result = performRelationalOperationOnData(data, value1Param, relationalOperator, value2);
				}
				index++;
			} else if (rule.equals("(")) {
				index++;
				result = execution(Utility.deepCopy(copiedData));
			} else if (rule.equals(")")) {
				index++;
				return result;
			} else if (AND.equals(rule)) {
				if (!result) {
					skipUpToLogicalOperator(OR);
				} else {
					index++;
				}
			} else if (OR.equals(rule)) {
				if (result) {
					skipUpToLogicalOperator(AND);
				} else {
					data = Utility.deepCopy(copiedData);
					index++;
				}
			} else {
				throw new InvalidExpressionException(
						"Invalid expression at index : " + index + ", expression : " + rule);
			}
		}
		return result;
	}

	private void skipUpToLogicalOperator(String logicalOperator) {
		while (index < rules.length) {
			if (logicalOperator.equals(rules[index])) {
				index++;
				return;
			}
			index++;
		}
	}

	public static boolean performRelationalOperationOnData(Object data, String param, String operator, String value2)
			throws Exception {
		boolean result = false;
		if (param.contains(".")) {
			int spliterIndex = param.indexOf(".");
			Field field = data.getClass().getDeclaredField(param.substring(0, spliterIndex));
			String childParamName = param.substring(spliterIndex + 1);
			field.setAccessible(true);
			Type genericType = field.getGenericType();
			String value = null;
			if (genericType instanceof ParameterizedType) {
				if (childParamName.contains(".")) {
					MultiValueMap<Integer, Object> filteredDataMap = new MultiValueMap<Integer, Object>();
					Collection c = (Collection) field.get(data);
					if (c != null && c.size() > 0) {
						for (Object dataField : c) {
							if (performRelationalOperationOnData(dataField, childParamName, operator, value2)) {
								filteredDataMap.add(dataField.hashCode(), dataField);
							}
						}
						c.clear();
					}
					List<Object> filterValues = filteredDataMap.getAllValues();
					if (filterValues != null && filterValues.size() > 0) {
						result = true;
						filterValues.forEach(filteredData -> c.add(filteredData));
					}

				} else {
					MultiValueMap<String, Object> map = new MultiValueMap<String, Object>();
					Collection c = (Collection) field.get(data);
					Class valueClass = String.class;
					if (c != null && c.size() > 0) {
						value = "[";
						for (Object childObject : c) {
							Field childField = childObject.getClass().getDeclaredField(childParamName);
							childField.setAccessible(true);
							valueClass = (Class) childField.getGenericType();
							String childValue = String.valueOf(childField.get(childObject));
							value = value + childValue + ",";
							map.add(childValue, childObject);
						}
						value = value.substring(0, value.length() - 1);
						value = value + "]";
						c.clear();
					}

					List<String> resultValues = DataType.getDataTypeEnum(valueClass).getRelationalEngine()
							.executeOperation(operator, value, value2);
					if (resultValues != null && resultValues.size() > 0) {
						resultValues.forEach(resultValue -> c.addAll(map.getValues(resultValue)));
						result = true;
					}
				}

			} else {
				result = performRelationalOperationOnData(field.get(data), childParamName, operator, value2);
			}

		} else {
			Field field = data.getClass().getDeclaredField(param);
			field.setAccessible(true);
			Type genericType = field.getGenericType();
			String value = null;
			Class valueClass = String.class;
			if (genericType instanceof ParameterizedType) {

				Collection c = (Collection) field.get(data);
				if (c != null && c.size() > 0) {
					value = "[";
					for (Object dataValue : c) {
						valueClass = dataValue.getClass();
						value = value + dataValue + ",";
					}
					value = value.substring(0, value.length() - 1);
					value = value + "]";
				}

			} else {
				valueClass = (Class) genericType;
				value = (String) field.get(data);
			}
			List<String> resultValues = DataType.getDataTypeEnum(valueClass).getRelationalEngine()
					.executeOperation(operator, value, value2);
			result = resultValues != null && resultValues.size() > 0;
		}
		return result;
	}

	private static boolean isParam(String expression) {
		return expression.startsWith(PARAM_INDICATOR) || expression.startsWith("[" + PARAM_INDICATOR);
	}

	private static boolean isRelationalOperator(String expression) {
		return RelationalEngine.singleParamOperations.contains(expression)
				|| RelationalEngine.dualParamOperations.contains(expression);
	}

	private static boolean isLogicalOperator(String expression) {
		return AND.equals(expression) || OR.equals(expression);
	}

	private static String getParamValue(String param, Object obj) throws Exception {
		String value = "";
		if (param.contains(".")) {
			int spliterIndex = param.indexOf(".");
			Field field = obj.getClass().getDeclaredField(param.substring(0, spliterIndex));
			field.setAccessible(true);
			Type genericType = field.getGenericType();
			if (genericType instanceof ParameterizedType) {

				Collection c = (Collection) field.get(obj);
				if (c != null && c.size() > 0) {
					for (Object data : c) {
						value = value + getParamValue(param.substring(spliterIndex + 1), data) + ",";
					}
					value = value.substring(0, value.length() - 1);
				}
			} else {
				value = getParamValue(param.substring(spliterIndex + 1), field.get(obj));
			}

		} else {
			Field field = obj.getClass().getDeclaredField(param);
			field.setAccessible(true);
			Type genericType = field.getGenericType();
			if (genericType instanceof ParameterizedType) {

				Collection c = (Collection) field.get(obj);
				if (c != null && c.size() > 0) {
					for (Object data : c) {
						value = value + data + ",";
					}
					value = value.substring(0, value.length() - 1);
				}
			} else {
				value = (String) field.get(obj);
			}
		}
		return value;
	}

}
