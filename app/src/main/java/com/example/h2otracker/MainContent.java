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
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h2otracker.HelperClass.HelperClass;
import com.example.h2otracker.serviceAndBroadcasts.MyBroadcast;
/*import com.example.h2otracker.serviceAndBroadcasts.MyService;
import com.example.h2otracker.serviceAndBroadcasts.Restarter;*/
import com.example.h2otracker.serviceAndBroadcasts.sendDataBroadcast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainContent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    TextView quotes, waterQuantity;
    String[] motiQuotes = {"You're so much stronger than your excuses", "Don't compare yourself to others.", "Don't Quit.", "Don't tell everyone your plans, instead show them your results.", "“I choose to make the rest of my life, the best of my life.”", "Nothing can dim the light that shines from within.”", "Hustlers don’t sleep, they na"};
    Button addWater, nextQuote, changeCup, changeDrink;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    View view;

    //firebase stuff
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    ImageView img;
    String[] images = {"medal.png", "trophy.png"};
    String UserName = "";
    private int totalIntake;
    private int totalAmount ;

    HelperClass helperClass;
    HistoryAdapter historyAdapter;
    ArrayList<HistoryClass> historyClassArrayList;

    SharedPreferences sharedPreferencesWaterIntake, sharedPreferencesForUserInfo,simpleSharedPreference;


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

        // making references
        quotes = findViewById(R.id.quotesID);
        addWater = findViewById(R.id.AddWater);
        waterQuantity = findViewById(R.id.quantity);
        // nextQuote = findViewById(R.id.nextText);
        changeCup = findViewById(R.id.changeCup);
        changeDrink = findViewById(R.id.changeDrink);
        recyclerView = findViewById(R.id.recyclerView);
        img = findViewById(R.id.imageView);
        view = findViewById(R.id.mainPageBackground);
        historyClassArrayList = new ArrayList<HistoryClass>();

        helperClass = new HelperClass(this);

        // using shared reference for setting background image if night mode is enabled
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs",MODE_PRIVATE);
        boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn",false);
        if (isDarkModeOn){
            view.setBackgroundResource(R.drawable.night_blue);
        }else{
            view.setBackgroundResource(R.drawable.day_blue);
        }

        // using shared preference for total intake
        sharedPreferencesWaterIntake = getSharedPreferences("WaterIntake", MODE_PRIVATE);

        // shared preference for user info
        sharedPreferencesForUserInfo = getSharedPreferences("UserInfo", MODE_PRIVATE);

        //simple shared preference
        simpleSharedPreference = getSharedPreferences("sharedPrefs",MODE_PRIVATE);

        //calculating waterIntake
        calculateWaterIntake();
        sendDataTOFirebase();





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

        Intent dataIntent = new Intent(MainContent.this, sendDataBroadcast.class);
        dataIntent.putExtra("amount",totalAmount);
        PendingIntent dataPendingIntent = PendingIntent.getBroadcast(MainContent.this,0,dataIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10000, pendingIntent); // every 10 seconds

        Calendar date = Calendar.getInstance();

        date.set(Calendar.HOUR, 23);
        date.set(Calendar.MINUTE, 35);
        date.set(Calendar.SECOND, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,date.getTimeInMillis(),dataPendingIntent);

        changeDrink.setText("Water");



        try {
            // getting history from helper class
            historyClassArrayList = helperClass.getHistory();

            if (historyClassArrayList.size() > 0) {
                historyAdapter = new HistoryAdapter(MainContent.this, historyClassArrayList, helperClass);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainContent.this));
                recyclerView.setAdapter(historyAdapter);
                calculateWaterDrinked();
            }

        } catch (Exception e) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }


        // working with progress bar
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(totalIntake);
        progressBar.setProgress(totalAmount);
        waterQuantity.setText(totalAmount + " ml /" + totalIntake + " ml");

        addWater.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
                    LocalDate date = null;
                    DateTimeFormatter formatter = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        date = LocalDate.now();
                        formatter = DateTimeFormatter.ofPattern("dd");
                    }

                    String text = date.format(formatter);


                    long result = helperClass.addRecord(addWater.getText().toString(), changeDrink.getText().toString(), currentTime);
                    if (result != -1) {
                        Toast.makeText(MainContent.this, "Data Added for this drink", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainContent.this, "Sorry can not store data For this drink", Toast.LENGTH_SHORT).show();
                    }

                    // for adding history to realtime database
                    FirebaseHistoryClass intakeHistory = new FirebaseHistoryClass(pro+intake,text);

                    reference.child(user.getUid()).child("History").child("waterIntake"+text).child("amountAndDate").setValue(intakeHistory).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainContent.this, "added to firebase", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainContent.this, "not added to firebase", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    // for each drinks respective amount
                        calculateCategory();


                    reference.child(user.getUid()).child("History").child("waterIntake"+text).child("Category").setValue(intakeHistory).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainContent.this, "added to firebase", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainContent.this, "not added to firebase", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    refreshRecyclerView();


                }
            }

            private void refreshRecyclerView() {
                try {
                    if (historyClassArrayList.size()>0){
                        historyClassArrayList.clear();
                    }
                }catch (NullPointerException e){
                    Toast.makeText(MainContent.this,"", Toast.LENGTH_SHORT).show();
                }

                historyClassArrayList = helperClass.getHistory();
                historyAdapter = new HistoryAdapter(MainContent.this, historyClassArrayList, helperClass);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainContent.this));
                recyclerView.setAdapter(historyAdapter);


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
                        img.setImageDrawable(getResources().getDrawable(R.drawable.large));
                    }


                });
                largeSize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainContent.this, "" + largeSize.getText(), Toast.LENGTH_SHORT).show();
                        addWater.setText("300");
                        dialog.dismiss();
                        img.setImageDrawable(getResources().getDrawable(R.drawable.medal));
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


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        TextView userName = view.findViewById(R.id.userName);
        userName.setText(UserName);
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick:  in username");
                startActivity(new Intent(MainContent.this, ProfileActivity.class));
            }
        });

        try {
            quotesChange();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void calculateCategory() {


    }

    private void sendDataTOFirebase() {
        Calendar calendar = Calendar.getInstance();

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    private void calculateWaterDrinked() {
        ArrayList<Integer> intakedWater = helperClass.totalIntakeByUser();
        int a = 0;
        for (int i= 0; i < intakedWater.size(); i++){
            a += intakedWater.get(i);

        }
        totalAmount = a;

    }

    private void calculateWaterIntake() {

        //receiving this information from profile activity
        //edited : we can not get information from profile activity because our first main screen is this activity
        //so we have to get info from the RealTime Database
        UserName = sharedPreferencesForUserInfo.getString("name","Mundi");
        int userAge = sharedPreferencesForUserInfo.getInt("age", 0);
        int weight = sharedPreferencesForUserInfo.getInt("weight", 0);
        int height = sharedPreferencesForUserInfo.getInt("height", 0);
        String gender = sharedPreferencesForUserInfo.getString("gender", "");

        Toast.makeText(this, "age "+userAge, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "weight "+weight, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "gender "+gender, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "name "+UserName, Toast.LENGTH_SHORT).show();

        if (userAge < 13) {
            sharedPreferencesWaterIntake.edit().putInt("totalIntakeAge", 1700).apply();
        } if (userAge >= 14 && userAge <= 18) {
            sharedPreferencesWaterIntake.edit().putInt("totalIntakeAge", 2300).apply();
        } if (userAge >= 19) {
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

        Toast.makeText(this, "totalIntakeAge"+total1, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "totalIntakeWeight"+totol2, Toast.LENGTH_SHORT).show();
        int finalTotal = (total1 + totol2 * 2 ) / 2;

        totalIntake = finalTotal;
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
            case R.id.acheivements:
                startActivity(new Intent(this, Achievements.class));
                return true;
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

    public void quotesChange() throws InterruptedException {
        Random random = new Random();
        int h = random.nextInt((5 - 0) + 1) + 0;
        quotes.setText(String.valueOf(motiQuotes[h]));
        new CountDownTimer(5000, 2000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                try {
                    quotesChange();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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
            if (!simpleSharedPreference.getBoolean("NotificationOn",true)){
                channel.setSound(null,null);
            }
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
                Log.i("Service status", "Running");
                return true;
            }
        }
        Log.i("Service status", "Not running");
        return false;
    }


}