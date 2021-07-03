package com.example.restaurant.Objects;

import android.widget.SearchView;

import java.io.Serializable;

public class Manager implements Serializable {
    private int id;
    private String fullName, userName, password;

    private static int ID = 0;
    private static String FULLNAME = "0";
    private static String USERNAME = "0";
    private static String PASSWORD = "0";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Manager() {
        this.id = ID;
        this.fullName = FULLNAME;
        this.userName = USERNAME;
        this.password = PASSWORD;
    }

    public Manager(String fullName, String userName, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
    }

    public Manager(int id, String fullName, String userName, String password) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
    }
}
