package com.ezdi.ruleevaluator.relational;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.ezdi.ruleevaluator.utility.Utility;

public class OperandTypeLongRelationalExpressionEvaluator implements RelationalExpressionEvaluator {

	@Override
	public List<String> performEqualTo(List<String> operand1, String operand2) {
		if(!Utility.isValid(operand2)) {
			return Collections.EMPTY_LIST;
		}
		long operand2InLong = Long.parseLong(operand2);
		operand1 = operand1.parallelStream().filter(value ->  Utility.isValid(value) && Long.parseLong(value) == operand2InLong).collect(Collectors.toList());
		return operand1;
	}

	@Override
	public List<String> performLessThan(List<String> operand1, String operand2) {
		if(!Utility.isValid(operand2)) {
			return Collections.EMPTY_LIST;
		}
		long operand2InLong = Long.parseLong(operand2);
		operand1 = operand1.parallelStream().filter(value ->  Utility.isValid(value) && Long.parseLong(value) < operand2InLong).collect(Collectors.toList());
		return operand1;
	}

	@Override
	public List<String> performGreaterThan(List<String> operand1, String operand2) {
		if(!Utility.isValid(operand2)) {
			return Collections.EMPTY_LIST;
		}
		long operand2InLong = Long.parseLong(operand2);
		operand1 = operand1.parallelStream().filter(value ->  Utility.isValid(value) && Long.parseLong(value) > operand2InLong).collect(Collectors.toList());
		return operand1;
	}

	@Override
	public List<String> performBetween(List<String> operand1, List<String> operand2) {
		if(!Utility.isValid(operand2)) {
			return Collections.EMPTY_LIST;
		}
		long operand2Start = Long.parseLong(operand2.get(0));
		long operand2End = Long.parseLong(operand2.get(1));
		operand1 = operand1.parallelStream().filter(value ->  Utility.isValid(value) && Long.parseLong(value) > operand2Start && Long.parseLong(value) < operand2End).collect(Collectors.toList());
		return operand1;
	}

	
	

}
