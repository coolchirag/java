package com.chirag.eventdrivenframework.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface EventHandler {

	String[] eventNames();
	int order() default Integer.MAX_VALUE;
}
