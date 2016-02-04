package com.gigigo.orchextra.android.beacons.actions;

import java.util.Collections;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class ActionsSchedulerPersistorNullImpl implements ActionsSchedulerPersistor{

  @Override public ScheduledAction getScheduledActionWithId(String id) {
    return null;
  }

  @Override public void addAction(ScheduledAction action) {
    //Fake null impl
  }

  @Override public List<ScheduledAction> obtainAllPendingActions() {
    return Collections.emptyList();
  }

  @Override public void removeAction(ScheduledAction action) {
    //Fake null impl
  }
}
