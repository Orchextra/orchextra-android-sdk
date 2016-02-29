package com.gigigo.orchextra.domain.model.entities.proximity;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 29/2/16.
 */
public class ActionRelated {

  final String actionId;
  final boolean cancelable;

  public ActionRelated(String actionId, boolean cancelable) {
    this.actionId = actionId;
    this.cancelable = cancelable;
  }

  public String getActionId() {
    return actionId;
  }

  public boolean isCancelable() {
    return cancelable;
  }
}
