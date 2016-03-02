package com.gigigo.orchextra.domain.model.actions;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public interface ScheduledAction {
  boolean isCancelable();

  long getScheduleTime();

  String getId();

  String getEventId();

  void setEventId(String id);

  com.gigigo.orchextra.domain.model.actions.strategy.BasicAction getBasicAction();
}
