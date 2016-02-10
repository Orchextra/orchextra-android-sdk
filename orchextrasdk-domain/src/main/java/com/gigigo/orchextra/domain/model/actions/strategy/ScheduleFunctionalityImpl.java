package com.gigigo.orchextra.domain.model.actions.strategy;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/2/16.
 */
public class ScheduleFunctionalityImpl implements ScheduleFunctionality {

  private final com.gigigo.orchextra.domain.model.actions.strategy.Schedule schedule;

  public ScheduleFunctionalityImpl(
      com.gigigo.orchextra.domain.model.actions.strategy.Schedule schedule) {
    this.schedule = schedule;
  }

  @Override public com.gigigo.orchextra.domain.model.actions.strategy.Schedule getSchedule() {
    return schedule;
  }

  @Override public boolean isCancelable() {
    return schedule.isCancelable();
  }

  @Override public long getScheduleTime() {
    return schedule.getScheduledTime();
  }

  @Override public boolean isSupported() {
    return true;
  }

}
