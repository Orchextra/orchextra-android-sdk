package com.gigigo.orchextra.domain.entities.actions.strategy;

import com.gigigo.orchextra.domain.entities.actions.*;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 18/12/15.
 */
public abstract class BasicAction {

  protected ActionType actionType;
  protected URLFunctionality urlFunctionality;
  protected NotifFunctionality notifFunctionality;

  public BasicAction(String url, Notification notification) {
    this.urlFunctionality = new URLFunctionalityImpl(url);
    this.notifFunctionality = new NotifFunctionalityImpl(notification);
  }

  public ActionType getActionType() {
    return actionType;
  }

  public void performAction(
      com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher actionDispatcher) {
    if (notifFunctionality.isSupported()){
      performSimpleAction(actionDispatcher);
    }else{
      performNotifAction(actionDispatcher);
    }

  }

  public String getUrl() {
    return this.urlFunctionality.getUrl();
  }

  protected abstract void performSimpleAction(
      com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher actionDispatcher);
  protected abstract void performNotifAction(
      com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher actionDispatcher);

}
