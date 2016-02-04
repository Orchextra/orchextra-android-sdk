package com.gigigo.orchextra.actions;

import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;

public interface ActionExecutor {

    void execute(BasicAction action);
}
