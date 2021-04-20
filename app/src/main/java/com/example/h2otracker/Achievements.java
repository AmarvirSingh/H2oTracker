package com.example.h2otracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.h2otracker.HelperClass.HelperClass;

public class Achievements extends AppCompatActivity {
    SharedPreferences sharedPreferencesWaterIntake;
    HelperClass helperClass;
    ImageView img6000;
    ImageView img8000;
    ImageView img10000;
    ImageView img12000;
    ImageView img15000;
    ImageView img20000;
    ImageView img25000;
    ImageView img30000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        img6000 =findViewById(R.id.imageView6000);
        img8000 = findViewById(R.id.imageView8000);
        img10000 = findViewById(R.id.imageView10000);
        img12000 = findViewById(R.id.imageView12000);
        img15000 = findViewById(R.id.imageView15000);
        img20000 = findViewById(R.id.imageView20000);
        img25000 = findViewById(R.id.imageView25000);
        img30000 = findViewById(R.id.imageView30000);
        helperClass = new HelperClass(this);
        calculateWaterDrinked();
    }
}