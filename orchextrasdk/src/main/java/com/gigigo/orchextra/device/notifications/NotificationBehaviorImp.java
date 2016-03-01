package com.gigigo.orchextra.device.notifications;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.domain.abstractions.actions.ActionDispatcherListener;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.abstractions.notifications.ForegroundNotificationBuilder;
import com.gigigo.orchextra.domain.abstractions.notifications.NotificationBehavior;
import com.gigigo.orchextra.domain.abstractions.notifications.NotificationBuilder;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;

public class NotificationBehaviorImp implements NotificationBehavior {

    private final AppRunningMode appRunningMode;
    private final ForegroundNotificationBuilder foregroundNotificationBuilder;
    private final NotificationBuilder backgroundNotificationBuilder;
    private final ContextProvider contextProvider;

    private ActionDispatcherListener actionDispatcherListener;

    public NotificationBehaviorImp(AppRunningMode appRunningMode,
                                   ForegroundNotificationBuilder foregroundNotificationBuilder,
                                   NotificationBuilder backgroundNotificationBuilder,
                                   ContextProvider contextProvider) {
        this.appRunningMode = appRunningMode;
        this.foregroundNotificationBuilder = foregroundNotificationBuilder;
        this.backgroundNotificationBuilder = backgroundNotificationBuilder;
        this.contextProvider = contextProvider;
    }

    @Override
    public void dispatchNotificationAction(BasicAction action, Notification notification) {
        if (!contextProvider.getCurrentActivity().isFinishing() &&
                appRunningMode.getRunningModeType() == AppRunningModeType.FOREGROUND) {
            foregroundNotificationBuilder.setActionDispatcherListener(actionDispatcherListener);
            foregroundNotificationBuilder.buildNotification(action, notification);
        } else {
            backgroundNotificationBuilder.buildNotification(action, notification);
        }
    }

    @Override
    public void setActionDispatcherListener(
            ActionDispatcherListener actionDispatcherListener) {
        this.actionDispatcherListener = actionDispatcherListener;
    }
}
