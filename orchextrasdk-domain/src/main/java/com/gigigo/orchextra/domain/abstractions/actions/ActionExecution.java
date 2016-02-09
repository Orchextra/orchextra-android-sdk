package com.gigigo.orchextra.domain.abstractions.actions;

import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;

public interface ActionExecution {

    void execute(BasicAction action);
}
