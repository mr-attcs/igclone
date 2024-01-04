package com.mrattcs.instagramapi.exceptions;

public class UserException extends Exception{

    public UserException(String message) {
        super("User Exception happened:" + message);
    }

}
