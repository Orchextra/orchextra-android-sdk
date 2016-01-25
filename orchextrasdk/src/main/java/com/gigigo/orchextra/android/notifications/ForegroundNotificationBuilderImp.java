package com.gigigo.orchextra.android.notifications;

import android.content.Context;
import android.content.DialogInterface;

import com.gigigo.orchextra.R;
import com.gigigo.orchextra.android.dialogs.DialogTwoOptions;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.entities.actions.strategy.Notification;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.notifications.NotificationBuilder;

public class ForegroundNotificationBuilderImp implements NotificationBuilder {

    private final Context context;
    private final ActionDispatcher actionDispatcher;
    private BasicAction action;

    public ForegroundNotificationBuilderImp(Context context, ActionDispatcher actionDispatcher) {
        this.context = context;
        this.actionDispatcher = actionDispatcher;
    }

    @Override
    public void buildNotification(BasicAction action, Notification notification) {
        this.action = action;

        DialogTwoOptions dialog = new DialogTwoOptions(context, notification.getTitle(), notification.getBody(),
                context.getString(R.string.orc_accept_text), positiveButtonListener,
                context.getString(R.string.orc_cancel_text), null);

        dialog.onCreateDialog().show();
    }

    private DialogInterface.OnClickListener positiveButtonListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            action.performAction(actionDispatcher);
        }
    };
}
