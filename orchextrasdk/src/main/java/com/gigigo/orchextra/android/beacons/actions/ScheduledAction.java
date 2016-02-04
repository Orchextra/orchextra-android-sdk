package com.gigigo.orchextra.android.beacons.actions;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public interface ScheduledAction {
  boolean isCancelable();
  long getScheduleTime();
  String getId();
}
