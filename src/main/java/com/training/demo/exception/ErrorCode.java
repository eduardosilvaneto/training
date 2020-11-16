package com.training.demo.exception;

public enum ErrorCode {
	  
	  DEMO_SUCCESS("0", "Sucesso!"), 
	  DEMO_ERROR_0001("0001", "Error Generic."),
	  DEMO_ERROR_0002("0002", "Error Busisnes #?");
	
	  private String code;
	  private String message;

	  private ErrorCode(String code, String message) {
	    this.code = code;
	    this.message = message;
	  }
	  public String getMessage() {
	    return message;
	  }
	  public String getCode() {
	    return code;
	  }
	  public int getCodeInt() {
	    return Integer.valueOf(code);
	  }
	  public ErrorCode format(String[] words) {
	    for(String s : words)
	      this.message = this.message.replace("#?", s);
	    
	    return this;
	  }
	  public ErrorCode format(Object... args) {
	    this.message = String.format(this.message, args);
	    return this;
	  }
	  
	  public ErrorCode append(String str) {
	    this.message = this.message + " Detalhes: " + str;
	    return this;
	  }
}
