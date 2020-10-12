package ruleEngine.domain;

public interface DomainParser {

	String[] parse(String[] ruleExpression) throws Exception;
}
