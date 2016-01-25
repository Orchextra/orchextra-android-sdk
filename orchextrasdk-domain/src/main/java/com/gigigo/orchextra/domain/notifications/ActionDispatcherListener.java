package com.gigigo.orchextra.domain.notifications;

import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;

public interface ActionDispatcherListener {
    void onActionAccepted(BasicAction action, boolean isForeground);
    void onActionDismissed(BasicAction action, boolean isForeground);
}
