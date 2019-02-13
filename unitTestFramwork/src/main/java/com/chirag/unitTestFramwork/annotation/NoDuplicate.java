package com.chirag.unitTestFramwork.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This Annotation will marked resource as non-duplicate.
 * This Annotation will help you for generate duplicate resource scenario.
 * @author chiragj
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface NoDuplicate {

	/**
	 * Mapping between request's fields and resource's fields.
	 * @return
	 */
	RequestResourceMapping[] value();
	
	int httpStatus() default 409;
}
