package com.gigigo.orchextra.domain.entities.actions.strategy;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/2/16.
 */
public class EmptyScheduleFunctionalityImpl implements ScheduleFunctionality{

  @Override public Schedule getSchedule() {
    throw new UnsupportedOperationException();
  }

  @Override public boolean isCancelable() {
    throw new UnsupportedOperationException();
  }

  @Override public long getScheduleTime() {
    throw new UnsupportedOperationException();
  }

  @Override public boolean isSupported() {
    return false;
  }
}
