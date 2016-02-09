package com.gigigo.orchextra.domain.model.actions.types;

import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ScanAction extends
    com.gigigo.orchextra.domain.model.actions.strategy.BasicAction {

  public ScanAction(String url, com.gigigo.orchextra.domain.model.actions.strategy.Notification notification, com.gigigo.orchextra.domain.model.actions.strategy.Schedule schedule) {
    super(url, notification, schedule);
    this.actionType = com.gigigo.orchextra.domain.model.actions.ActionType.SCAN;
  }

  @Override protected void performSimpleAction(ActionDispatcher actionDispatcher) {
    actionDispatcher.dispatchAction(this);
  }

  @Override protected void performNotifAction(ActionDispatcher actionDispatcher) {
    actionDispatcher.dispatchAction(this, notifFunctionality.getNotification());
  }

}
