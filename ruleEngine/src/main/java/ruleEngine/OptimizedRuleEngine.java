package ruleEngine;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

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
		Object originalData = Utility.deepCopy(data);
		while (index < rules.length) {
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
					result = executeRelationalOperation(data, value1Param, relationalOperator, value2);
				}
				index++;
			} else if (rule.equals("(")) {
				/*
				 * Indicate child level rule found so copy of original data (because during child level execution it may change/filter data, but we don't want that changes at current level execution), and execute that rule separately through nested call.
				 */
				index++;
				result = execution(Utility.deepCopy(originalData));
			} else if (rule.equals(")")) {
				/*
				 * Indicate child level rule is executed so return result to parent level executor.
				 */
				index++;
				return result;
			} else if (AND.equals(rule)) {
				if (!result) {
					/*
					 * If current result is false then anything after AND is doesn't required to verify (i.e false AND anything = false). So skip expressions until you found OR operator or end of rule.
					 * But here we need to execute remaining expression with copy of original data because during execution of previous expression it may change/filter original data, but now result is false so execute remaining expression with original data.
					 */
					skipUpToLogicalOperator(OR);
					data = Utility.deepCopy(originalData);
				} else {
					/*
					 * If current result is true then we need to check other expressions (i.e true AND true = true, true AND false = false).
					 */
					index++;
				}
			} else if (OR.equals(rule)) {
				if (result) {
					/*
					 * If current result is true then anything after OR is doesn't required to verify (i.e true OR anything = true). So skip expressions until you found AND operator or end of rule.
					 */
					skipUpToLogicalOperator(AND);
				} else {
					/*
					 * If current result is false then we need to check other expressions (i.e false OR true = true, false OR false = false). 
					 * But here we need to execute remaining expression with copy of original data because during execution of previous expression it may change/filter original data, but now result is false so execute remaining expression with original data.  
					 */
					data = Utility.deepCopy(originalData);
					index++;
				}
			} else {
				throw new InvalidExpressionException(
						"Invalid expression at index : " + index + ", expression : " + rule);
			}
		}
		return result;
	}

	/*
	 * Skip un required expression.
	 */
	private void skipUpToLogicalOperator(String logicalOperator) {
		int countOfSubRule = 0;
		while (index < rules.length) {
			String expression = rules[index];
			if (countOfSubRule==0 && logicalOperator.equals(expression)) {
				/*
				 * consider required logicalOperator when you get it at entry level of this method. Skip child level expression
				 */
				index++;
				return;
			} else if("(".equals(expression)) {
				/*
				 * Indicate we found child level expression.
				 */
				countOfSubRule++;
			} else if(")".equals(expression)) {
				if(countOfSubRule==0) {
					/*
					 * Indicate entry level expression is over.
					 */
					return;
				} else {
					/*
					 * Indicate child level expression over.
					 */
					countOfSubRule++;
				}
			}
			index++;
		}
	}

	public static boolean executeRelationalOperation(Object data, String param, String operator, String value2)
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
							if (executeRelationalOperation(dataField, childParamName, operator, value2)) {
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
				result = executeRelationalOperation(field.get(data), childParamName, operator, value2);
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
