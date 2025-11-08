package com.sousaarthur.blog.exception;

public class EventSizeException extends RuntimeException{
    public EventSizeException(String message) {
        super(message);
    }

    public EventSizeException(){
        super("Tamanho do evento inválido.");
    }
}
