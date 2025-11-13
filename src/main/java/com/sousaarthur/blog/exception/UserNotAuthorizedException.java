package com.sousaarthur.blog.exception;

public class UserNotAuthorizedException extends  RuntimeException{
    public UserNotAuthorizedException(String message){
        super(message);
    }

    public UserNotAuthorizedException(){
        super("Você não possui permissão para relaizar essa ação");
    }
}
