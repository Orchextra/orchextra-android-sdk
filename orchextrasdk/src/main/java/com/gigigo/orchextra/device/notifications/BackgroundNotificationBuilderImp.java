package com.gigigo.orchextra.device.notifications;

import android.app.PendingIntent;

import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.domain.abstractions.notifications.NotificationBuilder;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;

public class BackgroundNotificationBuilderImp implements NotificationBuilder {

    private final AndroidNotificationBuilder androidNotification;
    private final AndroidBasicActionMapper androidBasicActionMapper;

    public BackgroundNotificationBuilderImp(AndroidNotificationBuilder androidNotification,
                                            AndroidBasicActionMapper androidBasicActionMapper) {
        this.androidNotification = androidNotification;
        this.androidBasicActionMapper = androidBasicActionMapper;
    }

    @Override
    public void buildNotification(BasicAction action, Notification notification) {
        AndroidBasicAction androidBasicAction = androidBasicActionMapper.modelToExternalClass(
            action);

        PendingIntent pendingIntent = androidNotification.getPendingIntent(androidBasicAction);

        androidNotification.createNotification(notification, pendingIntent);
    }

}
