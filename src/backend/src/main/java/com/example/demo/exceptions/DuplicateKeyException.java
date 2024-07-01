package com.example.demo.exceptions;

public class DuplicateKeyException  extends Exception{
    public DuplicateKeyException(String message){
        super(message);
    }
}

