package com.example.h2otracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {
//RecyclerView dataList;


    private Switch dark_switch;
List<String> titles;
List<Integer> images;

Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
       // dataList = findViewById(R.id.datalist);


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