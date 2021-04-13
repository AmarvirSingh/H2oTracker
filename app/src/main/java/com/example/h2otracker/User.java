package com.example.h2otracker;

public class User {
    private String fullName, Email, age, height, weight, gender, wakeupTime, sleepTime;

    public User() {

    }

    public User(String fullName, String email, String age, String height, String weight, String gender, String wakeupTime, String sleepTime) {
        this.fullName = fullName;
        Email = email;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.wakeupTime = wakeupTime;
        this.sleepTime = sleepTime;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWakeupTime() {
        return wakeupTime;
    }

    public void setWakeupTime(String wakeupTime) {
        this.wakeupTime = wakeupTime;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }
}
