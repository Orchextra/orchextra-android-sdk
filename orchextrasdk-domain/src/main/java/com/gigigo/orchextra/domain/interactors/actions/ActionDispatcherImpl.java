package com.gigigo.orchextra.domain.interactors.actions;

import com.gigigo.orchextra.domain.entities.actions.types.CustomAction;
import com.gigigo.orchextra.domain.entities.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.entities.actions.types.NotificationAction;
import com.gigigo.orchextra.domain.entities.actions.types.ScanAction;
import com.gigigo.orchextra.domain.entities.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.entities.actions.types.WebViewAction;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 19/12/15.
 */
public class ActionDispatcherImpl implements ActionDispatcher {

  @Override public void dispatchAction(
      com.gigigo.orchextra.domain.entities.actions.types.BrowserAction action) {

  }

  @Override public void dispatchAction(
      com.gigigo.orchextra.domain.entities.actions.types.BrowserAction action, com.gigigo.orchextra.domain.entities.actions.strategy.Notification notification) {

  }

  @Override public void dispatchAction(
      com.gigigo.orchextra.domain.entities.actions.types.WebViewAction action) {

  }

  @Override public void dispatchAction(WebViewAction action, com.gigigo.orchextra.domain.entities.actions.strategy.Notification notification) {

  }

  @Override public void dispatchAction(
      com.gigigo.orchextra.domain.entities.actions.types.CustomAction action) {

  }

  @Override public void dispatchAction(CustomAction action, com.gigigo.orchextra.domain.entities.actions.strategy.Notification notification) {

  }

  @Override public void dispatchAction(
      com.gigigo.orchextra.domain.entities.actions.types.ScanAction action) {

  }

  @Override public void dispatchAction(ScanAction action, com.gigigo.orchextra.domain.entities.actions.strategy.Notification notification) {

  }

  @Override public void dispatchAction(
      com.gigigo.orchextra.domain.entities.actions.types.VuforiaScanAction action) {

  }

  @Override public void dispatchAction(VuforiaScanAction action, com.gigigo.orchextra.domain.entities.actions.strategy.Notification notification) {

  }

  @Override public void dispatchAction(
      com.gigigo.orchextra.domain.entities.actions.types.NotificationAction action) {

  }

  @Override public void dispatchAction(NotificationAction action, com.gigigo.orchextra.domain.entities.actions.strategy.Notification notification) {

  }

  @Override public void dispatchAction(
      com.gigigo.orchextra.domain.entities.actions.types.EmptyAction action) {

  }

  @Override public void dispatchAction(EmptyAction action, com.gigigo.orchextra.domain.entities.actions.strategy.Notification notification) {

  }

}
