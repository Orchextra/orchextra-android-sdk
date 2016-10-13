package com.gigigo.orchextra.device.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gigigo.orchextra.di.injector.InjectorImpl;
import com.gigigo.orchextra.sdk.OrchextraManager;

import orchextra.javax.inject.Inject;

public class NotificationReceiver extends BroadcastReceiver {

    public static final String NOTIFICATION_BROADCAST_RECEIVER = "NOTIFICATION_BROADCAST_RECEIVER";
    public static final String ACTION_NOTIFICATION_BROADCAST_RECEIVER = "com.gigigo.imagerecognition.intent.action.NOTIFICATION_BROADCAST";

    @Inject
    NotificationDispatcher notificationDispatcher;

    @Override
    public void onReceive(Context context, Intent intent) {
        initDependencies();

        checkExtras(context, intent);
    }

    private void initDependencies() {
        InjectorImpl injector = OrchextraManager.getInjector();
        if (injector != null) {
            injector.injectNotificationBroadcastComponent(this);
        }
    }

    private void checkExtras(Context context, Intent intent) {
        if (OrchextraManager.getInjector() != null && intent != null &&
                intent.getExtras() != null &&
                intent.getExtras().containsKey(NOTIFICATION_BROADCAST_RECEIVER)) {

            notificationDispatcher.manageBackgroundNotification(intent);

            intent.removeExtra(NOTIFICATION_BROADCAST_RECEIVER);
            intent.removeExtra(AndroidNotificationBuilder.EXTRA_NOTIFICATION_ACTION);
        }
    }
}
