package com.gigigo.orchextra.domain.abstractions.notifications;

import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;

public interface NotificationBuilder {
    void buildNotification(BasicAction action, Notification notification);
}
