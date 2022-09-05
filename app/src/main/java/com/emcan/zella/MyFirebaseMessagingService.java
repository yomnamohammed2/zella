package com.emcan.zella;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.emcan.zella.activities.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if(remoteMessage.getData().size()>0){
            Log.d(TAG, " Message Body: " + remoteMessage.getData());

            sendNotification(remoteMessage.getData().get("message"),remoteMessage.getData().get("type"),getApplicationContext());

        }

        if(remoteMessage.getNotification()!=null) {
           // Toast.makeText(getApplicationContext(), "Notification Message Body: " + remoteMessage.getNotification().getBody(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "From: " + remoteMessage.getFrom());
            Log.d(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            JSONObject object = null;
            try {
                object = new JSONObject(remoteMessage.getNotification().getBody());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("JSON OBJECT", object.toString());
            try {
                String callNumber = object.getString("message");
                String type = object.getString("type");
           //     sendNotification(callNumber,type,getApplicationContext());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("newToken", s);
        SharedPrefManager.getInstance(this).set_token(s);
    }


    public void sendNotification(String message , String type, Context context) {
        NotificationCompat.Builder notification_builder;

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("type",type);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final int not_nu=generateRandom();
        PendingIntent pendingIntent = PendingIntent.getActivity(context,not_nu/* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        NotificationManager notification_manager=
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
      NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notification_manager.createNotificationChannel(mChannel);
        }


        notification_builder = new NotificationCompat.Builder(context,channelId)
                .setSmallIcon(R.drawable.mainicon)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(message)

                .setStyle(bigText).setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(defaultSoundUri)
                 .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                         R.drawable.mainicon))
                .setContentIntent(pendingIntent);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
       notification_builder.setContentIntent(resultPendingIntent);

       notification_manager.notify(notificationId, notification_builder.build());
    }



    public int generateRandom(){
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }
}