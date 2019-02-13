package com.chirag.unitTestFramwork.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This Annotation will help you for test longer text data scenario.
 * @author chiragj
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface ValidTextSize {

	/**
	 * Max allow length of text field.
	 * @return
	 */
	int max();
	
	int httpStatus() default 400;
}
