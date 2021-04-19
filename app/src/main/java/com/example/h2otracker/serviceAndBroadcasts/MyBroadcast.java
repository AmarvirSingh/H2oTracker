package com.example.h2otracker.serviceAndBroadcasts;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.h2otracker.R;

public class MyBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,"inform");
        mBuilder.setContentTitle("title notioficatioon");
        mBuilder.setContentText("thisnis text 0");
        mBuilder.setSmallIcon(R.drawable.app_icon);
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);

      /*  Intent notificationIntent = new Intent(context, MyBroadcast.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);*/



        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(100,mBuilder.build());

    }
}
