package com.jtp.atm.exception;


public class InsufficientNoteException extends Exception {
    public InsufficientNoteException(String message){
        super(message);
    }
}
