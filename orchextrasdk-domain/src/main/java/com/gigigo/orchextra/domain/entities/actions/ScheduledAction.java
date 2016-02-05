package com.gigigo.orchextra.domain.entities.actions;

import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;

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
  BasicAction getBasicAction();
}
