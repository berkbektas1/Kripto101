package com.example.kripto101.Models;

public class User {
    public String fullName, email, registrationDate, levelAccount;

    public User(){

    }

    public User(String fullName, String email, String registrationDate, String levelAccount) {
        this.fullName = fullName;
        this.email = email;
        this.registrationDate = registrationDate;
        this.levelAccount = levelAccount;
    }


}
