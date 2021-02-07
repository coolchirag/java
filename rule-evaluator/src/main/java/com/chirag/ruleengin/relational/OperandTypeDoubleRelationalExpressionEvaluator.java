package com.chirag.ruleengin.relational;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.chirag.ruleengin.utility.Utility;

public class OperandTypeDoubleRelationalExpressionEvaluator implements RelationalExpressionEvaluator {

	@Override
	public List<String> performEqualTo(List<String> operand1, String operand2) {
		if(operand2==null) {
			return Collections.EMPTY_LIST;
		}
		double operand2InDouble = Double.parseDouble(operand2);
		operand1 = operand1.parallelStream().filter(value ->  Utility.isValid(value) && Double.parseDouble(value) == operand2InDouble).collect(Collectors.toList());
		return operand1;
	}

	@Override
	public List<String> performLessThan(List<String> operand1, String operand2) {
		if(operand2==null) {
			return Collections.EMPTY_LIST;
		}
		Double operand2InDouble = Double.parseDouble(operand2);
		operand1 = operand1.parallelStream().filter(value ->  Utility.isValid(value) && Double.parseDouble(value) < operand2InDouble).collect(Collectors.toList());
		return operand1;
	}

	@Override
	public List<String> performGreaterThan(List<String> operand1, String operand2) {
		if(operand2==null) {
			return Collections.EMPTY_LIST;
		}
		Double operand2InDouble = Double.parseDouble(operand2);
		operand1 = operand1.parallelStream().filter(value ->  Utility.isValid(value) && Double.parseDouble(value) > operand2InDouble).collect(Collectors.toList());
		return operand1;
	}

	@Override
	public List<String> performBetween(List<String> operand1, List<String> operand2) {
		if(!Utility.isValid(operand2)) {
			return Collections.EMPTY_LIST;
		}
		Double operand2Start = Double.parseDouble(operand2.get(0));
		Double operand2End = Double.parseDouble(operand2.get(1));
		operand1 = operand1.parallelStream().filter(value ->  Utility.isValid(value) && Double.parseDouble(value) > operand2Start && Double.parseDouble(value) < operand2End).collect(Collectors.toList());
		return operand1;
	}
	

}
