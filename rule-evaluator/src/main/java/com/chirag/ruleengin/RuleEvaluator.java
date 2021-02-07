package com.chirag.ruleengin;

import static com.chirag.ruleengin.constant.RuleExpressionConstant.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.chirag.ruleengin.exception.InvalidExpressionException;
import com.chirag.ruleengin.relational.RelationalExpressionEvaluator;
import com.chirag.ruleengin.utility.MultiValueMap;
import com.chirag.ruleengin.utility.OperandDataTypeUtility;
import com.chirag.ruleengin.utility.Utility;

public class RuleEvaluator {

	private String[] rules;
	private Integer index = 0;
	private StringBuilder evaluationLogMsg = new StringBuilder();;

	public RuleEvaluator(String rule) {
		super();
		this.rules = rule.split(" ");
		evaluationLogMsg.append("Rule : " + rule + ", Execution : ");
	}

	public RuleEvaluator(String rule, StringBuilder evaluationLogMsg) {
		this(rule);
		this.evaluationLogMsg = evaluationLogMsg;
		evaluationLogMsg.append("Rule : " + rule + ", Execution : ");
	}

	public boolean execution(Serializable originalData) throws Exception {
		boolean result = false;
		Serializable data = Utility.deepCopy(originalData);
		while (index < rules.length) {
			String rule = rules[index];
			if (isField(rule)) {
				String operand1FieldName = rule.substring(FIELD_INDICATER_LENGTH);
				index++;
				String relationalOperator = rules[index];
				evaluationLogMsg.append(index);
				String operand2 = null;
				if (!isRelationalOperator(relationalOperator)) {
					throw new InvalidExpressionException(
							"Invalid expression at index : " + index + ", expression : " + rules[index]);
				} else if (RelationalExpressionEvaluator.DUAL_OPERAND_OPERATORS.contains(relationalOperator)) {
					index++;
					operand2 = rules[index];
					if (isField(operand2)) {
						/*
						 * Indicate needs to compare between two parameters.
						 */
						if (operand2.startsWith("[")) {
							operand2 = getFieldValue(
									operand2.substring(FIELD_INDICATER_LENGTH + 1, operand2.length() - 1), data);
							if (operand2 != null) {
								operand2 = "[" + operand2 + "]";
							}
						} else {
							operand2 = getFieldValue(operand2.substring(FIELD_INDICATER_LENGTH), data);
						}
					}
				}
				result = executeRelationalOperation(data, operand1FieldName, relationalOperator, operand2);
				index++;
				evaluationLogMsg.append("-" + result + ", ");
			} else if (rule.equals("(")) {
				/*
				 * Indicate child level rule found so copy of original data (because during
				 * child level execution it may change/filter data, but we don't want that
				 * changes at current level execution), and execute that rule separately through
				 * nested call.
				 */
				index++;
				result = execution(data);
			} else if (rule.equals(")")) {
				/*
				 * Indicate child level rule is executed so return result to parent level
				 * executor.
				 */
				index++;
				return result;
			} else if (LOGICAL_OPERTOR_AND.equals(rule)) {
				if (!result) {
					/*
					 * If current result is false then anything after AND is doesn't required to
					 * verify (i.e false AND anything = false). So skip expressions until you found
					 * OR operator or end of rule. But here we need to execute remaining expression
					 * with copy of original data because during execution of previous expression it
					 * may change/filter original data, but now result is false so execute remaining
					 * expression with original data.
					 */
					skipUpToLogicalOperator(LOGICAL_OPERATOR_OR);
					data = Utility.deepCopy(originalData);
				} else {
					/*
					 * If current result is true then we need to check other expressions (i.e true
					 * AND true = true, true AND false = false).
					 */
					index++;
				}
			} else if (LOGICAL_OPERATOR_OR.equals(rule)) {
				if (result) {
					/*
					 * If current result is true then anything after OR is doesn't required to
					 * verify (i.e true OR anything = true). So skip expressions until you found AND
					 * operator or end of rule.
					 */
					skipUpToLogicalOperator(LOGICAL_OPERTOR_AND);
				} else {
					/*
					 * If current result is false then we need to check other expressions (i.e false
					 * OR true = true, false OR false = false). But here we need to execute
					 * remaining expression with copy of original data because during execution of
					 * previous expression it may change/filter original data, but now result is
					 * false so execute remaining expression with original data.
					 */
					data = Utility.deepCopy(originalData);
					index++;
				}
			} else {
				throw new InvalidExpressionException(
						"Invalid expression at index : " + index + ", expression : " + rules[index]);
			}
		}
		evaluationLogMsg.append("final result : " + result);
		return result;
	}

	/*
	 * Skip un-required expression.
	 */
	private void skipUpToLogicalOperator(String logicalOperator) {
		int countOfSubRule = 0;
		while (index < rules.length) {
			String expression = rules[index];
			if (countOfSubRule == 0 && logicalOperator.equals(expression)) {
				/*
				 * consider required logicalOperator when you get it at entry level of this
				 * method. Skip child level expression
				 */
				evaluationLogMsg.append("skipping upto : " + index + ", ");
				index++;
				return;
			} else if ("(".equals(expression)) {
				/*
				 * Indicate we found child level expression.
				 */
				countOfSubRule++;
			} else if (")".equals(expression)) {
				if (countOfSubRule == 0) {
					/*
					 * Indicate entry level expression is over.
					 */
					evaluationLogMsg.append("skipping upto : " + index + ", ");
					return;
				} else {
					/*
					 * Indicate child level expression over.
					 */
					countOfSubRule--;
				}
			}
			index++;
		}
		evaluationLogMsg.append("skipping all remaining, ");
	}

	private static boolean executeRelationalOperation(final Object data, final String fieldName, final String operator,
			final String operand2) throws Exception {
		boolean result = false;
		if (fieldName.contains(".")) {
			/*
			 * Indicate required field is at child level.
			 */
			int spliterIndex = fieldName.indexOf(".");
			Field field = data.getClass().getDeclaredField(fieldName.substring(0, spliterIndex));
			String childParamName = fieldName.substring(spliterIndex + 1);
			field.setAccessible(true);
			Type genericType = field.getGenericType();
			if (genericType instanceof ParameterizedType) {
				/*
				 * Indicate field is List type.
				 */
				
				ParameterizedType pt = ((ParameterizedType) genericType);
				String className = pt.getActualTypeArguments()[0].getTypeName();
				Field childField = Class.forName(className).getDeclaredField(childParamName);
				
				
				
				if (childParamName.contains(".") || childField.getGenericType() instanceof ParameterizedType) {
					/*
					 * Indicate required field is at one more child level.
					 * OR
					 * Child field is List type. i.e cptCode.modifiers
					 */
					MultiValueMap<Integer, Object> filteredDataMap = new MultiValueMap<Integer, Object>();
					Collection c = (Collection) field.get(data);
					if (Utility.isValidCollection(c)) {
						for (Object dataField : c) {
							if (executeRelationalOperation(dataField, childParamName, operator, operand2)) {
								filteredDataMap.add(dataField.hashCode(), dataField);
							}
						}
						c.clear();
						List<Object> filterValues = filteredDataMap.getAllValues();
						if (Utility.isValidCollection(filterValues)) {
							result = true;
							filterValues.forEach(filteredData -> c.add(filteredData));
						}
					} else {
						// For negative and is_blank relations, we mark condition as true if operand is
						// null.
						result = RelationalExpressionEvaluator.TRUE_FOR_NULL_OPERAND_RELATIONAL_OPERATORS.contains(operator);
					}
				} else {
					/*
					 * Indicate required fields is at child level of list. 
					 * i.e cptCodes.code ISDUPLICATE, here we need to filer cptCode based on child object value.
					 * 
					 */
					MultiValueMap<String, Object> originalDataMap = new MultiValueMap<String, Object>();
					Collection c = (Collection) field.get(data);
					String operand = null;
					Class valueClass = (Class) childField.getGenericType();
					if (Utility.isValidCollection(c)) {
						operand="[";
						for (Object childObject : c) {
							childField.setAccessible(true);
							String childValue = String.valueOf(childField.get(childObject));
							operand = operand + childValue + ",";
							originalDataMap.add(childValue, childObject);
						}
						operand = operand.substring(0, operand.length() - 1);
						operand = operand + "]";
						c.clear();
						
					}
					if(!Utility.isValid(operand)) {
						// For negative and is_blank relations, we mark condition as true if operand is
						// null.
						result = RelationalExpressionEvaluator.TRUE_FOR_NULL_OPERAND_RELATIONAL_OPERATORS.contains(operator);
					} else {
						List<String> resultValues = OperandDataTypeUtility.getDataTypeEnum(valueClass)
								.getRelationalExpressionEvaluator().evaluate(operator, operand, operand2);
						if(Utility.isValidCollection(resultValues)) {
							resultValues.forEach(resultValue -> c.addAll(originalDataMap.getValues(resultValue)));
							result = true;
						}
					}
				}

				/*
				 * Comment out below code for code optimization and handle scenario of
				 * code.modifier equalto 50.
				 */
				/*
				 * if (childParamName.contains(".")) {
				 * 
				 * Indicate param is at one more level child. So execute nested call for each
				 * child element and filter it out.
				 * 
				 * MultiValueMap<Integer, Object> filteredDataMap = new MultiValueMap<Integer,
				 * Object>(); Collection c = (Collection) field.get(data); if
				 * (Utility.isValidCollection(c)) { for (Object dataField : c) { if
				 * (executeRelationalOperation(dataField, childParamName, operator, operand2)) {
				 * filteredDataMap.add(dataField.hashCode(), dataField); } } c.clear(); }
				 * List<Object> filterValues = filteredDataMap.getAllValues(); if
				 * (Utility.isValidCollection(filterValues)) { result = true;
				 * filterValues.forEach(filteredData -> c.add(filteredData)); } else if
				 * (!Utility.isValid(operand) &&
				 * RelationalExpressionEvaluator.NEGATIVE_RELATIONAL_OPERATORS.contains(operator
				 * )) { // For negative relation we mark condition as true if operand is null.
				 * result = true; }
				 * 
				 * } else {
				 * 
				 * Filter child elements as per relational operations.
				 * 
				 * MultiValueMap<String, Object> map = new MultiValueMap<String, Object>();
				 * Collection c = (Collection) field.get(data); Class valueClass = String.class;
				 * if (Utility.isValidCollection(c)) { operand = "["; for (Object childObject :
				 * c) { Field childField =
				 * childObject.getClass().getDeclaredField(childParamName);
				 * childField.setAccessible(true); valueClass = (Class)
				 * childField.getGenericType(); String childValue =
				 * String.valueOf(childField.get(childObject)); operand = operand + childValue +
				 * ","; map.add(childValue, childObject); } operand = operand.substring(0,
				 * operand.length() - 1); operand = operand + "]"; c.clear(); } if
				 * (!Utility.isValid(operand)) {
				 * 
				 * If operand is not valid then no need to evaluate expression. If operator is
				 * negative_relational_operators then result is true else result is false;
				 * 
				 * result =
				 * RelationalExpressionEvaluator.NEGATIVE_RELATIONAL_OPERATORS.contains(operator
				 * ); } else { List<String> resultValues =
				 * OperandDataTypeUtility.getDataTypeEnum(valueClass)
				 * .getRelationalExpressionEvaluator().evaluate(operator, operand, operand2); if
				 * (resultValues != null && resultValues.size() > 0) {
				 * resultValues.forEach(resultValue -> c.addAll(map.getValues(resultValue)));
				 * result = true; } } }
				 */

			} else {
				/*
				 * Indicate required field is object type.
				 */
				result = executeRelationalOperation(field.get(data), childParamName, operator, operand2);
			}

		} else {
			/*
			 * Indicate required field is at entry level.
			 */
			Field field = data.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			Type genericType = field.getGenericType();
			String operand = null;
			Class valueClass = String.class;
			if (genericType instanceof ParameterizedType) {

				/*
				 * Indicate field is List Type.
				 */
				Collection c = (Collection) field.get(data);
				MultiValueMap<String, Object> originalDataMap = new MultiValueMap<String, Object>();
				if (Utility.isValidCollection(c)) {
					operand = "[";
					for (Object dataValue : c) {
						valueClass = dataValue.getClass();
						String valueStr = String.valueOf(dataValue);
						operand = operand + valueStr + ",";
						originalDataMap.add(valueStr, dataValue);
					}
					operand = operand.substring(0, operand.length() - 1);
					operand = operand + "]";
					c.clear();
				}
				
				if (!Utility.isValid(operand)) {
					/*
					 * If operand is not valid then no need to evaluate expression. If operator is
					 * negative_relational_operators or is_blank operator then result is true else
					 * result is false;
					 */
					result = RelationalExpressionEvaluator.TRUE_FOR_NULL_OPERAND_RELATIONAL_OPERATORS.contains(operator);
				} else {
					List<String> resultValues = OperandDataTypeUtility.getDataTypeEnum(valueClass)
							.getRelationalExpressionEvaluator().evaluate(operator, operand, operand2);
					if (Utility.isValidCollection(resultValues)) {
						result = true;
						resultValues.forEach(resultValue -> c.addAll(originalDataMap.getValues(resultValue)));
					}
				}

			} else {
				/*
				 * Indicate field is Object Type.
				 */
				valueClass = (Class) genericType;
				operand = String.valueOf(field.get(data));
				if (!Utility.isValid(operand)) {
					/*
					 * If operand is not valid then no need to evaluate expression. If operator is
					 * negative_relational_operators or is_blank operator then result is true else
					 * result is false;
					 */
					result = RelationalExpressionEvaluator.TRUE_FOR_NULL_OPERAND_RELATIONAL_OPERATORS.contains(operator);
				} else {
					List<String> resultValues = OperandDataTypeUtility.getDataTypeEnum(valueClass)
							.getRelationalExpressionEvaluator().evaluate(operator, operand, operand2);
					if (Utility.isValidCollection(resultValues)) {
						result = true;
					}
				}
			}
			

		}
		return result;
	}

	private static boolean isField(String expression) {
		return expression.startsWith(FIELD_INDICATOR) || expression.startsWith("[" + FIELD_INDICATOR);
	}

	private static boolean isRelationalOperator(String expression) {
		return RelationalExpressionEvaluator.SINGLE_OPERAND_OPERATORS.contains(expression)
				|| RelationalExpressionEvaluator.DUAL_OPERAND_OPERATORS.contains(expression);
	}

	private static String getFieldValue(String param, Object obj) throws Exception {
		String value = null;
		if (param.contains(".")) {
			/*
			 * Indicate param is at child level
			 */
			int spliterIndex = param.indexOf(".");
			Field field = obj.getClass().getDeclaredField(param.substring(0, spliterIndex));
			field.setAccessible(true);
			Type genericType = field.getGenericType();
			if (genericType instanceof ParameterizedType) {
				/*
				 * Indicate child field is list so concat each child value.
				 */
				Collection c = (Collection) field.get(obj);
				if (Utility.isValidCollection(c)) {
					value = "";
					for (Object data : c) {
						value = value + getFieldValue(param.substring(spliterIndex + 1), data) + ",";
					}
					value = value.substring(0, value.length() - 1);
				}
			} else {
				/*
				 * Indicate child field is an object so directly get value.
				 */
				value = getFieldValue(param.substring(spliterIndex + 1), field.get(obj));
			}

		} else {
			/*
			 * Indicate param is at entry level.
			 */
			Field field = obj.getClass().getDeclaredField(param);
			field.setAccessible(true);
			Type genericType = field.getGenericType();
			if (genericType instanceof ParameterizedType) {

				Collection c = (Collection) field.get(obj);
				if (Utility.isValidCollection(c)) {
					value = "";
					for (Object data : c) {
						value = value + data + ",";
					}
					value = value.substring(0, value.length() - 1);
				}
			} else {
				value = String.valueOf(field.get(obj));
			}
		}
		return value;
	}

}
