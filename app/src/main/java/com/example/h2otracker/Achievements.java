package com.example.h2otracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.h2otracker.HelperClass.HelperClass;

import java.util.ArrayList;

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
        img6000 = findViewById(R.id.imageView6000);
        img8000 = findViewById(R.id.imageView8000);
        img10000 = findViewById(R.id.imageView10000);
        img12000 = findViewById(R.id.imageView12000);
        img15000 = findViewById(R.id.imageView15000);
        img20000 = findViewById(R.id.imageView20000);
        img25000 = findViewById(R.id.imageView25000);
        img30000 = findViewById(R.id.imageView30000);
        helperClass = new HelperClass(this);
        calculateWaterDrinked();

        if (totalAmount >= 6000 && totalAmount < 8000) {
            img6000.setVisibility(View.VISIBLE);
        } else if (totalAmount >= 8000 && totalAmount < 10000) {
            img6000.setVisibility(View.VISIBLE);
            img8000.setVisibility(View.VISIBLE);


        } else if (totalAmount >= 10000 && totalAmount < 12000) {
            img6000.setVisibility(View.VISIBLE);
            img8000.setVisibility(View.VISIBLE);
            img10000.setVisibility(View.VISIBLE);


        } else if (totalAmount >= 12000 && totalAmount < 15000) {
            img6000.setVisibility(View.VISIBLE);
            img8000.setVisibility(View.VISIBLE);
            img10000.setVisibility(View.VISIBLE);
            img12000.setVisibility(View.VISIBLE);

        } else if (totalAmount >= 15000 && totalAmount < 20000) {
            img6000.setVisibility(View.VISIBLE);
            img8000.setVisibility(View.VISIBLE);
            img10000.setVisibility(View.VISIBLE);
            img12000.setVisibility(View.VISIBLE);
            img15000.setVisibility(View.VISIBLE);
        } else if (totalAmount >= 20000 && totalAmount < 25000) {
            img6000.setVisibility(View.VISIBLE);
            img8000.setVisibility(View.VISIBLE);
            img10000.setVisibility(View.VISIBLE);
            img12000.setVisibility(View.VISIBLE);
            img15000.setVisibility(View.VISIBLE);
            img20000.setVisibility(View.VISIBLE);
        } else if (totalAmount >= 25000 && totalAmount < 30000) {
            img6000.setVisibility(View.VISIBLE);
            img8000.setVisibility(View.VISIBLE);
            img10000.setVisibility(View.VISIBLE);
            img12000.setVisibility(View.VISIBLE);
            img15000.setVisibility(View.VISIBLE);
            img20000.setVisibility(View.VISIBLE);
            img25000.setVisibility(View.VISIBLE);
        } else if (totalAmount >= 30000) {
            img6000.setVisibility(View.VISIBLE);
            img8000.setVisibility(View.VISIBLE);
            img10000.setVisibility(View.VISIBLE);
            img12000.setVisibility(View.VISIBLE);
            img15000.setVisibility(View.VISIBLE);
            img20000.setVisibility(View.VISIBLE);
            img25000.setVisibility(View.VISIBLE);
            img30000.setVisibility(View.VISIBLE);
        }
    }
        int totalAmount = 0;
        private void calculateWaterDrinked(){
            ArrayList<Integer> intakedWater = helperClass.totalIntakeByUser();
            int a = 0;
            for (int i= 0; i < intakedWater.size(); i++){
                a += intakedWater.get(i);

            }
            totalAmount = a;
        }
    }

