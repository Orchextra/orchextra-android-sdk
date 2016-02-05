package com.gigigo.orchextra.domain.entities.actions.strategy;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 18/12/15.
 */
public interface ScheduleFunctionality extends FunctionalitySupport {
  Schedule getSchedule();

  boolean isCancelable();

  long getScheduleTime();
}
