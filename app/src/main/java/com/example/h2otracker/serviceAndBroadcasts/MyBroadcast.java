package com.example.h2otracker.serviceAndBroadcasts;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.h2otracker.R;
import com.example.h2otracker.SettingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyBroadcast extends BroadcastReceiver {

SharedPreferences sharedPreferences;
    String tone = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAG", "onReceive: ");
        sharedPreferences = context.getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE);
        tone = sharedPreferences.getString("tone","");
        createNotification(context);


    }



    private void createNotification(Context context) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,"inform");
        mBuilder.setContentTitle("title notioficatioon");
        mBuilder.setContentText("thisnis text 0");
        mBuilder.setSmallIcon(R.drawable.app_icon);
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);

      /*  Intent notificationIntent = new Intent(context, MyBroadcast.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);*/
        mBuilder.setSound(null);

      if (tone.equalsIgnoreCase("tone1") && !sharedPreferences.getBoolean("NotificationOn",false)){
          MediaPlayer mp= MediaPlayer.create(context,R.raw.tone1);
          mp.start();
      } else if (tone.equalsIgnoreCase("tone2") && !sharedPreferences.getBoolean("NotificationOn",true)){
          MediaPlayer mp= MediaPlayer.create(context,R.raw.tone2);
          mp.start();
      }



        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(100,mBuilder.build());
    }
}
