package com.example.yummyfood;

public class HelperClass {
    String name, email, user, password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HelperClass(String name, String email, String user, String password) {
        this.name = name;
        this.email = email;
        this.user = user;
        this.password = password;
    }

    public HelperClass(){

    }
}
