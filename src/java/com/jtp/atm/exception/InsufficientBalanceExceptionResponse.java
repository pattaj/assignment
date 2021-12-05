package com.jtp.atm.exception;

public class InsufficientBalanceExceptionResponse {
	
	private String error;
	
	public InsufficientBalanceExceptionResponse() {
		
	}
	
	public InsufficientBalanceExceptionResponse(String error) {
		this.error = error;
		
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
