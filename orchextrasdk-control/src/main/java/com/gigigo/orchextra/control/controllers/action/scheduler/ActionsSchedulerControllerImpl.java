package com.gigigo.orchextra.control.controllers.action.scheduler;

import com.gigigo.orchextra.domain.abstractions.actions.ActionsScheduler;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsSchedulerController;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsSchedulerPersistor;
import com.gigigo.orchextra.domain.model.actions.ScheduledAction;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class ActionsSchedulerControllerImpl implements ActionsSchedulerController {

  private final ActionsScheduler actionsScheduler;
  private final ActionsSchedulerPersistor actionsSchedulerPersistor;

  public ActionsSchedulerControllerImpl(ActionsScheduler actionsScheduler,
      ActionsSchedulerPersistor actionsSchedulerPersistor) {

    this.actionsScheduler = actionsScheduler;
    this.actionsSchedulerPersistor = actionsSchedulerPersistor;
  }

  @Override public void cancelPendingActionWithId(String id, boolean forceCancel) {
    ScheduledAction action = actionsSchedulerPersistor.getScheduledActionWithId(id);

    if (action == null){
      action = BasicAction.generateCancelActionHolder(id, forceCancel);
    }

    cancelAction(forceCancel, action);
  }

  @Override public void addAction(ScheduledAction action) {
    actionsSchedulerPersistor.addAction(action);
    actionsScheduler.scheduleAction(action);
  }

  @Override public void scheduleAllPendingActions() {
    List<ScheduledAction> scheduledActions = actionsSchedulerPersistor.obtainAllPendingActions();
    for (ScheduledAction scheduledAction: scheduledActions){
      actionsScheduler.scheduleAction(scheduledAction);
    }
  }

  @Override public void cancelAllPendingActions(boolean forceCancel) {
    List<ScheduledAction> scheduledActions = actionsSchedulerPersistor.obtainAllPendingActions();
    for (ScheduledAction scheduledAction: scheduledActions){
      if (scheduledAction.isCancelable() || forceCancel)
        cancelAction(forceCancel, scheduledAction);
    }
  }

  @Override public void removeScheduledActionWithId(String id) {
    ScheduledAction action = actionsSchedulerPersistor.getScheduledActionWithId(id);
    removeAction(action);
  }

  @Override public void removeScheduledAction(ScheduledAction scheduledAction) {
    removeAction(scheduledAction);
  }

  private void cancelAction(boolean forceCancel, ScheduledAction action) {
    if (action.isCancelable() || forceCancel){
      cancelScheduledAction(action);
      removeScheduledAction(action);
    }
  }

  private void cancelScheduledAction(ScheduledAction action) {
    actionsScheduler.cancelAction(action);
  }

  private void removeAction(ScheduledAction action) {
    actionsSchedulerPersistor.removeAction(action);
  }

}
