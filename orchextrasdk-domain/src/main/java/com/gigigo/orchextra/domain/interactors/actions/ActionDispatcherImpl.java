/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.domain.interactors.actions;

import com.gigigo.orchextra.domain.abstractions.actions.ActionDispatcherListener;
import com.gigigo.orchextra.domain.abstractions.actions.ActionExecution;
import com.gigigo.orchextra.domain.abstractions.notifications.NotificationBehavior;
import com.gigigo.orchextra.domain.abstractions.stats.StatsDispatcher;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.OrchextraNotification;
import com.gigigo.orchextra.domain.model.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.model.actions.types.CustomAction;
import com.gigigo.orchextra.domain.model.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.model.actions.types.NotificationAction;
import com.gigigo.orchextra.domain.model.actions.types.ScanAction;
import com.gigigo.orchextra.domain.model.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.model.actions.types.WebViewAction;

public class ActionDispatcherImpl implements ActionDispatcher {

  private final ActionExecution actionExecution;
  private final NotificationBehavior notificationBehavior;
  private final CustomSchemeReceiverContainer customSchemeReceiverContainer;
  private final StatsDispatcher statsDispatcher;

  public ActionDispatcherImpl(ActionExecution actionExecution,
      NotificationBehavior notificationBehavior,
      CustomSchemeReceiverContainer customSchemeReceiverContainer,
      StatsDispatcher statsDispatcher) {

    this.actionExecution = actionExecution;
    this.notificationBehavior = notificationBehavior;
    this.customSchemeReceiverContainer = customSchemeReceiverContainer;
    this.statsDispatcher = statsDispatcher;

    notificationBehavior.setActionDispatcherListener(actionDispatcherListener);
  }

  @Override public void dispatchAction(BrowserAction action) {
    actionExecution.execute(action);
    statsDispatcher.sendCompletedActionTrackId(action.getTrackId());
  }

  @Override public void dispatchAction(BrowserAction action, OrchextraNotification notification) {
    notificationBehavior.dispatchNotificationAction(action, notification);
  }

  @Override public void dispatchAction(WebViewAction action) {
    actionExecution.execute(action);
    statsDispatcher.sendCompletedActionTrackId(action.getTrackId());
  }

  @Override public void dispatchAction(WebViewAction action, OrchextraNotification notification) {
    notificationBehavior.dispatchNotificationAction(action, notification);
  }

  @Override public void dispatchAction(CustomAction action) {
    if (customSchemeReceiverContainer.getCustomSchemeReceiver() != null) {
      customSchemeReceiverContainer.getCustomSchemeReceiver().onReceive(action.getUrl());
    }
    statsDispatcher.sendCompletedActionTrackId(action.getTrackId());
  }

  @Override public void dispatchAction(CustomAction action, OrchextraNotification notification) {
    notificationBehavior.dispatchNotificationAction(action, notification);
  }

  @Override public void dispatchAction(ScanAction action) {
    actionExecution.execute(action);
    statsDispatcher.sendCompletedActionTrackId(action.getTrackId());
  }

  @Override public void dispatchAction(ScanAction action, OrchextraNotification notification) {
    notificationBehavior.dispatchNotificationAction(action, notification);
  }

  @Override public void dispatchAction(VuforiaScanAction action) {
    actionExecution.execute(action);
    statsDispatcher.sendCompletedActionTrackId(action.getTrackId());
  }

  @Override
  public void dispatchAction(VuforiaScanAction action, OrchextraNotification notification) {
    notificationBehavior.dispatchNotificationAction(action, notification);
  }

  @Override public void dispatchAction(NotificationAction action) {
    OrchextraNotification notification = action.getNotifFunctionality();
    if (notification.shouldBeShown()) {
      notificationBehavior.dispatchNotificationAction(action, notification);
    }
    statsDispatcher.sendCompletedActionTrackId(action.getTrackId());
  }

  @Override
  public void dispatchAction(NotificationAction action, OrchextraNotification notification) {
    notificationBehavior.dispatchNotificationAction(action, notification);
  }

  @Override public void dispatchAction(EmptyAction action) {
    //    statsDispatcher.sendCompletedActionTrackId(action.getTrackId());
  }

  @Override public void dispatchAction(EmptyAction action, OrchextraNotification notification) {
    //    notificationBehavior.dispatchNotificationAction(action, notification);
  }

  private ActionDispatcherListener actionDispatcherListener = new ActionDispatcherListener() {
    @Override public void onActionAccepted(BasicAction action, boolean isForeground) {
      action.performAction(ActionDispatcherImpl.this);
    }

    @Override public void onActionDismissed(BasicAction action, boolean isForeground) {

    }
  };
}
