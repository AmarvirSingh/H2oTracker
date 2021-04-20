package com.example.h2otracker;

public class Category {
    float soda;
    float water;
    float coffee;
    String date;

    public Category(float soda, float water, float coffee, String date) {
        this.soda = soda;
        this.water = water;
        this.coffee = coffee;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getSoda() {
        return soda;
    }

    public void setSoda(float soda) {
        this.soda = soda;
    }

    public float getWater() {
        return water;
    }

    public void setWater(float water) {
        this.water = water;
    }

    public float getCoffee() {
        return coffee;
    }

    public void setCoffee(float coffee) {
        this.coffee = coffee;
    }
}
