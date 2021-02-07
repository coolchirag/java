package com.chirag.ruleengine.relational;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.chirag.ruleengine.exception.InvalidOperandException;
import com.chirag.ruleengine.utility.Utility;

public class OperandTypeDateRelationalExpressionEvaluator implements RelationalExpressionEvaluator {
	
	private DateFormat dateFormate = new SimpleDateFormat();

	@Override
	public List<String> performEqualTo(List<String> operand1, String operand2) {
		if(!Utility.isValid(operand2)) {
			return Collections.EMPTY_LIST;
		}
		Date operand2InDouble = convertStringToDate(operand2);
		operand1 = operand1.parallelStream().filter(date ->  Utility.isValid(date) &&convertStringToDate(date).equals(operand2InDouble)).collect(Collectors.toList());
		return operand1;
	}

	@Override
	public List<String> performLessThan(List<String> operand1, String operand2) {
		if(!Utility.isValid(operand2)) {
			return Collections.EMPTY_LIST;
		}
		Date operand2InDouble = convertStringToDate(operand2);
		operand1 = operand1.parallelStream().filter(date ->  Utility.isValid(date) && convertStringToDate(date).before(operand2InDouble)).collect(Collectors.toList());
		return operand1;
	}

	@Override
	public List<String> performGreaterThan(List<String> operand1, String operand2) {
		if(!Utility.isValid(operand2)) {
			return Collections.EMPTY_LIST;
		}
		Date operand2InDouble = convertStringToDate(operand2);
		operand1 = operand1.parallelStream().filter(date ->  Utility.isValid(date) && convertStringToDate(date).after(operand2InDouble)).collect(Collectors.toList());
		return operand1;
	}

	@Override
	public List<String> performBetween(List<String> operand1, List<String> operand2) {
		if(!Utility.isValid(operand2)) {
			return Collections.EMPTY_LIST;
		}
		Date operand2Start = convertStringToDate(operand2.get(0));
		Date operand2End = convertStringToDate(operand2.get(1));
		operand1 = operand1.parallelStream().filter(date ->  Utility.isValid(date) && convertStringToDate(date).after(operand2Start) && convertStringToDate(date).before(operand2End)).collect(Collectors.toList());
		return operand1;
	}

	private Date convertStringToDate(String dateStr) {
		Date date = null;
		if (dateStr != null) {
			try {
				date = dateFormate.parse(dateStr);
			} catch (ParseException e) {
				throw new InvalidOperandException("Date formate is invalid : " + dateStr);

			}
		}
		return date;

	}
	
	

}
