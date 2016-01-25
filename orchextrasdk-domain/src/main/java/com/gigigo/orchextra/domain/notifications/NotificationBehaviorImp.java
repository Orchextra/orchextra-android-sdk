package com.gigigo.orchextra.domain.notifications;

import com.gigigo.orchextra.domain.device.DeviceRunningModeType;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.entities.actions.strategy.Notification;
import com.gigigo.orchextra.domain.entities.triggers.AppRunningModeType;

public class NotificationBehaviorImp implements NotificationBehavior {

    private final DeviceRunningModeType deviceRunningModeType;
    private final NotificationBuilder foregroundNotificationBuilder;
    private final NotificationBuilder backgroundNotificationBuilder;

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
            foregroundNotificationBuilder.buildNotification(action, notification);
        } else {
            backgroundNotificationBuilder.buildNotification(action, notification);
        }

    }
}
