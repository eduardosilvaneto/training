package com.training.demo.exception;

public class GenericException extends Exception {  
	 /**
	 * 
	 */
	private static final long serialVersionUID = -3338649896230550910L;
	
	private int code = 0;
	  private String message;
	  private ErrorCode errorCode;

	  public GenericException(ErrorCode errorCode) {
	    super(errorCode.getMessage());
	    this.errorCode = errorCode;
	    this.message = errorCode.getMessage();
	  }

	  public GenericException(ErrorCode errorCode, Throwable cause) {
	    super(errorCode.getMessage(), cause);
	    this.errorCode = errorCode;
	  }

	  public GenericException(String message, Throwable cause) {
	    super(message, cause);
	  }

	  public GenericException(int code, String message, Throwable cause) {
	    super(message, cause);
	    this.message = message;
	    this.code = code;
	  }

	  public GenericException(int code, String message) {
	    super(message);
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
