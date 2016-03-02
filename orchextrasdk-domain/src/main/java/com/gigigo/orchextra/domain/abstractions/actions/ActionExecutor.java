package com.gigigo.orchextra.domain.abstractions.actions;

import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;

public interface ActionExecutor {
  void execute(BasicAction action);
}
