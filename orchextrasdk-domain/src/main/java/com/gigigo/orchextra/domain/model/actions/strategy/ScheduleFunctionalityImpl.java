package com.gigigo.orchextra.domain.model.actions.strategy;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/2/16.
 */
public class ScheduleFunctionalityImpl implements ScheduleFunctionality {

  private final Schedule schedule;

  public ScheduleFunctionalityImpl(Schedule schedule) {
    this.schedule = schedule;
  }

  @Override public Schedule getSchedule() {
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
