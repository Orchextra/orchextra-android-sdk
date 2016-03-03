package com.gigigo.orchextra.domain.model.actions.strategy;

import com.gigigo.orchextra.domain.model.actions.ScheduledAction;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/2/16.
 */
public class ScheduledActionImpl implements ScheduledAction {

  private final BasicAction basicAction;
  private final ScheduleFunctionality scheduleFunctionality;

  public ScheduledActionImpl(BasicAction basicAction) {
    this.scheduleFunctionality = basicAction.scheduleFunctionality;
    this.basicAction = basicAction;
  }

  @Override public boolean isCancelable() {
    return scheduleFunctionality.isCancelable();
  }

  @Override public long getScheduleTime() {
    return scheduleFunctionality.getScheduleTime();
  }

  @Override public String getId() {
    return basicAction.getId();
  }

  @Override public String getEventId() {
    return scheduleFunctionality.getSchedule().getEventId();
  }

  @Override public void setEventId(String id) {
    scheduleFunctionality.getSchedule().setEventId(id);
  }

  @Override public BasicAction getBasicAction() {
    return basicAction;
  }
}
