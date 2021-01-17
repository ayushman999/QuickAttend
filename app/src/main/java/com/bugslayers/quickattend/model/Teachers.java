package com.bugslayers.quickattend.model;

public class Teachers {
    private String name;
    private String email;

    public Teachers() {
    }

    public Teachers(String email,String name) {
        this.name = name;
        this.email= email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}