package com.ifcompany.smart_house_01.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ifcompany.smart_house_01.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCMService";
    private static final String CHANNEL_ID = "default_channel";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From : " + remoteMessage.getFrom());

        // 메시지에 알림이 포함된 경우
        if(remoteMessage.getNotification() != null) {

            Log.d(TAG, "Message Notification Title : " + remoteMessage.getNotification().getTitle());
            Log.d(TAG, "Message Notification Body : " + remoteMessage.getNotification().getBody());

            // 알림 전송
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String title, String messageBody) {

        // Android 8.0 이상에서는 NotificationChannel 필요
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID
                    , "Default Channel"
                    , NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Channel for default notifications");
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_notifications_active_24)  // 알림 아이콘 설정
                .setContentTitle(title)                                     // 알림 제목
                .setContentText(messageBody)                                // 알림 내용
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)           // 우선순위 설정
                .setAutoCancel(true);                                       // 알림 클릭 시 자동으로 닫힘

        notificationManager.notify(0, notificationBuilder.build());  // 알림 표시
    }

    @Override
    public void onNewToken(String token) {

        Log.d(TAG, "Refreshed token : " + token);
    }
}
