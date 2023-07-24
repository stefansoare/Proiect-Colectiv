package com.project.pc.exceptions;

public class IncompleteTaskException extends RuntimeException{
    public IncompleteTaskException(String message){
        super(message);
    }
}
