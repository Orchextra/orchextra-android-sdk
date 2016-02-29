package com.gigigo.orchextra.domain.model.actions.types;

import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;
import com.gigigo.orchextra.domain.model.actions.strategy.Schedule;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ScanAction extends BasicAction {

  public ScanAction(String id, String trackId, String url, Notification notification, Schedule schedule) {
    super(id, trackId, url, notification, schedule);
    this.actionType = com.gigigo.orchextra.domain.model.actions.ActionType.SCAN;
  }

  @Override protected void performSimpleAction(ActionDispatcher actionDispatcher) {
    actionDispatcher.dispatchAction(this);
  }

  @Override protected void performNotifAction(ActionDispatcher actionDispatcher) {
    actionDispatcher.dispatchAction(this, notifFunctionality.getNotification());
  }

}
