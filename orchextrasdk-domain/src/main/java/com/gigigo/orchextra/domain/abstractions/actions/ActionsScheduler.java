package com.gigigo.orchextra.domain.abstractions.actions;

import com.gigigo.orchextra.domain.model.actions.ScheduledAction;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public interface ActionsScheduler {

  void scheduleAction(ScheduledAction action);

  void cancelAction(ScheduledAction action);

  boolean hasPersistence();

}
