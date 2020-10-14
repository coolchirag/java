package ruleEngine.exception;

public class InvalidExpressionException extends RuntimeException {

	public InvalidExpressionException() {
		super();
	}

	public InvalidExpressionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidExpressionException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidExpressionException(String message) {
		super(message);
	}

	public InvalidExpressionException(Throwable cause) {
		super(cause);
	}

}
