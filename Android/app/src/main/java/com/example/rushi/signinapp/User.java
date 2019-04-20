package com.example.rushi.signinapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String fullname;
    public String phonenumber;
    public String username;
    public String password;

    public User(){}

    public User(String fullname, String phonenumber, String username, String password)
    {
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.username = username;
        this.password = password;
    }

    public String getFullname() {return fullname;}
    public String getUsername() {
        return username;
    }
    public String getPassword(){
        return password;
    }
}
