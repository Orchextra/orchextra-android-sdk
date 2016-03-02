package com.gigigo.orchextra.domain.interactors.actions;

import com.gigigo.orchextra.domain.model.actions.strategy.Notification;
import com.gigigo.orchextra.domain.model.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.model.actions.types.CustomAction;
import com.gigigo.orchextra.domain.model.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.model.actions.types.NotificationAction;
import com.gigigo.orchextra.domain.model.actions.types.ScanAction;
import com.gigigo.orchextra.domain.model.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.model.actions.types.WebViewAction;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 18/12/15.
 */
public interface ActionDispatcher {

  void dispatchAction(BrowserAction action);

  void dispatchAction(BrowserAction action, Notification notification);

  void dispatchAction(WebViewAction action);

  void dispatchAction(WebViewAction action, Notification notification);

  void dispatchAction(CustomAction action);

  void dispatchAction(CustomAction action, Notification notification);

  void dispatchAction(ScanAction action);

  void dispatchAction(ScanAction action, Notification notification);

  void dispatchAction(VuforiaScanAction action);

  void dispatchAction(VuforiaScanAction action, Notification notification);

  void dispatchAction(NotificationAction action);

  void dispatchAction(NotificationAction action, Notification notification);

  void dispatchAction(EmptyAction action);

  void dispatchAction(EmptyAction action, Notification notification);
}
