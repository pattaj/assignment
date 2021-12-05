package com.jtp.atm.exception;

public class InvalidAccountAndPinException extends Exception {
	
	private String message;
	
	public InvalidAccountAndPinException(String message){
        this.message = message;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
