package com.gigigo.orchextra.domain.interactors.actions;

import com.gigigo.orchextra.domain.abstractions.actions.ActionExecution;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;
import com.gigigo.orchextra.domain.model.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.model.actions.types.CustomAction;
import com.gigigo.orchextra.domain.model.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.model.actions.types.NotificationAction;
import com.gigigo.orchextra.domain.model.actions.types.ScanAction;
import com.gigigo.orchextra.domain.model.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.model.actions.types.WebViewAction;
import com.gigigo.orchextra.domain.abstractions.actions.ActionDispatcherListener;
import com.gigigo.orchextra.domain.abstractions.notifications.NotificationBehavior;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 19/12/15.
 */
public class ActionDispatcherImpl implements ActionDispatcher {

  private final ActionExecution actionExecution;
  private final NotificationBehavior notificationBehavior;
  //private  final CustomSchemeReceiver customSchemeReceiver;

  public ActionDispatcherImpl(ActionExecution actionExecution, NotificationBehavior notificationBehavior) {
      this.actionExecution = actionExecution;
      this.notificationBehavior = notificationBehavior;

      notificationBehavior.setActionDispatcherListener(actionDispatcherListener);
  }

  @Override public void dispatchAction(BrowserAction action) {
    actionExecution.execute(action);
  }

  @Override public void dispatchAction(BrowserAction action, Notification notification) {
    notificationBehavior.dispatchNotificationAction(action, notification);
    notification.setShown(true);
  }

  @Override public void dispatchAction(WebViewAction action) {
    actionExecution.execute(action);
  }

  @Override public void dispatchAction(WebViewAction action, Notification notification) {
    notificationBehavior.dispatchNotificationAction(action, notification);
    notification.setShown(true);
  }

  @Override public void dispatchAction(CustomAction action) {
    //TODO unncomment when moved class
    //customSchemeReceiver.onReceive(action.getUrl());
  }

  @Override public void dispatchAction(CustomAction action, Notification notification) {
    notificationBehavior.dispatchNotificationAction(action, notification);
    notification.setShown(true);
  }

  @Override public void dispatchAction(ScanAction action) {
    actionExecution.execute(action);
  }

  @Override public void dispatchAction(ScanAction action, Notification notification) {
    notificationBehavior.dispatchNotificationAction(action, notification);
    notification.setShown(true);
  }

  @Override public void dispatchAction(VuforiaScanAction action) {
    actionExecution.execute(action);
  }

  @Override public void dispatchAction(VuforiaScanAction action, Notification notification) {
    notificationBehavior.dispatchNotificationAction(action, notification);
    notification.setShown(true);
  }

  @Override public void dispatchAction(NotificationAction action) {
    Notification notification = action.getNotifFunctionality();
    notificationBehavior.dispatchNotificationAction(null, notification);
  }

  @Override public void dispatchAction(NotificationAction action, Notification notification) {
    notificationBehavior.dispatchNotificationAction(action, notification);
    notification.setShown(true);
  }

  @Override public void dispatchAction(EmptyAction action) {
  }

  @Override public void dispatchAction(EmptyAction action, Notification notification) {
    notificationBehavior.dispatchNotificationAction(action, notification);
    notification.setShown(true);
  }

    private ActionDispatcherListener actionDispatcherListener = new ActionDispatcherListener() {
        @Override
        public void onActionAccepted(BasicAction action, boolean isForeground) {
          action.performAction(ActionDispatcherImpl.this);
        }

        @Override
        public void onActionDismissed(BasicAction action, boolean isForeground) {

        }
    };
}
