package com.chellange.exception;

public class NotFoundException extends RuntimeException{
	public NotFoundException(String message){
        super(message);
    }
}