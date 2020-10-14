package ruleEngine.relational;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ruleEngine.exception.InvalidValueException;

public class DateRelationalEngine implements RelationalEngine{
	
	private DateFormat dateFormate = new SimpleDateFormat();

	@Override
	public List<String> performEqualTo(List<String> value1, String value2) {
		List<Date> value1InDate = value1.parallelStream().map(date -> convertStringToDate(date)).collect(Collectors.toList());
		Date value2InDouble =convertStringToDate(value2);
		value1InDate = value1InDate.parallelStream().filter(date -> date.equals(value2InDouble)).collect(Collectors.toList());
		return value1InDate.parallelStream().map(value -> value.toString()).collect(Collectors.toList());
	}

	@Override
	public List<String> performLessThan(List<String> value1, String value2) {
		List<Date> value1InDate = value1.parallelStream().map(date -> convertStringToDate(date)).collect(Collectors.toList());
		Date value2InDouble =convertStringToDate(value2);
		value1InDate = value1InDate.parallelStream().filter(date -> date.before(value2InDouble)).collect(Collectors.toList());
		return value1InDate.parallelStream().map(value -> value.toString()).collect(Collectors.toList());
	}

	@Override
	public List<String> performGreaterThan(List<String> value1, String value2) {
		List<Date> value1InDate = value1.parallelStream().map(date -> convertStringToDate(date)).collect(Collectors.toList());
		Date value2InDouble =convertStringToDate(value2);
		value1InDate = value1InDate.parallelStream().filter(date -> date.after(value2InDouble)).collect(Collectors.toList());
		return value1InDate.parallelStream().map(value -> value.toString()).collect(Collectors.toList());
	}

	@Override
	public List<String> performBetween(List<String> value1, List<String> value2) {
		List<Date> value1InDate = value1.parallelStream().map(date -> convertStringToDate(date)).collect(Collectors.toList());
		Date value2Start =convertStringToDate(value2.get(0));
		Date value2End =convertStringToDate(value2.get(1));
		value1InDate = value1InDate.parallelStream().filter(date -> date.after(value2Start) && date.before(value2End)).collect(Collectors.toList());
		return value1InDate.parallelStream().map(value -> value.toString()).collect(Collectors.toList());
	}

	private Date convertStringToDate(String date) {
		try {
			return dateFormate.parse(date);
		} catch (ParseException e) {
			throw new InvalidValueException("Date formate is invalid : " + date);
			
		}
		
	}
	
	

}
