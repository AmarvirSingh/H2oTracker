package com.example.h2otracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainContent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;

    TextView quotes, waterQuantity;
    String[] motiQuotes = {"tfyds", "fds", "fds", "fsd"};
    Button addWater, nextText;
    ProgressBar progressBar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        quotes = findViewById(R.id.quotesID);
        addWater = findViewById(R.id.AddWater);
        waterQuantity = findViewById(R.id.quantity);
        nextText = findViewById(R.id.nextText);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(5000);
        progressBar.setProgress(50);
        addWater.setOnClickListener(this);
       // nextText.setOnClickListener(this);

        quotesChange();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                progressBar.setVisibility(view.VISIBLE);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/


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
            default:
                Toast.makeText(this, "we can not ", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        for (int i = 0; i < 20; i++) {
            int x = 0;
            if (addWater.isPressed()) {
                x += 250;

                progressBar.setProgress(x);
            }
            waterQuantity.setText(String.valueOf(x));
        }

 /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/


    }
}