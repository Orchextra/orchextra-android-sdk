package com.gigigo.orchextra.device.actions;

import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 5/2/16.
 */
public interface ViewActionDispatcher {
  void dispatchViewAction(AndroidBasicAction androidBasicAction);
}
