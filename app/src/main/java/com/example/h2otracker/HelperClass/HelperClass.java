package com.example.h2otracker.HelperClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HelperClass extends SQLiteOpenHelper {

    private static final String NOTE_TABLE = "userTable" ;
    private Context context;


    public HelperClass(@Nullable Context context) {
        super(context, "UserDatabase", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE IF NOT EXISTS historyTable ( " +
                " id INTEGER NOT NULL CONSTRAINT pk_history PRIMARY KEY AUTOINCREMENT," +
                " amount TEXT NOT NULL, " +
                " total TEXT NOT NULL, " +
                " time TEXT NOT NULL, " +
                " image BLOB NOT NULL" +
                ");";

        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  historyTable ;");
    }

    // function to add data to table
    public long addRecord(String amount, String total, String time, byte[] imageInByte) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("amount",amount);
        cv.put("total",total);
        cv.put("time",time);
        cv.put("image",imageInByte);

        long result = db.insert("historyTable",null,cv);

        return result;
    }


}
