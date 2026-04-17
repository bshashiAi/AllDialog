package com.bshashi.anydialog.exceptions;

public class DialogException extends Exception {
    public DialogException(String message) {
        super(message);
    }
    
    public DialogException(String message, Throwable cause) {
        super(message, cause);
    }
}