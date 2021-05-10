package com.example.safood.Model;

public class User {

    private String phone;
    private String password;
    private String choice;


    public User() {
    }

    public User(String phone, String password, String choice) {
        this.phone = phone;
        this.password = password;
        this.choice = choice;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
