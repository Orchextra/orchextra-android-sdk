package com.gigigo.orchextra.device.notifications;

import android.app.Activity;
import android.content.Intent;
import com.gigigo.orchextra.device.actions.ActionRecovery;
import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public class NotificationDispatcherImpl implements NotificationDispatcher {

  private final ActionRecovery actionRecovery;

  public NotificationDispatcherImpl(ActionRecovery actionRecovery) {
  this.actionRecovery = actionRecovery;
  }

  @Override public void manageBackgroundNotification(Activity activity) {
    Intent intent = activity.getIntent();

    if (intent != null) {
      if (intent.hasExtra(AndroidNotificationBuilder.EXTRA_NOTIFICATION_ACTION)) {
        AndroidBasicAction androidBasicAction = intent.getParcelableExtra(AndroidNotificationBuilder.EXTRA_NOTIFICATION_ACTION);
        if (androidBasicAction != null) {
          actionRecovery.recoverAction(androidBasicAction);
          intent.removeExtra(AndroidNotificationBuilder.EXTRA_NOTIFICATION_ACTION);
        }
      }
    }
  }

}
