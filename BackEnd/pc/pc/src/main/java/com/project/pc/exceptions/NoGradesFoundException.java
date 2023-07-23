package com.project.pc.exceptions;

public class NoGradesFoundException extends RuntimeException{
    public NoGradesFoundException(String meessage){
        super(meessage);
    }
}
