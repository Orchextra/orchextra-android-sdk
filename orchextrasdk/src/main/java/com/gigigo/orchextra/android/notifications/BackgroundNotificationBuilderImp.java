package com.gigigo.orchextra.android.notifications;

import android.app.PendingIntent;

import com.gigigo.orchextra.android.entities.AndroidBasicAction;
import com.gigigo.orchextra.android.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.entities.actions.strategy.Notification;
import com.gigigo.orchextra.domain.notifications.NotificationBuilder;

public class BackgroundNotificationBuilderImp implements NotificationBuilder {

    private final AndroidNotificationBuilder androidNotification;
    private final AndroidBasicActionMapper androidBasicActionMapper;

    private BasicAction action;

    public BackgroundNotificationBuilderImp(AndroidNotificationBuilder androidNotification,
                                            AndroidBasicActionMapper androidBasicActionMapper) {
        this.androidNotification = androidNotification;
        this.androidBasicActionMapper = androidBasicActionMapper;
    }

    @Override
    public void buildNotification(BasicAction action, Notification notification) {
        this.action = action;

        AndroidBasicAction androidBasicAction = androidBasicActionMapper.modelToControl(action);

        PendingIntent pendingIntent = androidNotification.getPendingIntent(androidBasicAction);

        androidNotification.createNotification(notification, pendingIntent);
    }
}
