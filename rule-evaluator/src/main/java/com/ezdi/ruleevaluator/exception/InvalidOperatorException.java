package com.ezdi.ruleevaluator.exception;

public class InvalidOperatorException extends RuntimeException {

	public InvalidOperatorException() {
		super();
	}

	public InvalidOperatorException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidOperatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidOperatorException(String message) {
		super(message);
	}

	public InvalidOperatorException(Throwable cause) {
		super(cause);
	}

}
