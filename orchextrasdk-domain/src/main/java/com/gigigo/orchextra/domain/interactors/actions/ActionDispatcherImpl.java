package com.gigigo.orchextra.domain.interactors.actions;

import com.gigigo.orchextra.domain.entities.actions.strategy.Notification;
import com.gigigo.orchextra.domain.entities.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.entities.actions.types.CustomAction;
import com.gigigo.orchextra.domain.entities.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.entities.actions.types.NotificationAction;
import com.gigigo.orchextra.domain.entities.actions.types.ScanAction;
import com.gigigo.orchextra.domain.entities.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.entities.actions.types.WebViewAction;
import com.gigigo.orchextra.domain.notifications.NotificationBehavior;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 19/12/15.
 */
public class ActionDispatcherImpl implements ActionDispatcher {

  private final NotificationBehavior notificationBehavior;

  public ActionDispatcherImpl(NotificationBehavior notificationBehavior) {
      this.notificationBehavior = notificationBehavior;
  }

  @Override public void dispatchAction(BrowserAction action) {

  }

  @Override public void dispatchAction(BrowserAction action, Notification notification) {
      notificationBehavior.dispatchNotificationAction(action, notification);
      notification.setShown(true);
  }

  @Override public void dispatchAction(WebViewAction action) {

  }

  @Override public void dispatchAction(WebViewAction action, Notification notification) {

  }

  @Override public void dispatchAction(CustomAction action) {

  }

  @Override public void dispatchAction(CustomAction action, Notification notification) {

  }

  @Override public void dispatchAction(ScanAction action) {

  }

  @Override public void dispatchAction(ScanAction action, Notification notification) {

  }

  @Override public void dispatchAction(VuforiaScanAction action) {

  }

  @Override public void dispatchAction(VuforiaScanAction action, Notification notification) {

  }

  @Override public void dispatchAction(NotificationAction action) {

  }

  @Override public void dispatchAction(NotificationAction action, Notification notification) {

  }

  @Override public void dispatchAction(EmptyAction action) {

  }

  @Override public void dispatchAction(EmptyAction action, Notification notification) {

  }
}
