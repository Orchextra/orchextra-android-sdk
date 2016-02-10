package com.gigigo.orchextra.domain.model.actions.strategy;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 18/12/15.
 */
public class Schedule {

  private final boolean cancelable;
  private final long scheduledTime;
  private String eventId;

  public Schedule(boolean cancelable, long scheduledTime) {
    this.cancelable = cancelable;
    this.scheduledTime = scheduledTime;
  }

  public boolean isCancelable() {
    return cancelable;
  }

  public long getScheduledTime() {
    return scheduledTime;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  @Override public int hashCode() {
    return (cancelable ? 1 : 0);
  }
}
