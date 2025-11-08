package com.sousaarthur.blog.exception;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException(String message) {
        super(message);
    }

    public EventNotFoundException() {
        super("Evento não encontrado.");
    }
}
