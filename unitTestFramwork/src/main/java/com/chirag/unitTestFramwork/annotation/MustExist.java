package com.chirag.unitTestFramwork.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This Annotation will marked resource as mandatory field.
 * This annotation will help you to generate resource not found scenario.
 * 
 * @author chiragj
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface MustExist {

	/**
	 * httpstatus integer value which you expect as test case result.
	 * default if 404 Not Found
	 * @return
	 */
	int httpStatus() default 404;
}
