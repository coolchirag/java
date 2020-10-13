package ruleEngine;

import java.util.ArrayList;
import java.util.List;

import ruleEngine.DataType.DataTypeEnum;
import ruleEngine.relational.RelationalEngine;

public class RelationalOperationExecutor {
	
	public static String[] execute(String[] ruleExpression) {
		List<String> result = new ArrayList<String>();
		Integer size = ruleExpression.length;
		Integer index=0;
		do {
			if(ruleExpression[index].startsWith("dataType_")) {
				String[] array = ruleExpression[index].split("_");
				index++;
				String operation = ruleExpression[index];
				index++;
				String value1 = ruleExpression[index];
				String value2 = null;
				if(!RelationalEngine.singleParamOperations.contains(operation)) {
					index++;
					value2 = ruleExpression[index];
				}
				DataTypeEnum.valueOf(array[1]).getRelationalEngine().executeOperation(operation, value1, value2);
			} else {
				index++;
			}
		} while(index<size);
		return (String[]) result.toArray();
	}
}
