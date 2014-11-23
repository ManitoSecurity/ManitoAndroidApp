package com.ensc40.manitosecurity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class Notification_Service{
    private static final String TAG = "NOTIFICATION";
    private NotificationManager mNotificationManager;
    private Context mContext;

    public Notification_Service (Context context){
        mContext = context;
    }

    public void displayNotification(String number) {
        Log.d(TAG, "notification");

        Toast.makeText(mContext, "NOTIFICATION!!",
                Toast.LENGTH_LONG).show();

        Notification.Builder  mBuilder = new Notification.Builder(mContext);

        mBuilder.setContentTitle("Manito Security");
        mBuilder.setContentText("I think I just saw something!");
        mBuilder.setTicker("Detection");
        mBuilder.setSmallIcon(R.drawable.button_on);
        mBuilder.setAutoCancel(true);
	      
		/* Creates an explicit intent for an Activity in your app */
        Intent resultIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null));

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        //stackBuilder.addParentStack(NotificationView.class);
	
		/* Adds the Intent that starts the Activity to the top of the stack */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        int randomInt = (int)(1000.0 * Math.random());
        //int notificationID = 100;
		/* notificationID allows you to update the notification later on. */
        mNotificationManager.notify(randomInt , mBuilder.build());
    }

}
