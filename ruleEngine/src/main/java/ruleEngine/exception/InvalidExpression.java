package ruleEngine.exception;

public class InvalidExpression extends RuntimeException {

	public InvalidExpression() {
		super();
	}

	public InvalidExpression(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidExpression(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidExpression(String message) {
		super(message);
	}

	public InvalidExpression(Throwable cause) {
		super(cause);
	}

}
