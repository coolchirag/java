package com.chirag.ruleengin.exception;

public class InvalidOperandException extends RuntimeException {

	public InvalidOperandException() {
		super();
		
	}

	public InvalidOperandException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidOperandException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidOperandException(String message) {
		super(message);
	}

	public InvalidOperandException(Throwable cause) {
		super(cause);
	}

}
