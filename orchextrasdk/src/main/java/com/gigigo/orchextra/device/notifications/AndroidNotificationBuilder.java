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

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4ox.app.NotificationCompat;
import android.widget.RemoteViews;
import com.gigigo.ggglib.device.AndroidSdkVersion;
import com.gigigo.orchextra.R;
import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.OrchextraNotification;

public class AndroidNotificationBuilder {

  public static final String EXTRA_NOTIFICATION_ACTION = "OX_EXTRA_NOTIFICATION_ACTION";
  public static final String NOTIFICATION_ACTION_OX = "NOTIFICATION_ACTION_OX";
  public static final String HAVE_ACTIVITY_NOTIFICATION_OX = "HAVE_ACTIVITY_NOTIFICATION_OX";

  private static final String NOTIFICATION_OREO_ID = "NOTIFICATION_OREO_ID";

  private final Context context;

  public AndroidNotificationBuilder(Context context) {
    this.context = context;
  }

  public void createNotificationPush(OrchextraNotification orchextraNotification,
      PendingIntent pendingIntent) {
    createNotification(orchextraNotification, pendingIntent, true);
  }

  public void createNotification(OrchextraNotification orchextraNotification,
      PendingIntent pendingIntent) {
    createNotification(orchextraNotification, pendingIntent, false);
  }

  public PendingIntent getPendingIntent(AndroidBasicAction androidBasicAction) {
    Intent intent =
        new Intent(context, NotificationReceiver.class).setAction(NOTIFICATION_ACTION_OX)
            .putExtra(NotificationReceiver.NOTIFICATION_BROADCAST_RECEIVER,
                NotificationReceiver.NOTIFICATION_BROADCAST_RECEIVER)
            .putExtra(EXTRA_NOTIFICATION_ACTION, androidBasicAction)
            .putExtra(AndroidNotificationBuilder.HAVE_ACTIVITY_NOTIFICATION_OX, false)
            .setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

    return PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
  }

  private void createNotification(OrchextraNotification orchextraNotification,
      PendingIntent pendingIntent, boolean isPush) {
    Notification notification;

    if (orchextraNotification.isFake()) {
      orchextraNotification.setTitle(
          context.getResources().getString(R.string.ox_notification_default_title));
      orchextraNotification.setBody(
          context.getResources().getString(R.string.ox_notification_default_body));
    }

    if (AndroidSdkVersion.hasJellyBean16()) {
      notification = createBigNotification(orchextraNotification, pendingIntent, isPush);
    } else {
      notification = createNormalNotification(orchextraNotification, pendingIntent);
    }

    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    if (notificationManager != null) {

      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        NotificationChannel notificationChannel =
            new NotificationChannel(NOTIFICATION_OREO_ID, context.getPackageName(),
                NotificationManager.IMPORTANCE_DEFAULT);

        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setShowBadge(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        notificationManager.createNotificationChannel(notificationChannel);
      }

      //only 1 notification entry, for the same action notificationManager.notify((int) notificationId, notification);
      //one notification for action response, no care if the same notification, this is the first implementation
      notificationManager.notify((int) (System.currentTimeMillis() % Integer.MAX_VALUE),
          notification);
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  private Notification createBigNotification(OrchextraNotification orchextraNotification,
      PendingIntent pendingIntent, boolean isPush) {

    Bitmap largeIcon =
        BitmapFactory.decodeResource(context.getResources(), R.drawable.ox_notification_large_icon);

    Notification.Builder mNotifyBuilder;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      mNotifyBuilder = new Notification.Builder(context, NOTIFICATION_OREO_ID).setChannelId(
          NOTIFICATION_OREO_ID);
    } else {
      mNotifyBuilder = new Notification.Builder(context);
    }

    RemoteViews mContentView;
    if (isPush) {
      mContentView = new RemoteViews(this.context.getPackageName(),
          R.layout.ox_custom_normal_push_notification);
    } else {
      mContentView = new RemoteViews(this.context.getPackageName(),
          R.layout.ox_custom_normal_local_notification);
    }
    try {
      //images
      mContentView.setImageViewResource(R.id.ox_notifimage_custom_local_notification,
          R.drawable.ox_notification_large_icon);
      mContentView.setImageViewResource(R.id.ox_notifimage_small_custom_local_notification,
          R.drawable.ox_notification_color_small_icon);
      //title & body
      mContentView.setTextViewText(R.id.ox_notiftitle_custom_local_notification,
          orchextraNotification.getTitle());
      mContentView.setTextViewText(R.id.ox_notiftext_custom_local_notification,
          orchextraNotification.getBody());
      //time
      mContentView.setLong(R.id.time, "setTime", mNotifyBuilder.build().when);
      mContentView.setLong(R.id.ox_time_custom_local_notification, "setTime",
          mNotifyBuilder.build().when);
    } catch (Exception e) {
      if (isPush) {
        System.out.println("Something gone wrong with Notification. \n"
            + "You can't delete/change id of Views, in R.layout.ox_custom_normal_push_notification, use visibility gone instead");
      } else {
        System.out.println("Something gone wrong with Notification. \n"
            + "You can't delete/change id of Views, in R.layout.ox_custom_normal_local_notification, use visibility gone instead");
      }
    }

    Notification builder = mNotifyBuilder.setSmallIcon(R.drawable.ox_notification_alpha_small_icon)
        .setLargeIcon(largeIcon)
        .setContent(mContentView)
        .setContentIntent(pendingIntent)
        .setWhen(System.currentTimeMillis())
        .setAutoCancel(true)
        .build();

    //region big notification
    RemoteViews bigView;
    if (isPush) {
      bigView =
          new RemoteViews(this.context.getPackageName(), R.layout.ox_custom_big_push_notification);
    } else {
      bigView =
          new RemoteViews(this.context.getPackageName(), R.layout.ox_custom_big_local_notification);
    }

    try {
      //images
      bigView.setImageViewResource(R.id.ox_notifimage_custom_local_notification,
          R.drawable.ox_notification_large_icon);
      bigView.setImageViewResource(R.id.ox_notifimage_small_custom_local_notification,
          R.drawable.ox_notification_color_small_icon);
      //title & body
      bigView.setTextViewText(R.id.ox_notiftitle_custom_local_notification,
          orchextraNotification.getTitle());
      bigView.setTextViewText(R.id.ox_notiftext_custom_local_notification,
          orchextraNotification.getBody());
      //time
      bigView.setLong(R.id.time, "setTime", mNotifyBuilder.build().when);
      bigView.setLong(R.id.ox_time_custom_local_notification, "setTime",
          mNotifyBuilder.build().when);
    } catch (Exception e) {
      if (isPush) {
        System.out.println("Something gone wrong with Notification. \n"
            + "You can't delete/change id of Views, in R.layout.ox_custom_big_push_notification, use visibility gone instead");
      } else {
        System.out.println("Something gone wrong with Notification. \n"
            + "You can't delete/change id of Views, in R.layout.ox_custom_big_local_notification, use visibility gone instead");
      }
    }
    //endregion

    mNotifyBuilder.setContent(mContentView);
    builder.bigContentView = bigView;

    if (pendingIntent != null) {
      mNotifyBuilder.setContentIntent(pendingIntent);
    }

    return builder;
  }
  //region show notifications when  <Build.VERSION_CODES.JELLY_BEAN) (not Custom)

  private int getSmallIconResourceId() {
    return AndroidSdkVersion.hasLollipop21() ? R.drawable.ox_notification_alpha_small_icon
        : R.drawable.ox_notification_color_small_icon;
  }

  private Notification createNormalNotification(OrchextraNotification orchextraNotification,
      PendingIntent pendingIntent) {
    Bitmap largeIcon =
        BitmapFactory.decodeResource(context.getResources(), R.drawable.ox_notification_large_icon);
    Notification noti = new Notification();
    NotificationCompat.Builder builder;
    if (pendingIntent != null) {
      builder = new NotificationCompat.Builder(context).setLargeIcon(largeIcon)
          .setSmallIcon(getSmallIconResourceId())
          .setContentTitle(orchextraNotification.getTitle())
          .setContentText(orchextraNotification.getBody())
          .setTicker(orchextraNotification.getTitle())
          .setContentIntent(pendingIntent)
          .setWhen(System.currentTimeMillis())
          .setColor(context.getResources().getColor(R.color.ox_notification_background_color))
          .setAutoCancel(true);

      noti = builder.build();
    }
    return noti;
  }
  //endregion
}
