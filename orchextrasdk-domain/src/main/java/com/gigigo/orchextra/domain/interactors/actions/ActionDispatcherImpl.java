package com.gigigo.orchextra.domain.interactors.actions;

import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.entities.actions.strategy.Notification;
import com.gigigo.orchextra.domain.entities.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.entities.actions.types.CustomAction;
import com.gigigo.orchextra.domain.entities.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.entities.actions.types.NotificationAction;
import com.gigigo.orchextra.domain.entities.actions.types.ScanAction;
import com.gigigo.orchextra.domain.entities.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.entities.actions.types.WebViewAction;
import com.gigigo.orchextra.domain.notifications.ActionDispatcherListener;
import com.gigigo.orchextra.domain.notifications.NotificationBehavior;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 19/12/15.
 */
public class ActionDispatcherImpl implements ActionDispatcher {

  private final ActionExecution actionExecution;
  private final NotificationBehavior notificationBehavior;

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
    actionExecution.execute(action);
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
    actionExecution.execute(action);
  }

  @Override public void dispatchAction(NotificationAction action, Notification notification) {
    notificationBehavior.dispatchNotificationAction(action, notification);
    notification.setShown(true);
  }

  @Override public void dispatchAction(EmptyAction action) {
    actionExecution.execute(action);
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
