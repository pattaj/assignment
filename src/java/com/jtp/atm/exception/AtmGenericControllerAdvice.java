package com.jtp.atm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AtmGenericControllerAdvice {
	
	
	@ExceptionHandler(InvalidAccountAndPinException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public InvalidAccountAndPinExceptionResponse invalidAcctAndPinNo(InvalidAccountAndPinException ex) {
		
		InvalidAccountAndPinExceptionResponse response = new InvalidAccountAndPinExceptionResponse(ex.getMessage());
		return response;
	}
	
	
	@ExceptionHandler(AccountNotExistException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public AccountNotExistExceptionResponse accountNull(AccountNotExistException ex) {
		
		AccountNotExistExceptionResponse response = new AccountNotExistExceptionResponse(ex.getMessage());
		return response;

	}	

	
	@ExceptionHandler(InsufficientBalanceException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public InsufficientBalanceExceptionResponse insufficientAcctBal(InsufficientBalanceException ex) {
		
		InsufficientBalanceExceptionResponse response = new InsufficientBalanceExceptionResponse(ex.getMessage());
		return response;

	}	
	
}
