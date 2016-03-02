package com.gigigo.orchextra.domain.abstractions.notifications;

import com.gigigo.orchextra.domain.abstractions.actions.ActionDispatcherListener;

public interface ForegroundNotificationBuilder extends NotificationBuilder {
  void setActionDispatcherListener(ActionDispatcherListener actionDispatcherListener);
}
