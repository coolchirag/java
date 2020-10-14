package ruleEngine.relational;

import java.util.List;
import java.util.stream.Collectors;

public class DoubleRelationalEngine implements RelationalEngine {

	@Override
	public List<String> performEqualTo(List<String> value1, String value2) {
		List<Double> value1InDouble = value1.parallelStream().map(Double::parseDouble).collect(Collectors.toList());
		Double value2InDouble = Double.parseDouble(value2);
		value1InDouble = value1InDouble.parallelStream().filter(value -> value.doubleValue() == value2InDouble).collect(Collectors.toList());
		return value1InDouble.parallelStream().map(value -> value.toString()).collect(Collectors.toList());
	}

	@Override
	public List<String> performLessThan(List<String> value1, String value2) {
		List<Double> value1InDouble = value1.parallelStream().map(Double::parseDouble).collect(Collectors.toList());
		Double value2InDouble = Double.parseDouble(value2);
		value1InDouble = value1InDouble.parallelStream().filter(value -> value.doubleValue() < value2InDouble).collect(Collectors.toList());
		return value1InDouble.parallelStream().map(value -> value.toString()).collect(Collectors.toList());
	}

	@Override
	public List<String> performGreaterThan(List<String> value1, String value2) {
		List<Double> value1InDouble = value1.parallelStream().map(Double::parseDouble).collect(Collectors.toList());
		Double value2InDouble = Double.parseDouble(value2);
		value1InDouble = value1InDouble.parallelStream().filter(value -> value.doubleValue() > value2InDouble).collect(Collectors.toList());
		return value1InDouble.parallelStream().map(value -> value.toString()).collect(Collectors.toList());
	}

	@Override
	public List<String> performBetween(List<String> value1, List<String> value2) {
		List<Double> value1InDouble = value1.parallelStream().map(Double::parseDouble).collect(Collectors.toList());
		Double value2Start = Double.parseDouble(value2.get(0));
		Double value2End = Double.parseDouble(value2.get(1));
		value1InDouble = value1InDouble.parallelStream().filter(value -> value.doubleValue() > value2Start && value.doubleValue() < value2End).collect(Collectors.toList());
		return value1InDouble.parallelStream().map(value -> value.toString()).collect(Collectors.toList());
	}
	

}
