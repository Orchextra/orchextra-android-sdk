package com.gigigo.orchextra.device.notifications;

import android.content.DialogInterface;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.R;
import com.gigigo.orchextra.domain.abstractions.notifications.ForegroundNotificationBuilder;
import com.gigigo.orchextra.ui.dialogs.DialogTwoOptions;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;
import com.gigigo.orchextra.domain.abstractions.actions.ActionDispatcherListener;

public class ForegroundNotificationBuilderImp implements ForegroundNotificationBuilder {

    private final ContextProvider context;

    private ActionDispatcherListener actionDispatcherListener;
    private BasicAction action;

    public ForegroundNotificationBuilderImp(ContextProvider context) {
        this.context = context;
    }

    @Override
    public void buildNotification(BasicAction action, Notification notification) {
        this.action = action;

        DialogTwoOptions dialog = new DialogTwoOptions(context.getCurrentActivity(), notification.getTitle(), notification.getBody(),
                context.getCurrentActivity().getString(R.string.ox_accept_text), positiveButtonListener,
                context.getCurrentActivity().getString(R.string.ox_cancel_text), negativeButtonListener);

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
