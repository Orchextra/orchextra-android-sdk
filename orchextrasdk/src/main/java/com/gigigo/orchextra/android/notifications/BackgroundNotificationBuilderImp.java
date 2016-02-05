package com.gigigo.orchextra.android.notifications;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;

import com.gigigo.orchextra.android.entities.AndroidBasicAction;
import com.gigigo.orchextra.android.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.entities.actions.strategy.Notification;
import com.gigigo.orchextra.domain.notifications.ActionDispatcherListener;
import com.gigigo.orchextra.domain.notifications.NotificationBuilder;

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
        AndroidBasicAction androidBasicAction = androidBasicActionMapper.modelToAndroid(action);

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
                    BasicAction basicAction = androidBasicActionMapper.androidToModel(androidBasicAction);
                    actionDispatcherListener.onActionAccepted(basicAction, false);
                }
            }
        }
    }
}
