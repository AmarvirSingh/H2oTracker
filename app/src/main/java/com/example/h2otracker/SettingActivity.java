package com.example.h2otracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {
//RecyclerView dataList;


    CardView card1,card2,card3,card4,card5;

    Switch dark_switch;
List<String> titles;
List<Integer> images;

Adapter adapter;


    @Override
    protected void onStart() {
        super.onStart();

        final boolean isDarkModeOn = getSharedPreferences("sharedPrefs",MODE_PRIVATE).getBoolean("isDarkModeOn",false);


        if(isDarkModeOn)
        {
            dark_switch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        else
        {
            dark_switch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
      //  card1 = findViewById(R.id.card1);
       // dataList = findViewById(R.id.datalist);

        //dark mode settings
        dark_switch = findViewById(R.id.switch3);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);

        final SharedPreferences.Editor editor =sharedPreferences.edit();

        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn",false);


        if(isDarkModeOn)
        {
            dark_switch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        else
        {
            dark_switch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }





        dark_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {

                if (isDarkModeOn)
                {
                   /* card1.setCardBackgroundColor(Color.BLACK);*/


                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                  //  getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                    editor.putBoolean("isDarkModeOn",false);
                    editor.apply();

                }

                else
                {

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

 //                   getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    editor.putBoolean("isDarkModeOn",true);
                    editor.apply();
                }

            }
        });



        titles = new ArrayList<>();
        images = new ArrayList<>();


        titles.add("Dark Mode");
        titles.add("Sound");
        titles.add("Feedback");
        titles.add("Reminder");
        titles.add("Share By");

        images.add(R.drawable.ic_baseline_brightness_3_24);
        images.add(R.drawable.ic_baseline_audiotrack_24);
        images.add(R.drawable.ic_baseline_favorite_24);
        images.add(R.drawable.notification);
        images.add(R.drawable.share);



      /*  adapter = new Adapter(this,titles,images);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
        dataList.setLayoutManager(gridLayoutManager);
       // Log.d("SettingActivity", "onCreate: "+ titles.size());
        dataList.setAdapter(adapter);*/
    }
}