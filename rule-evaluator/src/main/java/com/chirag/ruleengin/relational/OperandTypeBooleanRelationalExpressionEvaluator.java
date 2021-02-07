package com.chirag.ruleengin.relational;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.chirag.ruleengin.exception.InvalidOperatorException;
import com.chirag.ruleengin.utility.Utility;

public class OperandTypeBooleanRelationalExpressionEvaluator implements RelationalExpressionEvaluator {

	@Override
	public List<String> performEqualTo(List<String> operand1, String operand2) {
		if(!Utility.isValid(operand2)) {
			return Collections.EMPTY_LIST;
		}
		Boolean operand2InBoolean = Boolean.parseBoolean(operand2);
		return operand1.parallelStream().filter(value -> Utility.isValid(value) && Boolean.parseBoolean(value) == operand2InBoolean).collect(Collectors.toList());
	}

	@Override
	public List<String> performLessThan(List<String> operand1, String operand2) {
		throw new InvalidOperatorException(LESS_THAN + " is not allowed in boolean data.");
	}

	@Override
	public List<String> performGreaterThan(List<String> operand1, String operand2) {
		throw new InvalidOperatorException(GREATER_THAN + " is not allowed in boolean data.");
	}

	@Override
	public List<String> performBetween(List<String> operand1, List<String> operand2) {
		throw new InvalidOperatorException(BETWEEN + " is not allowed in boolean data.");
	}

	@Override
	public List<String> performIn(List<String> operand1, List<String> operand2) {
		throw new InvalidOperatorException(IN + " is not allowed in boolean data.");
	}

	@Override
	public List<String> performNotIn(List<String> operand1, List<String> operand2) {
		throw new InvalidOperatorException(NOT_IN + " is not allowed in boolean data.");
	}

	@Override
	public List<String> performIsDuplicate(List<String> operand1) {
		throw new InvalidOperatorException(IS_DUPLICATE + " is not allowed in boolean data.");
	}

}
