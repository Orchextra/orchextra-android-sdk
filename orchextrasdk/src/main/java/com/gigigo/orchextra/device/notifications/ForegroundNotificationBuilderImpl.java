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

import android.content.DialogInterface;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.R;
import com.gigigo.orchextra.domain.abstractions.actions.ActionDispatcherListener;
import com.gigigo.orchextra.domain.abstractions.notifications.ForegroundNotificationBuilder;
import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.OrchextraNotification;
import com.gigigo.orchextra.ui.dialogs.DialogOneOption;
import com.gigigo.orchextra.ui.dialogs.DialogTwoOptions;

public class ForegroundNotificationBuilderImpl implements ForegroundNotificationBuilder {

    private final ContextProvider contextProvider;

    private ActionDispatcherListener actionDispatcherListener;
    private BasicAction action;

    public ForegroundNotificationBuilderImpl(ContextProvider contextProvider) {
        this.contextProvider = contextProvider;
    }

    @Override
    public void buildNotification(BasicAction action, OrchextraNotification notification) {
        this.action = action;
        notification.setShown(true); //This prevent show and show again the dialog notification
        if (action.getActionType() == ActionType.NOTIFICATION) {
            buildAcceptDialog(notification);
        } else {
            buildTwoOptionsDialog(notification);
        }
    }

    private void buildAcceptDialog(OrchextraNotification notification) {
        DialogOneOption dialog = new DialogOneOption(contextProvider.getCurrentActivity(), notification.getTitle(), notification.getBody(),
                contextProvider.getCurrentActivity().getString(R.string.ox_accept_text), positiveButtonListener);

        dialog.onCreateDialog().show();
    }

    private void buildTwoOptionsDialog(OrchextraNotification notification) {
        DialogTwoOptions dialog = new DialogTwoOptions(contextProvider.getCurrentActivity(), notification.getTitle(), notification.getBody(),
                contextProvider.getCurrentActivity().getString(R.string.ox_accept_text), positiveButtonListener,
                contextProvider.getCurrentActivity().getString(R.string.ox_cancel_text), negativeButtonListener);

        dialog.onCreateDialog().show();
    }


    @Override
    public void setActionDispatcherListener(ActionDispatcherListener actionDispatcherListener) {
        this.actionDispatcherListener = actionDispatcherListener;
    }

    private DialogInterface.OnClickListener positiveButtonListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (actionDispatcherListener != null && action != null) {
                actionDispatcherListener.onActionAccepted(action, true);
            }
        }
    };

    private DialogInterface.OnClickListener negativeButtonListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (actionDispatcherListener != null && action != null) {
                actionDispatcherListener.onActionDismissed(action, true);
            }
        }
    };
}
