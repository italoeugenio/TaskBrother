package com.italo.TaskBrother.models.enums;

public enum UserRole {
    PARENTS("admin"),
    CHILDREN("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
