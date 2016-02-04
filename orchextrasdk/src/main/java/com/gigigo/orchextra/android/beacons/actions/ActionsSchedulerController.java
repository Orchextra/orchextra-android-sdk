package com.gigigo.orchextra.android.beacons.actions;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public interface ActionsSchedulerController {

  void cancelPendingActionWithId(String id, boolean forceCancel);

  void addAction(ScheduledAction action);

  void scheduleAllPendingActions();

  void cancelAllPendingActions(boolean forceCancel);

  void removeScheduledActionWithId(String id);

  void removeScheduledAction(ScheduledAction scheduledAction);
}
