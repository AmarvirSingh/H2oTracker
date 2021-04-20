package com.example.h2otracker.serviceAndBroadcasts;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DataService extends JobService {

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseAuth mAuth;


    @Override
    public boolean onStartJob(JobParameters params) {

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("User");
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();




        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
