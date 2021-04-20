package com.example.h2otracker.HelperClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.h2otracker.HistoryClass;

import java.util.ArrayList;

public class HelperClass extends SQLiteOpenHelper {

    private static final String NOTE_TABLE = "userTable";
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
                " type TEXT NOT NULL, " +
                " time TEXT NOT NULL " +
                " );";

        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  historyTable ;");
    }

    // function to add data to table
    public long addRecord(String amount, String type, String time) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("amount", amount);
        cv.put("type", type);
        cv.put("time", time);


        long result = db.insert("historyTable", null, cv);

        return result;
    }

    public ArrayList<HistoryClass> getHistory() {

        try {
            SQLiteDatabase database = getReadableDatabase();
            Cursor cursor = null;
            cursor = database.rawQuery("SELECT * FROM historyTable", null);

            ArrayList<HistoryClass> arrayList = new ArrayList<>();
            if (cursor.getCount() != 0) {

                while (cursor.moveToNext()) {
                    String time = cursor.getString(3);
                    String type = cursor.getString(2);
                    String amount = cursor.getString(1);

                    HistoryClass modelClass = new HistoryClass(type, time, amount);

                    arrayList.add(modelClass);
                }
                cursor.close();
                return arrayList;

            } else {
                return null;


            }

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return  null;
    }

    public ArrayList<Integer> totalIntakeByUser(){

            SQLiteDatabase database = getReadableDatabase();

            Cursor cursor = null;
            cursor = database.rawQuery("SELECT * FROM historyTable", null);

            ArrayList<Integer> arrayList = new ArrayList<>();
            if (cursor.getCount() != 0) {

                while (cursor.moveToNext()) {
                    String amount = cursor.getString(1);

//                    HistoryClass modelClass = new HistoryClass(amount);

                    arrayList.add(Integer.parseInt(amount));
                }
                cursor.close();
                return arrayList;

            } else {
                return null;
            }
    }

}
