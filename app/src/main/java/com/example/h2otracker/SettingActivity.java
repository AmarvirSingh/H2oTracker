package com.example.h2otracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;



public class SettingActivity extends AppCompatActivity {
//RecyclerView dataList;

    public static String toneName = "";

    CardView card1,card2,card3,card4,card5;

    Switch dark_switch,Reminder;
List<String> titles;
List<Integer> images;

TextView feedback ,shareBy,notificationTone;

View view;

     boolean isDarkModeOn;


    @Override
    protected void onStart() {
        super.onStart();

        isDarkModeOn = getSharedPreferences("sharedPrefs",MODE_PRIVATE).getBoolean("isDarkModeOn",false);


        if(isDarkModeOn)
        {
            view.setBackgroundResource(R.drawable.night_blue);

            dark_switch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        else
        {
            view.setBackgroundResource(R.drawable.day_blue);
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

        view = findViewById(R.id.view);




        // notification tones
        notificationTone = findViewById(R.id.notificationTone);
        notificationTone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog dialog = new Dialog(SettingActivity.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.activity_notification_tone);

                Button tone1 = dialog.findViewById(R.id.tone1);
                Button tone2 = dialog.findViewById(R.id.tone2);



                tone1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SettingActivity.this, "" + tone1.getText(), Toast.LENGTH_SHORT).show();
                        //   addWater.setText("100");
                        toneName = "tone1";
                        dialog.dismiss();
                    }
                });


                tone2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SettingActivity.this, "" + tone2.getText(), Toast.LENGTH_SHORT).show();
                        //   addWater.setText("100");
                        toneName = "tone2";
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

        });



        // reminder

        Reminder = findViewById(R.id.ReminderSwitch);
        Reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Much longer text that cannot fit one line...";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(SettingActivity.this)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle("My notification")
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Much longer text that cannot fit one line..."))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

                Intent intent = new Intent(SettingActivity.this,MainContent.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("message",message);

                PendingIntent pendingIntent = PendingIntent.getActivity(SettingActivity.this,
                        0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);


                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0,builder.build());

            }
        });




        //share by
        shareBy = findViewById(R.id.shareBy);
        shareBy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "i found this application which is very use full \n link here : www.google.com";

                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });


        //feedback
        feedback = findViewById(R.id.feedback);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent. ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"FeedbACKH2O@gmail.com"});

                emailIntent.setType("message/rfc822");

                startActivity(Intent.createChooser(emailIntent,"Choose an Email client :"));

            }
        });
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