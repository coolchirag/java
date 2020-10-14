package ruleEngine.relational;

import java.util.List;
import java.util.stream.Collectors;

public class LongRelationalEngine implements RelationalEngine {

	@Override
	public List<String> performEqualTo(List<String> value1, String value2) {
		List<Long> value1InLong = value1.parallelStream().map(Long::parseLong).collect(Collectors.toList());
		long value2InLong = Long.parseLong(value2);
		value1InLong = value1InLong.parallelStream().filter(value -> value.longValue() == value2InLong).collect(Collectors.toList());
		return value1InLong.parallelStream().map(value -> value.toString()).collect(Collectors.toList());
	}

	@Override
	public List<String> performLessThan(List<String> value1, String value2) {
		List<Long> value1InLong = value1.parallelStream().map(Long::parseLong).collect(Collectors.toList());
		long value2InLong = Long.parseLong(value2);
		value1InLong = value1InLong.parallelStream().filter(value -> value.longValue() < value2InLong).collect(Collectors.toList());
		return value1InLong.parallelStream().map(value -> value.toString()).collect(Collectors.toList());
	}

	@Override
	public List<String> performGreaterThan(List<String> value1, String value2) {
		List<Long> value1InLong = value1.parallelStream().map(Long::parseLong).collect(Collectors.toList());
		long value2InLong = Long.parseLong(value2);
		value1InLong = value1InLong.parallelStream().filter(value -> value.longValue() > value2InLong).collect(Collectors.toList());
		return value1InLong.parallelStream().map(value -> value.toString()).collect(Collectors.toList());
	}

	@Override
	public List<String> performBetween(List<String> value1, List<String> value2) {
		List<Long> value1InLong = value1.parallelStream().map(Long::parseLong).collect(Collectors.toList());
		long value2Start = Long.parseLong(value2.get(0));
		long value2End = Long.parseLong(value2.get(1));
		value1InLong = value1InLong.parallelStream().filter(value -> value.longValue() > value2Start && value.longValue() < value2End).collect(Collectors.toList());
		return value1InLong.parallelStream().map(value -> value.toString()).collect(Collectors.toList());
	}

	
	

}
