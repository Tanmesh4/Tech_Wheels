package com.example.techwheelsservicecentre;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App_notify extends Application {
public static final String chanelid="tech_wheels";


    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    public void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel=new NotificationChannel(
                    chanelid,
                    "Tech_Wheels",
                    NotificationManager.IMPORTANCE_HIGH );

            notificationChannel.setDescription(" Confirm Booking ");

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);




        }
    }
}
