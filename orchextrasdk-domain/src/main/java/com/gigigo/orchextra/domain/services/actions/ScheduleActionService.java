package com.gigigo.orchextra.domain.services.actions;

import com.gigigo.orchextra.domain.abstractions.actions.ActionsSchedulerController;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.services.DomaninService;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 12/2/16.
 */
public class ScheduleActionService implements DomaninService {

  private final ActionsSchedulerController actionsSchedulerController;

  public ScheduleActionService(ActionsSchedulerController actionsSchedulerController) {
    this.actionsSchedulerController = actionsSchedulerController;
  }

  public boolean schedulePendingAction(BasicAction action) {

    if (action.isScheduled()) {
      actionsSchedulerController.addAction(action.getScheduledAction());
      return true;
    } else {
      return false;
    }
  }

  public void cancelPendingActionWithId(String actionId, boolean forceCancel) {
    actionsSchedulerController.cancelPendingActionWithId(actionId, forceCancel);
  }
}
