package com.example.restaurant.Objects;

import java.io.Serializable;

public class Table implements Serializable {
    private int id;
    private int isAvailable;
    private String phoneNum;
    private String guestName;

    private static final int ID = 0;
    private static final int ISAVAILABLE = 0;
    private static final String PHONENUM = "0";
    private static final String GUESTNAME = "0";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Table() {
        this.id = ID;
        this.isAvailable = ISAVAILABLE;
        this.phoneNum = PHONENUM;
        this.guestName = GUESTNAME;
    }

    public Table( int isAvailable) {
        this.id = ID;
        this.isAvailable = isAvailable;
        this.phoneNum = PHONENUM;
        this.guestName = GUESTNAME;
    }
    public Table(int id, int isAvailable) {
        this.id = id;
        this.isAvailable = isAvailable;
        this.phoneNum = PHONENUM;
        this.guestName = GUESTNAME;
    }

    public Table(int id, int isAvailable, String phoneNum, String guestName) {
        this.id = id;
        this.isAvailable = isAvailable;
        this.phoneNum = phoneNum;
        this.guestName = guestName;
    }
}
