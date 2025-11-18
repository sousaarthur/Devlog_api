package com.sousaarthur.blog.exception;

public class IllegalCategoryCreateException extends RuntimeException {
    public IllegalCategoryCreateException(String message) {
        super(message);
    }
    public IllegalCategoryCreateException() {
        super("Categoria já existente");
    }
}
