package com.jtp.atm.exception;


public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message){
        super(message);
    }
}
