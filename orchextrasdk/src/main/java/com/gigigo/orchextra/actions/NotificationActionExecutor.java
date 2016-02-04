package com.gigigo.orchextra.actions;

import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.entities.actions.strategy.Notification;
import com.gigigo.orchextra.domain.notifications.NotificationBehavior;

public class NotificationActionExecutor implements ActionExecutor {

    private final NotificationBehavior notificationBehavior;

    public NotificationActionExecutor(NotificationBehavior notificationBehavior) {
        this.notificationBehavior = notificationBehavior;
    }

    @Override
    public void execute(BasicAction action) {
        Notification notification = action.getNotifFunctionality();
        notificationBehavior.dispatchNotificationAction(null, notification);
    }
}
