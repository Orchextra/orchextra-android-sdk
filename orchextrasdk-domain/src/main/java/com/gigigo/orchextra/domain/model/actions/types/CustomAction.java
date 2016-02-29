package com.gigigo.orchextra.domain.model.actions.types;

import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;
import com.gigigo.orchextra.domain.model.actions.strategy.Schedule;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.model.actions.ActionType;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class CustomAction extends BasicAction {

  public CustomAction(String id, String trackId, String url, Notification notification, Schedule schedule) {
    super(id, trackId, url, notification, schedule);
    this.actionType = ActionType.CUSTOM_SCHEME;
  }

  @Override protected void performSimpleAction(ActionDispatcher actionDispatcher) {
    actionDispatcher.dispatchAction(this);
  }

  @Override protected void performNotifAction(ActionDispatcher actionDispatcher) {
    actionDispatcher.dispatchAction(this, notifFunctionality.getNotification());
  }

}
