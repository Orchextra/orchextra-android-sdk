package com.gigigo.orchextra.domain.abstractions.actions;

import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;

public interface ActionDispatcherListener {
    void onActionAccepted(BasicAction action, boolean isForeground);
    void onActionDismissed(BasicAction action, boolean isForeground);
}
