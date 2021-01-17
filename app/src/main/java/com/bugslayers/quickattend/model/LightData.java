package com.bugslayers.quickattend.model;

public class LightData {
    private String name;
    private String phone_num;
    private String email;

    public LightData(String name, String phone_num, String email) {
        this.name = name;
        this.phone_num = phone_num;
        this.email = email;
    }

    public LightData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

