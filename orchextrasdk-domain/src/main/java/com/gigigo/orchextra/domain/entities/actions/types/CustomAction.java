package com.gigigo.orchextra.domain.entities.actions.types;

import com.gigigo.orchextra.domain.entities.actions.strategy.Notification;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.entities.actions.ActionType;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class CustomAction extends BasicAction {

  public CustomAction(String url, Notification notification) {
    super(url, notification);
    this.actionType = ActionType.CUSTOM_SCHEME;
  }

  @Override protected void performSimpleAction(ActionDispatcher actionDispatcher) {
    actionDispatcher.dispatchAction(this);
  }

  @Override protected void performNotifAction(ActionDispatcher actionDispatcher) {
    actionDispatcher.dispatchAction(this, notifFunctionality.getNotification());
  }

}
