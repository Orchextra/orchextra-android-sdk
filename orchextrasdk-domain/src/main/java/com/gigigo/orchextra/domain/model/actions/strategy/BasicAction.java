package com.gigigo.orchextra.domain.model.actions.strategy;

import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.domain.model.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.model.actions.types.CustomAction;
import com.gigigo.orchextra.domain.model.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.model.actions.types.NotificationAction;
import com.gigigo.orchextra.domain.model.actions.types.ScanAction;
import com.gigigo.orchextra.domain.model.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.model.actions.types.WebViewAction;

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

  public BasicAction(String url, Notification notification, Schedule schedule) {
    this.urlFunctionality = new URLFunctionalityImpl(url);

    if (notification==null){
      this.notifFunctionality = new EmptyNotifFunctionalityImpl();
    }else{
      this.notifFunctionality = new NotifFunctionalityImpl(notification);
    }

    if (schedule==null){
      this.scheduleFunctionality = new EmptyScheduleFunctionalityImpl();
    }else{
      this.scheduleFunctionality = new ScheduleFunctionalityImpl(schedule);
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

  public ScheduledActionImpl getScheduledAction() {
    if (scheduleFunctionality.isSupported()){
      return new ScheduledActionImpl(this);
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
    private Schedule schedule;

    public ActionBuilder(ActionType actionType, String url,
        Notification notification, Schedule schedule) {
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
          return new BrowserAction(url, notification, schedule);
        case WEBVIEW:
          return new WebViewAction(url, notification, schedule);
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
