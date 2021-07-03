package com.example.restaurant.Objects;

import java.io.Serializable;

public class TableFood implements Serializable {
    private int id;
    private int tableId;
    private int isAvailable;
    private String phoneNum;
    private String guestName;
    private int foodId;
    private String name;
    private int cost;
    private String unit;
    private int amount;

    private static final int ID = 0;
    private static final int TABLEID = 0;
    private static final int ISAVAILABLE = 0;
    private static final String PHONENUM = "0";
    private static final String GUESTNAME = "0";
    private static final int FOODID = 0;
    private static final String NAME = "0";
    private static final int COST = 0;
    private static final String UNIT = "0";
    private static final int AMOUNT = 0;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
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

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public TableFood(int id, int tableId, int isAvailable, String phoneNum, String guestName, int foodId, String name, int cost, String unit, int amount) {
        this.id = id;
        this.tableId = tableId;
        this.phoneNum = phoneNum;
        this.guestName = guestName;
        this.foodId = foodId;
        this.name = name;
        this.cost = cost;
        this.unit = unit;
        this.amount = amount;
    }

    public TableFood() {
        this.id = ID;
        this.tableId = TABLEID;
        this.isAvailable = ISAVAILABLE;
        this.phoneNum = PHONENUM;
        this.guestName = GUESTNAME;
        this.foodId = FOODID;
        this.name = NAME;
        this.cost = COST;
        this.unit = UNIT;
        this.amount = AMOUNT;
    }
}
