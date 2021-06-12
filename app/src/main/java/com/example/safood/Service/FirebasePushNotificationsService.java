package com.example.safood.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.safood.Common.Common;
import com.example.safood.MainActivity;
import com.example.safood.Model.Notification;
import com.example.safood.Model.User;
import com.example.safood.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class FirebasePushNotificationsService extends FirebaseMessagingService {
    private static final String TAG = FirebasePushNotificationsService.class.getSimpleName();
    FirebaseDatabase database;
    private DatabaseReference users;

    public FirebasePushNotificationsService() {
        super();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String phone = Common.currentUser.getPhone();
                User user = snapshot.child(phone).getValue(User.class);
                String[] choicecs = user.getChoice().split(" ");
                //fixing array of choices

                for (int i =0 ;i<choicecs.length;i++){
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(choicecs[i]).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Toast.makeText(MainActivity.this, "UnSubscribed to " + topic, Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Toast.makeText(MainActivity.this, "Failed to unsubscribe", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        Log.d(TAG, "Token: " + token);

        //upload the token to your server
        //you have to store in preferences
    }

    /**
     * method to receive the incoming push notifications
     *
     * @param remoteMessage contains notification and data payload
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "Notification: " + remoteMessage.getFrom());

        //create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }

        //check if push notification has notification payload or not
        if (remoteMessage.getNotification() != null) {

            //get the title and body
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            Log.d(TAG, "Notification Title: " + title + " - Body: " + body);

            showNotification(title, body);
        }

        //check if push notification has data payload or not
        if (remoteMessage.getData().size() > 0) {

            //iterate the map to get key and value
            for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
                Log.d(TAG, "Key: " + entry.getKey() + " - Value: " + entry.getValue());
            }

            //show notification if required
            //showNotification(title, body);
        }

    }

    /**
     * method to create the notification channel
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        //channel_id should be unique for every notification channel
        NotificationChannel notificationChannel = new NotificationChannel("channel_id", "Test Notification Channel",
                NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription("My test notification channel");

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    /**
     * method to show the notification
     *
     * @param title of the notification
     * @param body  of the notification
     */
    private void showNotification(String title, String body) {

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);

        //pass the same channel_id which we created in previous method
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "channel_id")
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        notificationManager.notify(1, builder.build());
    }


}
