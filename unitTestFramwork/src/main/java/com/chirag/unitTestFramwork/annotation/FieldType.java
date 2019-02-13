package com.chirag.unitTestFramwork.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation used for indicate filed type.
 * Currently Not in used.
 * @author chiragj
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface FieldType {

	enum Type {
		REQUEST,
		RESOURCE,
		PERMISION
	};
	
	Type value();
}
