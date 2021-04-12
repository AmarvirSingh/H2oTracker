package com.example.h2otracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    public static final int TOTAL_PAGES = 3;
    private ViewPager viewPager;
    EditText etName, etAge, etWeight, etHeight, etEmail, etPassword;
    TextView sleepTime, wakeTime;
    Button btnTakeMeIn, btnSleep, btnWake;
    Spinner spinner;
    String [] gender = {"Male","Female"};
    ScrollView scrollView;

    //testing
    Button check;
    EditText checkInfo;


    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //creating refernences
        etEmail = findViewById(R.id.signUpEmail);
        etPassword = findViewById(R.id.signUpPassword);
        etName = findViewById(R.id.signUpFullName);
        etAge = findViewById(R.id.signUpAge);
        etWeight = findViewById(R.id.signUpWeight);
        etHeight = findViewById(R.id.signUpHeight);
        sleepTime = findViewById(R.id.signUpSleepTime);
        wakeTime = findViewById(R.id.signUpWakeTime);
        btnSleep = findViewById(R.id.btnSleep);
        btnTakeMeIn = findViewById(R.id.btnTakeMeIn);
        btnWake = findViewById(R.id.btnWake);
        spinner = findViewById(R.id.signUpSpinner);

        Log.d("TAG", "On create ");

        ArrayAdapter adapter = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item,gender);
        spinner.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();

        String name = etName.getText().toString();
        String userAge = etAge.getText().toString();
        String userWeight = etWeight.getText().toString();
        String userHeight = etHeight.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();


        btnTakeMeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Tag", "Take me in Clicked: ");


                if (name.isEmpty()){
                    etName.setError("Please fill this field!");
                    etName.requestFocus();
                    return;
                }
                if (userAge.isEmpty()){
                    etAge.setError("Please fill this field!");
                    etAge.requestFocus();
                    return;
                }
                if(userWeight.isEmpty()){
                    etWeight.setError("Please fill this field!");
                    etWeight.requestFocus();
                    return;
                }
                if (email.isEmpty()){
                    etEmail.setError("Please Enter your email");
                    etEmail.requestFocus();
                    return;
                }
                if (password.length()<=6){
                    etPassword.setError("Password should be greater than 6");
                    etPassword.requestFocus();
                    return;
                }
                if (userHeight.isEmpty()){
                    etHeight.setError("Please fill this field!");
                    etHeight.requestFocus();
                    return;
                }
                if (wakeTime.getText().toString().equals("Select Wakeup Time")){
                    Toast.makeText(getApplicationContext(), "please select wake up time", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sleepTime.getText().toString().equals("Select Sleep Time")){
                    Toast.makeText(getApplicationContext(), "please select sleep time", Toast.LENGTH_SHORT).show();
                    return;
                }




            }
        });



        checkInfo = findViewById(R.id.checkInfo);
        check = findViewById(R.id.check);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInfo.getText().toString().isEmpty()){
                    checkInfo.setError("Fill this");
                    return;
                }
            }
        });


        //viewPager = findViewById(R.id.pager);

      /*  slideAdapter = new ScreenSlideAdapter(getSupportFragmentManager());
        viewPager.setAdapter(slideAdapter);
        viewPager.setCurrentItem(viewPager.getCurrentItem());
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });*/

        btnWake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "sleepTime");
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(RegisterActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        wakeTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }

        });

        btnSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "Wakeiup time");
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(RegisterActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        sleepTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        btnTakeMeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

  /*  private static class ScreenSlideAdapter extends FragmentStatePagerAdapter{

        public ScreenSlideAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    FragmentCredential fragmentCredential = new FragmentCredential();
                    return fragmentCredential;
                case 1:
                    FragmentWeight fragmentWeight = new FragmentWeight();
                    return fragmentWeight;
                case 2:
                    SelectionFragment selectionFragment = new SelectionFragment();
                    return selectionFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return TOTAL_PAGES;
        }


    }*/

}