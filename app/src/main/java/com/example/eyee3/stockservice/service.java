package com.example.eyee3.stockservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class service extends Service {
    private NotificationManager mNM;
    private String TAG = "LocalService";
    private  final IBinder serviceBinder = new MyLocalBinder();

    public service() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return serviceBinder;
    }

    public String[] getStock() {
        String[] stocks = new String[] { "$1755.25", "$216.36", "$367.47" };
        return stocks;
    }

    public class MyLocalBinder extends Binder {
        service getService() {
            return service.this;
        }
    }

    @Override
    public void onCreate() {
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        showNotification();
    }

    private void showNotification() {
        CharSequence text = "Service has started";

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(service.this, "Chapman")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle("Service") // title for notification
                .setContentText(text)// message for notification
                .setAutoCancel(true); // clear notification after click

        Intent intent = new Intent(service.this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(service.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
