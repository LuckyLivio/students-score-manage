package com.grade.entity;

public enum UserRole {
    ADMIN("管理员"),
    TEACHER("教师"),
    STUDENT("学生");
    
    private final String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}