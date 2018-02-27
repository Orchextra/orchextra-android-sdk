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
import com.gigigo.orchextra.domain.abstractions.notifications.ForegroundNotificationBuilder;
import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.OrchextraNotification;
import com.gigigo.orchextra.sdk.application.applifecycle.OrchextraActivityLifecycle;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class ForegroundNotificationBuilderImpl implements ForegroundNotificationBuilder {

    private final ContextProvider contextProvider;

    private ActionDispatcherListener actionDispatcherListener;
    private BasicAction action;

    public ForegroundNotificationBuilderImpl(ContextProvider contextProvider) {
        this.contextProvider = contextProvider;
    }

    //this called by orchextra life cycle
    @Override public void buildNotification(BasicAction action, OrchextraNotification notification) {
        this.action = action;
        notification.setShown(true); //This prevent show and show again the dialog notification
        if (action.getActionType() == ActionType.NOTIFICATION) {
            buildAcceptDialog(notification);
        } else {
            buildTwoOptionsDialog(notification);
        }
    }

    //this call from NotificationBehavior, dispatch action in FOREGROUND
    @Override public void addBuildNotification(BasicAction action,
        OrchextraNotification notification) {
        OrchextraActivityLifecycle.setForegroundNotificationBuilder(this);
        OrchextraActivityLifecycle.addAndCheckOrchextraNotification(action, notification,
            contextProvider);
    }

    private void buildAcceptDialog(OrchextraNotification notification) {
        if (contextProvider.getCurrentActivity() != null) {
            NotificationActivity.Navigator.open(contextProvider.getApplicationContext(), notification,
                DialogType.ONE_BUTTON,
                new Function0<Unit>() {
                    @Override public Unit invoke() {
                        positiveButtonHandler();
                        return null;
                    }
                });
        }
    }

    private void buildTwoOptionsDialog(OrchextraNotification notification) {
        if (contextProvider.getCurrentActivity() != null) {
            NotificationActivity.Navigator.open(contextProvider.getApplicationContext(), notification,
                DialogType.TWO_BUTTON,
                new Function0<Unit>() {
                    @Override public Unit invoke() {
                        positiveButtonHandler();
                        return null;
                    }
                },
                new Function0<Unit>() {
                    @Override public Unit invoke() {
                        negativeButtonHandler();
                        return null;
                    }
                });
        }
    }

    @Override
    public void setActionDispatcherListener(ActionDispatcherListener actionDispatcherListener) {
        this.actionDispatcherListener = actionDispatcherListener;
    }

    private void positiveButtonHandler() {
        if (actionDispatcherListener != null && action != null) {
            actionDispatcherListener.onActionAccepted(action, true);
        }
    }

    private void negativeButtonHandler() {
        if (actionDispatcherListener != null && action != null) {
            actionDispatcherListener.onActionDismissed(action, true);
        }
    }
}
