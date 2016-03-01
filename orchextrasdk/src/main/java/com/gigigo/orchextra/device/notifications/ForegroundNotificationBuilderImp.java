package com.gigigo.orchextra.device.notifications;

import android.content.DialogInterface;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.R;
import com.gigigo.orchextra.domain.abstractions.actions.ActionDispatcherListener;
import com.gigigo.orchextra.domain.abstractions.notifications.ForegroundNotificationBuilder;
import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;
import com.gigigo.orchextra.ui.dialogs.DialogOneOption;
import com.gigigo.orchextra.ui.dialogs.DialogTwoOptions;

public class ForegroundNotificationBuilderImp implements ForegroundNotificationBuilder {

    private final ContextProvider contextProvider;

    private ActionDispatcherListener actionDispatcherListener;
    private BasicAction action;

    public ForegroundNotificationBuilderImp(ContextProvider context) {
        this.contextProvider = context;
    }

    @Override
    public void buildNotification(BasicAction action, Notification notification) {
        this.action = action;

        if (action.getActionType() == ActionType.NOTIFICATION) {
            buildAcceptDialog(notification);
        } else {
            buildTwoOptionsDialog(notification);
        }
    }

    private void buildAcceptDialog(Notification notification) {
        DialogOneOption dialog = new DialogOneOption(contextProvider.getCurrentActivity(), notification.getTitle(), notification.getBody(),
                contextProvider.getCurrentActivity().getString(R.string.ox_accept_text), positiveButtonListener);

        dialog.onCreateDialog().show();
    }

    private void buildTwoOptionsDialog(Notification notification) {
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
