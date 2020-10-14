package ruleEngine.relational;

import java.util.List;
import java.util.stream.Collectors;

import ruleEngine.exception.InvalidRelationalOperationException;

public class StringRelationalEngine implements RelationalEngine {

	@Override
	public List<String> performEqualTo(List<String> value1, String value2) {
		return value1.parallelStream().filter(value -> value.equalsIgnoreCase(value2)).collect(Collectors.toList());
	}

	@Override
	public List<String> performLessThan(List<String> value1, String value2) {
		throw new InvalidRelationalOperationException(LESS_THAN + " is not allowed in string data.");
	}

	@Override
	public List<String> performGreaterThan(List<String> value1, String value2) {
		throw new InvalidRelationalOperationException(GREATER_THAN + " is not allowed in string data.");
	}

	@Override
	public List<String> performBetween(List<String> value1, List<String> value2) {
		throw new InvalidRelationalOperationException(BETWEEN + " is not allowed in string data.");
	}

	

}
