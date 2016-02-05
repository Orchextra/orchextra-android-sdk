package com.gigigo.orchextra.android.beacons.actions.fakepersistor;

import com.gigigo.orchextra.domain.entities.actions.ScheduledAction;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class Action implements ScheduledAction {

  private boolean cancelable;
  private long scheduledTime;
  private String id;

  @Override public boolean isCancelable() {
    return cancelable;
  }

  @Override public long getScheduleTime() {
    return 0;
  }

  @Override public String getId() {
    return null;
  }

  @Override public String getEventId() {
    return null;
  }

  @Override public void setEventId(String id) {

  }

  @Override public BasicAction getBasicAction() {
    return null;
  }

  public void setCancelable(boolean cancelable) {
    this.cancelable = cancelable;
  }

  public long getScheduledTime() {
    return scheduledTime;
  }

  public void setScheduledTime(long scheduledTime) {
    this.scheduledTime = scheduledTime;
  }

  public void setId(String id) {
    this.id = id;
  }
}
