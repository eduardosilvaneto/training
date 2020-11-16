package com.training.demo.exception;

public class BusinessException extends GenericException {
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 5208343703349465997L;
	private int code = 0;
	  private String message;
	  private ErrorCode errorCode;

	  public BusinessException(ErrorCode errorCode, Throwable cause) {
	    super(errorCode, cause);
	    this.errorCode = errorCode;
	    this.message = errorCode.getMessage();
	  }

	  public BusinessException(ErrorCode errorCode) {
	    super(errorCode);
	    this.errorCode = errorCode;
	  }

	  public BusinessException(String message, Throwable cause) {
	    super(message, cause);
	  }

	  public BusinessException(int code, String message) {
	    super(code, message);
	    this.message = message;
	    this.code = code;
	  }

	  public BusinessException(int code, String message, Throwable cause) {
	    super(code, message, cause);
	    this.message = message;
	    this.code = code;
	  }

	  public int getCode() {
	    return code;
	  }

	  @Override
	  public String getMessage() {
	    return this.message;
	  }

	  public ErrorCode getErrorCode() {
	    return errorCode;
	  }
}
