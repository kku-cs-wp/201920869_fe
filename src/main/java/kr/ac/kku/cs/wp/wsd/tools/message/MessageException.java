package kr.ac.kku.cs.wp.wsd.tools.message;

/**
 * MessageException
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
public class MessageException extends RuntimeException{

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MessageException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public MessageException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public MessageException(Throwable cause) {
		super(cause);
	}
	
}