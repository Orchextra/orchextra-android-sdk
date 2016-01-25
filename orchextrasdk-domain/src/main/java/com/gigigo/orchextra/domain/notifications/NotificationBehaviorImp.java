package com.gigigo.orchextra.domain.notifications;

import com.gigigo.orchextra.domain.device.DeviceRunningModeType;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.entities.actions.strategy.Notification;
import com.gigigo.orchextra.domain.entities.triggers.AppRunningModeType;

public class NotificationBehaviorImp implements NotificationBehavior {

    private final DeviceRunningModeType deviceRunningModeType;
    private final NotificationBuilder foregroundNotificationBuilder;
    private final NotificationBuilder backgroundNotificationBuilder;

    private ActionDispatcherListener actionDispatcherListener;

    public NotificationBehaviorImp(DeviceRunningModeType deviceRunningModeType,
                                   NotificationBuilder foregroundNotificationBuilder,
                                   NotificationBuilder backgroundNotificationBuilder) {
        this.deviceRunningModeType = deviceRunningModeType;
        this.foregroundNotificationBuilder = foregroundNotificationBuilder;
        this.backgroundNotificationBuilder = backgroundNotificationBuilder;
    }

    @Override
    public void dispatchNotificationAction(BasicAction action, Notification notification) {

        if (deviceRunningModeType.getAppRunningModeType() == AppRunningModeType.FOREGROUND) {
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
