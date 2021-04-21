package com.example.h2otracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    public static final int TOTAL_PAGES = 3;
    private ViewPager viewPager;
    EditText etName, etAge, etWeight, etHeight, etEmail, etPassword;
    TextView sleepTime, wakeTime;
    Button btnTakeMeIn, btnSleep, btnWake;
    Spinner spinner;
    ProgressBar progressBarInRegister;

    String[] gender = {"Male", "Female"};

    //global variables
    String userGender, userWakeUp, userSleep;


    FirebaseAuth mAuth;
    FirebaseDatabase database;

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
        progressBarInRegister = findViewById(R.id.progressBarInRegister);


        progressBarInRegister.setVisibility(View.INVISIBLE);


        Log.d("TAG", "On create ");

        ArrayAdapter adapter = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, gender);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userGender = gender[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mAuth = FirebaseAuth.getInstance();


        btnTakeMeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = etName.getText().toString();
                String userAge = etAge.getText().toString();
                String userWeight = etWeight.getText().toString();
                String userHeight = etHeight.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();


                Log.d("Tag", "Take me in Clicked: ");


                if (name.isEmpty()) {
                    etName.setError("Please fill this field!");
                    etName.requestFocus();
                    return;
                }
                if (userAge.isEmpty()) {
                    etAge.setError("Please fill this field!");
                    etAge.requestFocus();
                    return;
                }
                if (userWeight.isEmpty()) {
                    etWeight.setError("Please fill this field!");
                    etWeight.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    etEmail.setError("Please Enter your email");
                    etEmail.requestFocus();
                    return;
                }
                if (password.length() <= 6) {
                    etPassword.setError("Password should be greater than 6");
                    etPassword.requestFocus();
                    return;
                }
                if (userHeight.isEmpty()) {
                    etHeight.setError("Please fill this field!");
                    etHeight.requestFocus();
                    return;
                }
                if (wakeTime.getText().toString().equals("Select Wakeup Time")) {
                    Toast.makeText(getApplicationContext(), "please select wake up time", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sleepTime.getText().toString().equals("Select Sleep Time")) {
                    Toast.makeText(getApplicationContext(), "please select sleep time", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBarInRegister.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name, email, userAge, userHeight, userWeight, userGender, userWakeUp, userSleep);
                            FirebaseDatabase.getInstance().getReference("User").child(mAuth.getCurrentUser().getUid())
                                    .child("UserInfo").setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                        progressBarInRegister.setVisibility(View.INVISIBLE);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                        Dialog dialog = new Dialog(RegisterActivity.this);
                                        builder.setTitle("Register Successfully")
                                                .setMessage("Please login to enter the Application")
                                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                                // The dialog is automatically dismissed when a dialog button is clicked.
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Continue with delete operation
                                                        return;
                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Can not create id ", Toast.LENGTH_SHORT).show();
                            progressBarInRegister.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });


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
                        wakeTime.setText(selectedHour + "-" + selectedMinute);
                        userWakeUp = wakeTime.getText().toString();
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
                        userSleep = sleepTime.getText().toString();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


    }


}