package com.ezdi.ruleevaluator.relational;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.ezdi.ruleevaluator.exception.InvalidOperatorException;
import com.ezdi.ruleevaluator.utility.Utility;

public class OperandTypeStringRelationalExpressionEvaluator implements RelationalExpressionEvaluator {

	@Override
	public List<String> performEqualTo(List<String> operand1, String operand2) {
		if(!Utility.isValid(operand2)) {
			return Collections.EMPTY_LIST;
		}
		return operand1.parallelStream().filter(value -> value.equalsIgnoreCase(operand2)).collect(Collectors.toList());
	}

	@Override
	public List<String> performLessThan(List<String> operand1, String operand2) {
		throw new InvalidOperatorException(LESS_THAN + " is not allowed in string data.");
	}

	@Override
	public List<String> performGreaterThan(List<String> operand1, String operand2) {
		throw new InvalidOperatorException(GREATER_THAN + " is not allowed in string data.");
	}

	@Override
	public List<String> performBetween(List<String> operand1, List<String> operand2) {
		throw new InvalidOperatorException(BETWEEN + " is not allowed in string data.");
	}

	

}
