package com.chirag.unitTestFramwork.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * This Annotation will help you in test invalid text input.
 * @author chiragj
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface ValidTextData {

	/**
	 * Set invalid text.
	 * @return
	 */
	String invalidText();
	
	int httpStatus() default 400;
}
