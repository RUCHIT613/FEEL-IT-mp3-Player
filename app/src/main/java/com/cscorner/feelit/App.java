package com.cscorner.feelit;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_1_ID="channel_1";
    @Override
    public void onCreate() {
        super.onCreate();
        create_notification();

    }
    public void create_notification(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel =new NotificationChannel(CHANNEL_1_ID,"service",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setImportance(NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            NotificationManager manager=getSystemService(NotificationManager.class);

            manager.createNotificationChannel(notificationChannel);
        }
    }
}
