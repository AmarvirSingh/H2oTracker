package com.example.h2otracker;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h2otracker.HelperClass.HelperClass;
import com.example.h2otracker.serviceAndBroadcasts.MyBroadcast;
/*import com.example.h2otracker.serviceAndBroadcasts.MyService;
import com.example.h2otracker.serviceAndBroadcasts.Restarter;*/
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainContent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    TextView quotes, waterQuantity;
    String[] motiQuotes = {"Good Quote", "fds", "fds", "fsd"};
    Button addWater, nextQuote, changeCup, changeDrink;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    //firebase stuff
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;


    private int totalIntake;
    private int totalAmount = 0;

    HelperClass helperClass;
    HistoryAdapter historyAdapter;
    ArrayList<HistoryClass> historyClassArrayList = new ArrayList<>();

    SharedPreferences sharedPreferencesWaterIntake, sharedPreferencesForUserInfo;


    Intent mServiceIntent;


    @Override
    protected void onStart() {

        boolean isDarkModeOn = getSharedPreferences("sharedPrefs", MODE_PRIVATE).getBoolean("isDarkModeOn", false);
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onStart();
    }

    /*@Override
    protected void onDestroy() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        createNotificationChannel();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // using shared preference for total intake
        sharedPreferencesWaterIntake = getSharedPreferences("WaterIntake", MODE_PRIVATE);

        // shared preference for user info
        sharedPreferencesForUserInfo = getSharedPreferences("UserInfo", MODE_PRIVATE);


        //calculating waterIntake
        calculateWaterIntake();

        totalIntake = sharedPreferencesWaterIntake.getInt("totalIntake", 0);
/*
        // service code

        mYourService = new MyService();
        mServiceIntent = new Intent(this, mYourService.getClass());
        if (!isMyServiceRunning(mYourService.getClass())) {
            startService(mServiceIntent);
        }*/


        //setting up firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");

/*

        Timer timer = new Timer();
        final Handler handler = new Handler();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {


                    }
                });
            }
        };
        timer.schedule(timerTask, 5000);
*/
        Intent intent = new Intent(MainContent.this, MyBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainContent.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10000, pendingIntent); // every 10 seconds




        quotes = findViewById(R.id.quotesID);
        addWater = findViewById(R.id.AddWater);
        waterQuantity = findViewById(R.id.quantity);
        nextQuote = findViewById(R.id.nextText);
        changeCup = findViewById(R.id.changeCup);
        changeDrink = findViewById(R.id.changeDrink);
        recyclerView = findViewById(R.id.recyclerView);

        changeDrink.setText("Water");

        helperClass = new HelperClass(this);

        try {
            // getting history from helper class
            historyClassArrayList = helperClass.getHistory();

            if (historyClassArrayList.size() > 0) {
                historyAdapter = new HistoryAdapter(MainContent.this, historyClassArrayList, helperClass);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainContent.this));
                recyclerView.setAdapter(historyAdapter);
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        // working with progress bar
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(totalIntake);
        progressBar.setProgress(totalAmount);
        waterQuantity.setText(0 + "/" + totalIntake);

        addWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intake = Integer.parseInt(addWater.getText().toString()); // converting string to integer value
                int pro = progressBar.getProgress();
                if (pro < progressBar.getMax()) {
                    progressBar.setProgress(pro + intake);
                    waterQuantity.setText(pro + intake + " ml / " + totalIntake + "ml");
                    // adding data to array list to use in recycler view
                  /*  type.add(changeDrink.getText().toString().trim());
                    amount.add(addWater.getText().toString().trim() + " ml");*/
                    Calendar calendar = Calendar.getInstance();
                    String currentTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);


                    long result = helperClass.addRecord(addWater.getText().toString(), changeDrink.getText().toString(), currentTime);
                    if (result != -1) {
                        Toast.makeText(MainContent.this, "done ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainContent.this, "Not done", Toast.LENGTH_SHORT).show();
                    }

                    refreshRecyclerView();


                }
            }

            private void refreshRecyclerView() {
                historyClassArrayList.clear();
                historyClassArrayList = helperClass.getHistory();
                historyAdapter = new HistoryAdapter(MainContent.this, historyClassArrayList, helperClass);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainContent.this));
                recyclerView.setAdapter(historyAdapter);
                historyAdapter.notifyDataSetChanged();
            }
        });

        // change cup style
        changeCup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(MainContent.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.cup_selector_dialog);

                Button smallSize = dialog.findViewById(R.id.smallCup);
                Button mediumSize = dialog.findViewById(R.id.mediumCup);
                Button largeSize = dialog.findViewById(R.id.largeCup);


                smallSize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainContent.this, "" + smallSize.getText(), Toast.LENGTH_SHORT).show();
                        addWater.setText("100");
                        dialog.dismiss();
                    }
                });
                mediumSize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainContent.this, "" + mediumSize.getText(), Toast.LENGTH_SHORT).show();
                        addWater.setText("200");
                        dialog.dismiss();
                    }
                });
                largeSize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainContent.this, "" + largeSize.getText(), Toast.LENGTH_SHORT).show();
                        addWater.setText("300");
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });


        // change drink
        changeDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(MainContent.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.drink_selector_dialog);

                Button waterDrink = dialog.findViewById(R.id.waterDrink);
                Button coffeeDrink = dialog.findViewById(R.id.coffeeDrink);
                Button SodaDrink = dialog.findViewById(R.id.sodaDrink);


                waterDrink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainContent.this, "" + waterDrink.getText(), Toast.LENGTH_SHORT).show();
                        changeDrink.setText("Water");
                        dialog.dismiss();
                    }
                });
                coffeeDrink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainContent.this, "" + coffeeDrink.getText(), Toast.LENGTH_SHORT).show();
                        changeDrink.setText("Coffee");
                        dialog.dismiss();
                    }
                });
                SodaDrink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainContent.this, "" + SodaDrink.getText(), Toast.LENGTH_SHORT).show();
                        changeDrink.setText("Soda");
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });


        quotesChange();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        TextView userName = view.findViewById(R.id.userName);
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick:  in username");
                startActivity(new Intent(MainContent.this, ProfileActivity.class));
            }
        });


    }

    private void calculateWaterIntake() {

        //receiving this information from profile activity
        //edited : we can not get information from profile activity because our first main screen is this activity
        //so we have to get info from the RealTime Database

        int userAge = sharedPreferencesForUserInfo.getInt("age", 0);
        int weight = sharedPreferencesForUserInfo.getInt("weight", 0);
        int height = sharedPreferencesForUserInfo.getInt("height", 0);
        String gender = sharedPreferencesForUserInfo.getString("gender", "");


        if (userAge < 13) {
            sharedPreferencesWaterIntake.edit().putInt("totalIntakeAge", 1700).apply();
        } else if (userAge >= 14 && userAge <= 18) {
            sharedPreferencesWaterIntake.edit().putInt("totalIntakeAge", 2300).apply();
        } else if (userAge >= 19) {
            if (gender.equalsIgnoreCase("male")) {
                sharedPreferencesWaterIntake.edit().putInt("totalIntakeAge", 3100).apply();
            } else {
                sharedPreferencesWaterIntake.edit().putInt("totalIntakeAge", 2100).apply();
            }
        }

        if (weight >= 45 && weight < 55) {
            sharedPreferencesWaterIntake.edit().putInt("totalIntakeWeight", 1500).apply();
        } else if (weight >= 55 && weight < 65) {
            sharedPreferencesWaterIntake.edit().putInt("totalIntakeWeight", 1900).apply();
        } else if (weight >= 65 && weight < 75) {
            sharedPreferencesWaterIntake.edit().putInt("totalIntakeWeight", 2200).apply();
        } else if (weight >= 75 && weight < 85) {
            sharedPreferencesWaterIntake.edit().putInt("totalIntakeWeight", 2600).apply();
        }

        int total1 = sharedPreferencesWaterIntake.getInt("totalIntakeAge", 0);
        int totol2 = sharedPreferencesWaterIntake.getInt("totalIntakeWeight", 0);

        int finalTotal = (total1 + totol2) / 2;

        sharedPreferencesWaterIntake.edit().putInt("totalIntake", finalTotal).apply();

        Toast.makeText(this, "Total Amount of water needed is  " + finalTotal, Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_content, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
            case R.id.Statistics:
                startActivity(new Intent(this, StaticAndHistoryActivity.class));
                return true;
            case R.id.nav_logout:
                mAuth.signOut();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                Toast.makeText(this, "activity Not Applied ", Toast.LENGTH_SHORT).show();
                return true;
        }
    }

    public void quotesChange() {
        String c;
        for (int i = 0; i < motiQuotes.length; i++) {
            c = motiQuotes[i];
            quotes.setText(String.valueOf(c));
        }

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NewChannel";
            String description = " this is the description ";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("inform", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // for service
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }





}