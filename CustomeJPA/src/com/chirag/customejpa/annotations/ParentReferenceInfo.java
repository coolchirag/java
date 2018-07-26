package com.chirag.customejpa.annotations;

public @interface ParentReferenceInfo {

	Class parentClass();
	String columnName();
}
