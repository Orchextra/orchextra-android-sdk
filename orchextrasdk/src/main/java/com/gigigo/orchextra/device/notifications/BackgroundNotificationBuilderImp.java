package com.gigigo.orchextra.device.notifications;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;

import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;
import com.gigigo.orchextra.domain.abstractions.actions.ActionDispatcherListener;
import com.gigigo.orchextra.domain.abstractions.notifications.NotificationBuilder;

public class BackgroundNotificationBuilderImp implements NotificationBuilder, AndroidBackgroundNotificationActionManager {

    private final AndroidNotificationBuilder androidNotification;
    private final AndroidBasicActionMapper androidBasicActionMapper;
    private ActionDispatcherListener actionDispatcherListener;

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

    @Override
    public void setActionDispatcherListener(ActionDispatcherListener actionDispatcherListener) {
        this.actionDispatcherListener = actionDispatcherListener;
    }

    @Override
    public void manageBackgroundNotification(Activity activity) {
        if (actionDispatcherListener != null) {
            Intent intent = activity.getIntent();
            if (intent != null) {
                AndroidBasicAction androidBasicAction = intent.getParcelableExtra(AndroidNotificationBuilder.EXTRA_NOTIFICATION_ACTION);
                if (androidBasicAction != null) {
                    BasicAction basicAction = androidBasicActionMapper.externalClassToModel(
                        androidBasicAction);
                    actionDispatcherListener.onActionAccepted(basicAction, false);
                }
            }
        }
    }
}
