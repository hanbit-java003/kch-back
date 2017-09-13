package com.hanbit.there.api.exception;

public class ExceptionVO {
	
	private String message;
	
	public ExceptionVO() {
		
	}

	public ExceptionVO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	

}
