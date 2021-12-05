package com.jtp.atm.exception;

public class InvalidAccountAndPinExceptionResponse {
	
	private String error;
	
	public InvalidAccountAndPinExceptionResponse() {
		
	}
	
	public InvalidAccountAndPinExceptionResponse(String error) {
		this.error = error;
		
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
	
	

}
