package com.gigigo.orchextra.device.notifications;

import android.app.PendingIntent;

import com.gigigo.orchextra.device.actions.ActionRecovery;
import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;
import com.gigigo.orchextra.domain.abstractions.notifications.NotificationBuilder;

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
        //TODO Review if is needed to call setShown here because it was already called from performAction in BasicAction.java
        AndroidBasicAction androidBasicAction = androidBasicActionMapper.modelToExternalClass(
            action);

        PendingIntent pendingIntent = androidNotification.getPendingIntent(androidBasicAction);

        androidNotification.createNotification(notification, pendingIntent);
    }

}
