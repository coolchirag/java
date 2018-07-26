package com.chirag.customejpa.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(ChildReferenceInfos.class)
public @interface ChildReferenceInfo {

	String refPropertyName();
	//Class childClass();
	String columnName();
	 
}
