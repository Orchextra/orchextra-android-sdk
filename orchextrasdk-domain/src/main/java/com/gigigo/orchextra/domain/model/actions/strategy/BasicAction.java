package com.gigigo.orchextra.domain.model.actions.strategy;

import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.domain.model.actions.types.CustomAction;
import com.gigigo.orchextra.domain.model.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.model.actions.types.NotificationAction;
import com.gigigo.orchextra.domain.model.actions.types.ScanAction;
import com.gigigo.orchextra.domain.model.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 18/12/15.
 */
public abstract class BasicAction{

  protected ActionType actionType;
  protected URLFunctionality urlFunctionality;
  protected NotifFunctionality notifFunctionality;
  protected ScheduleFunctionality scheduleFunctionality;

  private String eventCode;

  public BasicAction(String url, Notification notification, com.gigigo.orchextra.domain.model.actions.strategy.Schedule schedule) {
    this.urlFunctionality = new com.gigigo.orchextra.domain.model.actions.strategy.URLFunctionalityImpl(url);

    if (notification==null){
      this.notifFunctionality = new com.gigigo.orchextra.domain.model.actions.strategy.EmptyNotifFunctionalityImpl();
    }else{
      this.notifFunctionality = new com.gigigo.orchextra.domain.model.actions.strategy.NotifFunctionalityImpl(notification);
    }

    if (schedule==null){
      this.scheduleFunctionality = new com.gigigo.orchextra.domain.model.actions.strategy.EmptyScheduleFunctionalityImpl();
    }else{
      this.scheduleFunctionality = new com.gigigo.orchextra.domain.model.actions.strategy.ScheduleFunctionalityImpl(schedule);
    }

  }

  public ActionType getActionType() {
    return actionType;
  }

  public void performAction(ActionDispatcher actionDispatcher) {
    if (notifFunctionality.isSupported()){
      performNotifAction(actionDispatcher);
    }else{
      performSimpleAction(actionDispatcher);
    }

  }

  public boolean isScheduled() {
    return scheduleFunctionality.isSupported();
  }

  public com.gigigo.orchextra.domain.model.actions.strategy.ScheduledActionImpl getScheduledAction() {
    if (scheduleFunctionality.isSupported()){
      return new com.gigigo.orchextra.domain.model.actions.strategy.ScheduledActionImpl(this);
    }else{
      throw new UnsupportedOperationException();
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

  public String getEventCode() {
    return eventCode;
  }

  public void setEventCode(String eventCode) {
    this.eventCode = eventCode;
  }

  public static class ActionBuilder {

    private ActionType actionType;
    private String url;
    private Notification notification;
    private com.gigigo.orchextra.domain.model.actions.strategy.Schedule schedule;

    public ActionBuilder(ActionType actionType, String url,
        Notification notification, com.gigigo.orchextra.domain.model.actions.strategy.Schedule schedule) {
      this(actionType,url,notification);
      this.schedule = schedule;
    }

    public ActionBuilder(ActionType actionType, String url,
        Notification notification) {

      this.actionType = actionType;
      this.url = url;
      this.notification = notification;
    }

    public BasicAction build() {
      switch (actionType){
        case BROWSER:
          return new com.gigigo.orchextra.domain.model.actions.types.BrowserAction(url, notification, schedule);
        case WEBVIEW:
          return new com.gigigo.orchextra.domain.model.actions.types.WebViewAction(url, notification, schedule);
        case SCAN:
          return new ScanAction(url, notification, schedule);
        case VUFORIA_SCAN:
          return new VuforiaScanAction(url, notification, schedule);
        case CUSTOM_SCHEME:
          return new CustomAction(url, notification, schedule);
        case NOTIFICATION:
          return new NotificationAction(url, notification, schedule);
        case NOT_DEFINED:
        default:
          return new EmptyAction(url, notification, schedule);
      }
    }
  }
}
