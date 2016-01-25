package com.gigigo.orchextra.domain.notifications;

import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.entities.actions.strategy.Notification;

public interface NotificationBuilder {
    void buildNotification(BasicAction action, Notification notification);
}
