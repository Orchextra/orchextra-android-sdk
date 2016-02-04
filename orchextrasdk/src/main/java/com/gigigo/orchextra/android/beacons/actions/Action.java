package com.gigigo.orchextra.android.beacons.actions;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class Action implements ScheduledAction{

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
