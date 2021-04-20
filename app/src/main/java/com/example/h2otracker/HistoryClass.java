package com.example.h2otracker;

import android.database.sqlite.SQLiteDatabase;

public class HistoryClass {
    private String Type;
    private String Time;
    private String Amount;

    public HistoryClass(String type, String time, String amount) {
        Type = type;
        Time = time;
        Amount = amount;
    }

    public HistoryClass(String amount) {
        Amount = amount;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
