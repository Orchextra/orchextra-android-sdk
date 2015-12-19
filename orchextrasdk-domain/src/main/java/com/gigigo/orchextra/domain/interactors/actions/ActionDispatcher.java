package com.gigigo.orchextra.domain.interactors.actions;

import com.gigigo.orchextra.domain.entities.actions.types.CustomAction;
import com.gigigo.orchextra.domain.entities.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.entities.actions.types.NotificationAction;
import com.gigigo.orchextra.domain.entities.actions.types.ScanAction;
import com.gigigo.orchextra.domain.entities.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.entities.actions.types.WebViewAction;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 18/12/15.
 */
public interface ActionDispatcher {

  void dispatchAction(com.gigigo.orchextra.domain.entities.actions.types.BrowserAction action);
  void dispatchAction(com.gigigo.orchextra.domain.entities.actions.types.BrowserAction action, com.gigigo.orchextra.domain.entities.actions.strategy.Notification notification);
  void dispatchAction(com.gigigo.orchextra.domain.entities.actions.types.WebViewAction action);
  void dispatchAction(WebViewAction action, com.gigigo.orchextra.domain.entities.actions.strategy.Notification notification);
  void dispatchAction(com.gigigo.orchextra.domain.entities.actions.types.CustomAction action);
  void dispatchAction(CustomAction action, com.gigigo.orchextra.domain.entities.actions.strategy.Notification notification);
  void dispatchAction(com.gigigo.orchextra.domain.entities.actions.types.ScanAction action);
  void dispatchAction(ScanAction action, com.gigigo.orchextra.domain.entities.actions.strategy.Notification notification);
  void dispatchAction(com.gigigo.orchextra.domain.entities.actions.types.VuforiaScanAction action);
  void dispatchAction(VuforiaScanAction action, com.gigigo.orchextra.domain.entities.actions.strategy.Notification notification);
  void dispatchAction(com.gigigo.orchextra.domain.entities.actions.types.NotificationAction action);
  void dispatchAction(NotificationAction action, com.gigigo.orchextra.domain.entities.actions.strategy.Notification notification);
  void dispatchAction(com.gigigo.orchextra.domain.entities.actions.types.EmptyAction action);
  void dispatchAction(EmptyAction action, com.gigigo.orchextra.domain.entities.actions.strategy.Notification notification);

}
