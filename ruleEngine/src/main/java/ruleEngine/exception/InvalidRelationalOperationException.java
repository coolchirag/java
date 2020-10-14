package ruleEngine.exception;

public class InvalidRelationalOperationException extends RuntimeException {

	public InvalidRelationalOperationException() {
		super();
	}

	public InvalidRelationalOperationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidRelationalOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidRelationalOperationException(String message) {
		super(message);
	}

	public InvalidRelationalOperationException(Throwable cause) {
		super(cause);
	}

}
