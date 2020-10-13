package ruleEngine;

import ruleEngine.domain.encounter.EncounterDomainParser;
import ruleEngine.exception.InvalidExpression;
import ruleEngine.relational.RelationalEngine;

public class OptimizedRuleEngine {

	private static final String PARAM_INDICATOR = "param_";
	private static final Integer PARAM_INDICATER_LENGTH = PARAM_INDICATOR.length();
	
	private static final String AND = "AND";
	private static final String OR = "OR";
	
	private String[] rules;
	private Integer index = 0;
	
	
	public OptimizedRuleEngine(String[] rules) {
		super();
		this.rules = rules;
	}

	public boolean execution(Object data) throws Exception {
		boolean result = false;
		while (index<rules.length) {
			 result = false;
			String rule = rules[index];
			if(isParam(rule)) {
				String value1 = EncounterDomainParser.getParamValue(rule.substring(PARAM_INDICATER_LENGTH+1), data);
				index++;
				String relationalOperator = rules[index];
				if(!isRelationalOperator(relationalOperator)) {
					throw new InvalidExpression("Invalid expression at index : "+index+", expression : "+rule);
				} else if(RelationalEngine.singleParamOperations.contains(relationalOperator)) {
					//result = TODO perform relationalOperation.
				} else {
					index++;
					String value2 = rules[index];
					if(isParam(value2)) {
						value2 = EncounterDomainParser.getParamValue(value2.substring(PARAM_INDICATER_LENGTH+1), data);;
					}
					//result = TODO perform relationalOperation.
				}
				index ++;
			} else if (rule.equals("(")) {
				//Do deepCopy pass to nested call.
				index++;
				execution(data);
			} else if(rule.equals(")")) {
				index++;
				return result;
			} else if(AND.equals(rule)) {
				if(!result) {
					skipUpToLogicalOperator(OR);
				} else {
					index ++;
				}
			} else if(OR.equals(rule)) {
				if(result) {
					skipUpToLogicalOperator(AND);
				} else {
					index ++;
				}
			} else {
				throw new InvalidExpression("Invalid expression at index : "+index+", expression : "+rule);
			}
		} 
		return result;
	}
	
	private void skipUpToLogicalOperator(String logicalOperator) {
		while (index<rules.length) {
			if(logicalOperator.equals(rules[index])) {
				index++;
				return;
			}
			index++;
		}
	}
	
	private static boolean isParam(String expression) {
		return expression.startsWith(PARAM_INDICATOR);
	}
	
	private static boolean isRelationalOperator(String expression) {
		return RelationalEngine.singleParamOperations.contains(expression) || RelationalEngine.dualParamOperations.contains(expression);
	}
	
	private static boolean isLogicalOperator(String expression) {
		return AND.equals(expression) || OR.equals(expression);
	}
}
