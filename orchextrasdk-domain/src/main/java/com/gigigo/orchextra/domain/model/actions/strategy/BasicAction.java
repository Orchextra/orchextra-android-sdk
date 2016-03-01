package com.gigigo.orchextra.domain.model.actions.strategy;

import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.domain.model.actions.ScheduledAction;
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

  private String id;
  private String trackId;
  protected ActionType actionType;
  protected URLFunctionality urlFunctionality;
  protected NotifFunctionality notifFunctionality;
  protected ScheduleFunctionality scheduleFunctionality;

  private String eventCode;

  public BasicAction(String id, String trackId, String url, Notification notification, Schedule schedule) {

    this.id = id;
    this.trackId = trackId;

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

  public String getId() {
    return id;
  }

  public String getTrackId() {
    return trackId;
  }

  public void performAction(ActionDispatcher actionDispatcher) {
    if (notifFunctionality.isSupported()){
      notifFunctionality.getNotification().setShown(true);
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

  public static ScheduledAction generateCancelActionHolder(String id, boolean cancelable) {
    BasicAction action = new BasicAction.ActionBuilder()
        .actionId(id)
        .cancelable(cancelable)
        .actionType(ActionType.NOT_DEFINED)
        .build();
    return action.getScheduledAction();
  }

  public static class ActionBuilder {

    private String id;
    private String trackId;
    private ActionType actionType;
    private String url;
    private Notification notification;
    private Schedule schedule;

    public ActionBuilder(){}

    public ActionBuilder(String id, String tid, ActionType actionType, String url,
        Notification notification, Schedule schedule) {
      this(id, tid, actionType,url,notification);
      this.schedule = schedule;
    }

    public ActionBuilder(String id, String tid, ActionType actionType, String url,
        Notification notification) {

      this.id = id;
      this.trackId = tid;
      this.actionType = actionType;
      this.url = url;
      this.notification = notification;
    }

    public ActionBuilder actionId(String id) {
      this.id = id;
      return this;
    }

    public ActionBuilder actionType(ActionType actionType) {
      this.actionType = actionType;
      return this;
    }

    public ActionBuilder cancelable(boolean cancelable) {
      this.schedule = new Schedule(cancelable, 0);
      return this;
    }

    public BasicAction build() {
      switch (actionType){
        case BROWSER:
          return new BrowserAction(id, trackId, url, notification, schedule);
        case WEBVIEW:
          return new WebViewAction(id, trackId, url, notification, schedule);
        case SCAN:
          return new ScanAction(id, trackId, url, notification, schedule);
        case VUFORIA_SCAN:
          return new VuforiaScanAction(id, trackId, url, notification, schedule);
        case CUSTOM_SCHEME:
          return new CustomAction(id, trackId, url, notification, schedule);
        case NOTIFICATION:
          return new NotificationAction(id, trackId, url, notification, schedule);
        case NOT_DEFINED:
        default:
          return new EmptyAction(id, trackId, url, notification, schedule);
      }
    }

  }
}
