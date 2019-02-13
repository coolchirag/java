package com.chirag.unitTestFramwork.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This Annotation will marked request's field as mandatory field.
 * This Annotation will help you for generate mandatory field scenario.
 * @author chiragj
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface NotNull {
	
	int httpStatus() default 400;

}
