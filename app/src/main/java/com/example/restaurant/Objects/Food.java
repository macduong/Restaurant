package com.example.restaurant.Objects;

import java.io.Serializable;

public class Food implements Serializable {
    private int id;
    private String name;
    private int cost;
    private String unit;

    private static final int ID = 0;
    private static final String NAME = "0";
    private static final int COST = 0;
    private static final String UNIT = "0";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Food() {
        this.id = ID;
        this.name = NAME;
        this.cost = COST;
        this.unit = UNIT;
    }

    public Food( String name, int cost, String unit) {
        this.id = ID;
        this.name = name;
        this.cost = cost;
        this.unit = unit;
    }
    public Food(int id, String name, int cost, String unit) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.unit = unit;
    }
}
