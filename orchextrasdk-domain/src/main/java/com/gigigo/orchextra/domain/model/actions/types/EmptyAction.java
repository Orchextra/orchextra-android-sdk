package com.gigigo.orchextra.domain.model.actions.types;

import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;
import com.gigigo.orchextra.domain.model.actions.strategy.Schedule;
import com.gigigo.orchextra.domain.model.actions.ActionType;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class EmptyAction extends BasicAction {

  public EmptyAction(String url, Notification notification, Schedule schedule) {
    super(url, notification, schedule);
    this.actionType = ActionType.NOT_DEFINED;
  }

  public EmptyAction() {
    super(null, null, null);
    this.actionType = ActionType.NOT_DEFINED;
  }

  @Override protected void performSimpleAction(ActionDispatcher actionDispatcher) {
    actionDispatcher.dispatchAction(this);
  }

  @Override protected void performNotifAction(ActionDispatcher actionDispatcher) {
    actionDispatcher.dispatchAction(this, notifFunctionality.getNotification());
  }

}
