package com.ifcompany.smart_house_01.alarm;

import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.onesignal.notifications.IDisplayableMutableNotification;
import com.onesignal.notifications.INotificationReceivedEvent;
import com.onesignal.notifications.INotificationServiceExtension;

@Keep
public class NotificationReceiveHandler implements INotificationServiceExtension  {

    private String TAG = "NotificationReceiveHandler";
    @Override
    public void onNotificationReceived(@NonNull INotificationReceivedEvent iNotificationReceivedEvent) {
        IDisplayableMutableNotification notification = iNotificationReceivedEvent.getNotification();
        Log.d(TAG, "Title " + notification.getTitle());
        Log.d(TAG, "Body " + notification.getBody());

        iNotificationReceivedEvent.preventDefault();
    }
}
