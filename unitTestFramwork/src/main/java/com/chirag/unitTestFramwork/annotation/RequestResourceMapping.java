package com.chirag.unitTestFramwork.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This Annotation will contain mapping of request's field and resource'field.
 * @author chiragj
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
@Repeatable(NoDuplicate.class)
public @interface RequestResourceMapping {

	/**
	 * Request's field path with respect to AbstractTestBean object.
	 * Example request.field1
	 * @return
	 */
	String requestField();
	
	/**
	 * Resource's field path with respect to resource filed object.
	 * Example field1
	 * @return
	 */
	String resourceField();
}
