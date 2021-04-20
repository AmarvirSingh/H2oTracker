package com.example.h2otracker;

public class Category {
    int soda;
    int water;
    int coffee;
    String date;

    public Category(int soda, int water, int coffee, String date) {
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

    public void setSoda(int soda) {
        this.soda = soda;
    }

    public float getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public float getCoffee() {
        return coffee;
    }

    public void setCoffee(int coffee) {
        this.coffee = coffee;
    }
}
