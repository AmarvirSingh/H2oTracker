package com.example.h2otracker.serviceAndBroadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.h2otracker.FirebaseHistoryClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sendDataBroadcast  extends BroadcastReceiver {
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    @Override
    public void onReceive(Context context, Intent intent) {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("User");
       int amount = intent.getIntExtra("amount",0);
       sendData(context,mAuth,database,reference,amount);
        Log.d("TAG", "send data broadcast : ");
    }
    private void sendData(Context context,FirebaseAuth mAuth, FirebaseDatabase database, DatabaseReference reference,int amount) {

        FirebaseHistoryClass historyClass = new FirebaseHistoryClass();
        historyClass.setAmount(amount);
        reference.child(mAuth.getCurrentUser().getUid()).child("History").setValue(historyClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
if (task.isSuccessful()){
    Toast.makeText(context, "data added", Toast.LENGTH_SHORT).show();
}else {
    Toast.makeText(context, "can not add data", Toast.LENGTH_SHORT).show();
}
            }
        });

    }
}
