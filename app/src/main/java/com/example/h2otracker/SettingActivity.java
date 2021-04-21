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
    public static String toneName1 = "";

    CardView card1, card2, card3, card4, card5;

    Switch dark_switch, Reminder;
    List<String> titles;
    List<Integer> images;
    boolean ReminderIsChecked = false;

    TextView feedback, shareBy, notificationTone;

    View view;
    //taking shared preference for setting notifiaction tone
    SharedPreferences sharedPreferences;

    boolean isDarkModeOn;


    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);


        isDarkModeOn = getSharedPreferences("sharedPrefs", MODE_PRIVATE).getBoolean("isDarkModeOn", false);



        if (isDarkModeOn) {
            view.setBackgroundResource(R.drawable.night_blue);

            dark_switch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            view.setVisibility(View.GONE);
        } else {
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
// reminder

        Reminder = findViewById(R.id.ReminderSwitch);
        if (ReminderIsChecked ){
            Reminder.setChecked(true);
        }
        else{
            Reminder.setChecked(false);
        }



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
                        sharedPreferences.edit().putString("tone", "tone1").apply();
                        dialog.dismiss();
                    }
                });


                tone2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SettingActivity.this, "" + tone2.getText(), Toast.LENGTH_SHORT).show();
                        //   addWater.setText("100");
                        sharedPreferences.edit().putString("tone", "tone2").apply();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

        });




        Reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Reminder.isChecked()) {
                    sharedPreferences.edit().putBoolean("NotificationOn", false).apply();
                    ReminderIsChecked = true;
                } else {
                    sharedPreferences.edit().putBoolean("NotificationOn", true).apply();
                    ReminderIsChecked = false;

                }
            }
        });



        //share by
        shareBy = findViewById(R.id.shareBy);
        shareBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"FeedbACKH2O@gmail.com"});

                emailIntent.setType("message/rfc822");

                startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));

            }
        });
        //dark mode settings
        dark_switch = findViewById(R.id.switch3);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);


        if (isDarkModeOn) {
            dark_switch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            dark_switch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }


        dark_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isDarkModeOn) {
                    /* card1.setCardBackgroundColor(Color.BLACK);*/


                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    //  getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                    editor.putBoolean("isDarkModeOn", false);
                    editor.apply();

                } else {

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    //                   getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    editor.putBoolean("isDarkModeOn", true);
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