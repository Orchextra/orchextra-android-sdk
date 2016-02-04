package com.gigigo.orchextra.domain.notifications;

import com.gigigo.orchextra.domain.device.AppRunningMode;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.entities.actions.strategy.Notification;
import com.gigigo.orchextra.domain.entities.triggers.AppRunningModeType;

public class NotificationBehaviorImp implements NotificationBehavior {

    private final AppRunningMode appRunningMode;
    private final NotificationBuilder foregroundNotificationBuilder;
    private final NotificationBuilder backgroundNotificationBuilder;

    private ActionDispatcherListener actionDispatcherListener;

    public NotificationBehaviorImp(AppRunningMode appRunningMode,
                                   NotificationBuilder foregroundNotificationBuilder,
                                   NotificationBuilder backgroundNotificationBuilder) {
        this.appRunningMode = appRunningMode;
        this.foregroundNotificationBuilder = foregroundNotificationBuilder;
        this.backgroundNotificationBuilder = backgroundNotificationBuilder;
    }

    @Override
    public void dispatchNotificationAction(BasicAction action, Notification notification) {

        if (appRunningMode.getRunningModeType() == AppRunningModeType.FOREGROUND) {
            foregroundNotificationBuilder.setActionDispatcherListener(actionDispatcherListener);
            foregroundNotificationBuilder.buildNotification(action, notification);
        } else {
            backgroundNotificationBuilder.setActionDispatcherListener(actionDispatcherListener);
            backgroundNotificationBuilder.buildNotification(action, notification);
        }
    }

    @Override
    public void setActionDispatcherListener(ActionDispatcherListener actionDispatcherListener) {
        this.actionDispatcherListener = actionDispatcherListener;
    }
}
