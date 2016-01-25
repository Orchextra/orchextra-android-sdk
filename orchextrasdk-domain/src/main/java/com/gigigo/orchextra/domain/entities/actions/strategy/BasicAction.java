package com.gigigo.orchextra.domain.entities.actions.strategy;

import com.gigigo.orchextra.domain.entities.actions.ActionType;
import com.gigigo.orchextra.domain.entities.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.entities.actions.types.CustomAction;
import com.gigigo.orchextra.domain.entities.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.entities.actions.types.NotificationAction;
import com.gigigo.orchextra.domain.entities.actions.types.ScanAction;
import com.gigigo.orchextra.domain.entities.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.entities.actions.types.WebViewAction;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;

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

    if (notification==null){
      this.notifFunctionality = new EmptyNotifFunctionalityImpl();
    }else{
      this.notifFunctionality = new NotifFunctionalityImpl(notification);
    }

  }

  public ActionType getActionType() {
    return actionType;
  }

  public void performAction(ActionDispatcher actionDispatcher) {
    if (notifFunctionality.isSupported()){
      performSimpleAction(actionDispatcher);
    }else{
      performNotifAction(actionDispatcher);
    }

  }

  public String getUrl() {
    return this.urlFunctionality.getUrl();
  }

  public Notification getNotifFunctionality() {
    return notifFunctionality.getNotification();
  }

  protected abstract void performSimpleAction(ActionDispatcher actionDispatcher);
  protected abstract void performNotifAction(ActionDispatcher actionDispatcher);

  public static class ActionBuilder {

    private ActionType actionType;
    private String url;
    private Notification notification;

    public ActionBuilder(ActionType actionType, String url, Notification notification) {
      this.actionType = actionType;
      this.url = url;
      this.notification = notification;
    }

    public BasicAction build() {
      switch (actionType){
        case BROWSER:
          return new BrowserAction(url, notification);
        case WEBVIEW:
          return new WebViewAction(url, notification);
        case SCAN:
          return new ScanAction(url, notification);
        case VUFORIA_SCAN:
          return new VuforiaScanAction(url, notification);
        case CUSTOM_SCHEME:
          return new CustomAction(url, notification);
        case NOTIFICATION:
          return new NotificationAction(url, notification);
        case NOT_DEFINED:
        default:
          return new EmptyAction(url, notification);
      }
    }
  }
}
