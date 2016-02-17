package com.gigigo.orchextra.domain.services.actions;

import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 12/2/16.
 */
public interface EventAccessor {
  void updateEventWithAction(BasicAction basicAction);
}
