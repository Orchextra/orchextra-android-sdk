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

package com.gigigo.orchextra.device.notifications;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.domain.abstractions.actions.ActionDispatcherListener;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.abstractions.notifications.ForegroundNotificationBuilder;
import com.gigigo.orchextra.domain.abstractions.notifications.NotificationBehavior;
import com.gigigo.orchextra.domain.abstractions.notifications.NotificationBuilder;
import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.OrchextraNotification;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;

public class NotificationBehaviorImpl implements NotificationBehavior {

  private final AppRunningMode appRunningMode;
  private final ForegroundNotificationBuilder foregroundNotificationBuilder;
  private final NotificationBuilder backgroundNotificationBuilder;
  private final ContextProvider contextProvider;

  private ActionDispatcherListener actionDispatcherListener;

  public NotificationBehaviorImpl(AppRunningMode appRunningMode,
      ForegroundNotificationBuilder foregroundNotificationBuilder,
      NotificationBuilder backgroundNotificationBuilder, ContextProvider contextProvider) {
    this.appRunningMode = appRunningMode;
    this.foregroundNotificationBuilder = foregroundNotificationBuilder;
    this.backgroundNotificationBuilder = backgroundNotificationBuilder;
    this.contextProvider = contextProvider;
  }

  @Override
  public void dispatchNotificationAction(BasicAction action, OrchextraNotification notification) {

    if (action.getActionType() != ActionType.NOTIFICATION_PUSH
        && appRunningMode.getRunningModeType() == AppRunningModeType.FOREGROUND) {

      if (contextProvider.getCurrentActivity() == null || contextProvider.getCurrentActivity()
          .isFinishing()) {
        //start activity transparent for show dialog, and finish it when dialog is close
      }

      foregroundNotificationBuilder.setActionDispatcherListener(actionDispatcherListener);
      foregroundNotificationBuilder.buildNotification(action, notification);
    } else {
      backgroundNotificationBuilder.buildNotification(action, notification);
    }
  }

  @Override
  public void setActionDispatcherListener(ActionDispatcherListener actionDispatcherListener) {
    this.actionDispatcherListener = actionDispatcherListener;
  }
}
