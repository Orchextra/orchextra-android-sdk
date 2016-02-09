package com.gigigo.orchextra.domain.model.actions.types;

import com.gigigo.orchextra.domain.model.actions.strategy.Schedule;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class VuforiaScanAction extends
    com.gigigo.orchextra.domain.model.actions.strategy.BasicAction {

  public VuforiaScanAction(String url, com.gigigo.orchextra.domain.model.actions.strategy.Notification notification, Schedule schedule) {
    super(url, notification, schedule);
    this.actionType = com.gigigo.orchextra.domain.model.actions.ActionType.VUFORIA_SCAN;
  }

  @Override protected void performSimpleAction(ActionDispatcher actionDispatcher) {
    actionDispatcher.dispatchAction(this);
  }

  @Override protected void performNotifAction(ActionDispatcher actionDispatcher) {
    actionDispatcher.dispatchAction(this, notifFunctionality.getNotification());
  }

}
