package com.gigigo.orchextra.domain.model.actions.types;

import com.gigigo.orchextra.domain.model.actions.strategy.Schedule;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.model.actions.ActionType;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class CustomAction extends
    com.gigigo.orchextra.domain.model.actions.strategy.BasicAction {

  public CustomAction(String url, com.gigigo.orchextra.domain.model.actions.strategy.Notification notification, Schedule schedule) {
    super(url, notification, schedule);
    this.actionType = ActionType.CUSTOM_SCHEME;
  }

  @Override protected void performSimpleAction(ActionDispatcher actionDispatcher) {
    actionDispatcher.dispatchAction(this);
  }

  @Override protected void performNotifAction(ActionDispatcher actionDispatcher) {
    actionDispatcher.dispatchAction(this, notifFunctionality.getNotification());
  }

}
