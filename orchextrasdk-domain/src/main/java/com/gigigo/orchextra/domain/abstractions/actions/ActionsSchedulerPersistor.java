package com.gigigo.orchextra.domain.abstractions.actions;

import com.gigigo.orchextra.domain.model.actions.ScheduledAction;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public interface ActionsSchedulerPersistor {

  ScheduledAction getScheduledActionWithId(String id);

  void addAction(ScheduledAction action);

  List<ScheduledAction> obtainAllPendingActions();

  void removeAction(ScheduledAction action);
}
