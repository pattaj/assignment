package com.jtp.atm.exception;

public class AccountNotExistExceptionResponse {
	
	private String error;
	
	public AccountNotExistExceptionResponse() {
		
	}
	
	public AccountNotExistExceptionResponse(String error) {
		this.error = error;
		
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
