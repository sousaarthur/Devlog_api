package com.sousaarthur.blog.modules.auth.model;

public enum UserRole {
    ADMIN("admin"),
    WRITER("writer"),
    READER("reader");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
