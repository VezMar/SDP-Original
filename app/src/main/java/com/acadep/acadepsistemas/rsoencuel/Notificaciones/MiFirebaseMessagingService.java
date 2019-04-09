package com.acadep.acadepsistemas.rsoencuel.Notificaciones;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.acadep.acadepsistemas.rsoencuel.Clases.Login;
import com.acadep.acadepsistemas.rsoencuel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MiFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "NOTICIAS";
    private static String TokenCloudMessaging;

    public static final String TOKEN_BROADCAST = "myfmctokenbroadcast";

    FirebaseFirestore BDFireStore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

       if (remoteMessage.getData().isEmpty()){
            mostrarNotificacion(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }else{
           mostrarNotificacion(remoteMessage.getData());
        }


    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        TokenCloudMessaging = token;
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        Map<String, Object> Token = new HashMap<>();
        Token.put("token", token);
        BDFireStore.collection("users").document(mAuth.getUid()).set(Token, SetOptions.merge());
    }

    private void mostrarNotificacion(Map<String,String> data) {
        String title = data.get("title").toString();
        String body = data.get("body").toString();

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICACION_CHANNEL_ID = "com.acadep.acadepsistemas.rso.test";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICACION_CHANNEL_ID, "Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("EDMT Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        Intent resultIntent = new Intent(getApplicationContext(), Login.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this, NOTIFICACION_CHANNEL_ID);

        notiBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.rso_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(soundUri)
                .setContentInfo("Info")
                .setContentIntent(pendingIntent);


        notificationManager.notify(new Random().nextInt(), notiBuilder.build());
    }

    private void mostrarNotificacion(String title, String body) {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICACION_CHANNEL_ID = "com.acadep.acadepsistemas.rso.test";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICACION_CHANNEL_ID, "Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("EDMT Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        Intent resultIntent = new Intent(getApplicationContext(), Login.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this, NOTIFICACION_CHANNEL_ID);

        notiBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.rso_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(soundUri)
                .setContentInfo("Info")
                .setContentIntent(pendingIntent);


        notificationManager.notify(new Random().nextInt(), notiBuilder.build());

    }
}
