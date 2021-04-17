package com.example.h2otracker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainContent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    TextView quotes, waterQuantity;
    String[] motiQuotes = {"Good Quote", "fds", "fds", "fsd"};
    Button addWater, nextQuote, changeCup, editCup;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    private int totalIntake = 2250;
    private  int totalAmount = 0;



    @Override
    protected void onStart() {

        boolean isDarkModeOn = getSharedPreferences("sharedPrefs",MODE_PRIVATE).getBoolean("isDarkModeOn",false);
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance();

        quotes = findViewById(R.id.quotesID);
        addWater = findViewById(R.id.AddWater);
        waterQuantity = findViewById(R.id.quantity);
        nextQuote = findViewById(R.id.nextText);
        changeCup = findViewById(R.id.changeCup);


        // working with progress bar
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(totalIntake);
        progressBar.setProgress(totalAmount);
        waterQuantity.setText(0 + "/" + totalIntake );



        addWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intake =  Integer.parseInt(addWater.getText().toString()); // converting string to integer value
                int pro = progressBar.getProgress();
                if ( pro < progressBar.getMax()){
                    progressBar.setProgress(pro+intake);
                    waterQuantity.setText(pro + intake + "/ "+ totalIntake);
                }
            }
        });

        // change cup style
        changeCup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(MainContent.this);

                dialog.setCancelable(false);
                dialog.setContentView(R.layout.cup_selector_dialog);

                Button smallSize  = dialog.findViewById(R.id.smallCup);
                Button mediumSize  = dialog.findViewById(R.id.mediumCup);
                Button largeSize  = dialog.findViewById(R.id.largeCup);


                smallSize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainContent.this, ""+smallSize.getText(), Toast.LENGTH_SHORT).show();
                        addWater.setText("100");
                        dialog.dismiss();
                    }
                });
                mediumSize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainContent.this, ""+mediumSize.getText(), Toast.LENGTH_SHORT).show();
                        addWater.setText("200");
                        dialog.dismiss();
                    }
                });
                largeSize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainContent.this, ""+largeSize.getText(), Toast.LENGTH_SHORT).show();
                        addWater.setText("300");
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



}