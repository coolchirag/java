package com.chirag.ruleengine.relational;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.chirag.ruleengine.exception.InvalidOperandException;
import com.chirag.ruleengine.exception.InvalidOperatorException;
import com.chirag.ruleengine.utility.Utility;

public interface RelationalExpressionEvaluator {

	String EQUAL_TO = "EQUALTO";
	String NOT_EQUAL_TO = "NOTEQUALTO";
	String IN = "IN";
	String NOT_IN = "NOTIN";
	String IS_BLANK = "ISBLANK";
	String IS_NOT_BLANK = "ISNOTBLANK";
	String LESS_THAN = "LESSTHAN";
	String GREATER_THAN = "GREATER_THAN";
	String IS_DUPLICATE = "ISDUPLICATE";
	String BETWEEN = "BETWEEN";
	String CONTAINS ="CONTAINS";
	String NOT_CONTAINS ="NOTCONTAINS";
	Set<String> DUAL_OPERAND_OPERATORS = new HashSet<String>(
			Arrays.asList(EQUAL_TO, NOT_EQUAL_TO, IN, NOT_IN, LESS_THAN, GREATER_THAN, BETWEEN, CONTAINS, NOT_CONTAINS));
	Set<String> SINGLE_OPERAND_OPERATORS = new HashSet<String>(Arrays.asList(IS_BLANK, IS_DUPLICATE, IS_NOT_BLANK));
	Set<String> TRUE_FOR_NULL_OPERAND_RELATIONAL_OPERATORS = new HashSet<String>(Arrays.asList(NOT_EQUAL_TO, NOT_IN, IS_BLANK, NOT_CONTAINS));

	default List<String> evaluate(String operator, String operand1, String operand2) {
		List<String> result = new ArrayList<String>();

		final List<String> operand1List = new ArrayList<String>();
		if(operand1 != null) {
			if (operand1.startsWith("[")) {
				operand1List.addAll(Arrays.asList(operand1.substring(1, operand1.length() - 1).split(",")));
			} else {
				operand1List.add(operand1);
			}
		}
		
		List<String> operand2List = new ArrayList<String>();
		if(operand2 == null) {
			operand2 = "";
		} else if(operand2.startsWith("[")) {
			operand2List.addAll(Arrays.asList(operand2.substring(1, operand2.length() - 1).split(",")));
		}
		
		switch (operator) {
		case EQUAL_TO:
			result = performEqualTo(operand1List, operand2);
			break;
		case NOT_EQUAL_TO:
			result = performNotEqualTo(operand1List, operand2);
			break;
		case IN:
			if (operand2List.isEmpty()) {
				throw new InvalidOperandException(
						"Invalid value during : " + operator + ", expected array but found single value : " + operand2);
			}
			result = performIn(operand1List, operand2List);
			break;
		case NOT_IN:
			if (operand2List.isEmpty()) {
				throw new InvalidOperandException(
						"Invalid value during : " + operator + ", expected array but found single : " + operand2);
			}
			result = performNotIn(operand1List, operand2List);
			break;
		case CONTAINS:
			if (operand2List.isEmpty()) {
				throw new InvalidOperandException(
						"Invalid value during : " + operator + ", expected array but found single : " + operand2);
			}
			result = performContains(operand1List, operand2List);
			break;
		case NOT_CONTAINS:
			if (operand2List.isEmpty()) {
				throw new InvalidOperandException(
						"Invalid value during : " + operator + ", expected array but found single : " + operand2);
			}
			result = performNotContains(operand1List, operand2List);
			break;
		case IS_BLANK:
			result = performIsBlank(operand1List);
			break;
		case IS_NOT_BLANK:
			result = performIsNotBlank(operand1List);
			break;
		case LESS_THAN:
			result = performLessThan(operand1List, operand2);
			break;
		case GREATER_THAN:
			if (operand2List.size()>0) {
				throw new InvalidOperandException(
						"Invalid value during : " + operator + ", expected single value but found array : " + operand2);
			}
			result = performGreaterThan(operand1List, operand2);
			break;
		case IS_DUPLICATE:
			result = performIsDuplicate(operand1List);
			break;
		case BETWEEN:
			if (operand2List.size() != 2) {
				throw new InvalidOperandException(
						"Invalid value during : " + operator + ", expected array of size 2 but found value : " + operand2);
			}
			result = performBetween(operand1List, operand2List);
			break;
		default:
			throw new InvalidOperatorException("Invalid operator : " + operator);
		}
		
		return result;
	}

	public List<String> performEqualTo(List<String> operand1, String operand2);

	public default List<String> performNotEqualTo(List<String> operand1, String operand2) {
		List<String> equalList = performEqualTo(operand1, operand2);
		operand1.removeAll(equalList);
		return operand1;
	}

	public default List<String> performIn(List<String> operand1, List<String> operand2) {
		List<String> result = new ArrayList<String>();
		operand2.forEach(value -> {
			result.addAll(performEqualTo(operand1, value));
		});
		return result;
	}

	public default List<String> performNotIn(List<String> operand1, List<String> operand2) {
		List<String> equalList = performIn(operand1, operand2);
		operand1.removeAll(equalList);
		return operand1;
	}
	
	public default List<String> performContains(List<String> operand1, List<String> operand2) {
		List<String> equalList = performIn(operand1, operand2);
		if(equalList.size()==0) {
			operand1.clear();
		}
		return operand1;
	}
	
	public default List<String> performNotContains(List<String> operand1, List<String> operand2) {
		List<String> equalList = performIn(operand1, operand2);
		if(equalList.size()>0) {
			operand1.clear();
		}
		return operand1;
	}

	public default List<String> performIsBlank(List<String> values1) {
		return values1.stream().filter(value -> !Utility.isValid(value))
				.collect(Collectors.toList());
	}
	
	public default List<String> performIsNotBlank(List<String> values1) {
		return values1.stream().filter(Utility::isValid).collect(Collectors.toList());
	}

	public List<String> performLessThan(List<String> operand1, String operand2);

	public List<String> performGreaterThan(List<String> operand1, String operand2);

	public default List<String> performIsDuplicate(List<String> operand1) {
		List<String> duplicateList = new ArrayList<String>();
		for (String value : operand1) {
			List<String> equalList = performEqualTo(operand1, value);
			if (equalList != null && equalList.size() > 1) {
				duplicateList.add(value);
			}
		}
		return duplicateList;
	}

	public List<String> performBetween(List<String> operand1, List<String> operand2);
}
