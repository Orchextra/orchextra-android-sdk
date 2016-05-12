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

package com.gigigo.orchextra.device.notificationpush;

import android.content.Context;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.R;
import com.gigigo.orchextra.domain.abstractions.notificationpush.GcmInstanceIdRegister;
import com.gigigo.orchextra.domain.model.vo.NotificationPush;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class GcmInstanceIdRegisterImp implements GcmInstanceIdRegister {

  private final Context context;

  public GcmInstanceIdRegisterImp(Context context) {
    this.context = context;
  }

  @Override
  public NotificationPush getGcmNotification() {
    try {
      NotificationPush notificationPush = new NotificationPush();

      InstanceID instanceID = InstanceID.getInstance(context);
      String senderId = obtainSenderID();
      String token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

      GGGLogImpl.log("GCM Registration Token: " + token);

      notificationPush.setToken(token);
      notificationPush.setSenderId(senderId);

      return notificationPush;
    } catch (Exception e) {
      GGGLogImpl.log("Failed GCM Registration");
      return null;
    }
  }

  private String obtainSenderID() {

    String senderID = context.getString(R.string.ox_notifications_GCM_sender_id);

    if (senderID.equals(context.getString(R.string.ox_notifications_demo_GCM_sender_id))){

      GGGLogImpl.log("Push Notifications won't work "
          + "ox_notifications_GCM_sender_id string value not found", LogLevel.ERROR);

      throw new IllegalArgumentException("Push Notifications won't work "
          + "ox_notifications_GCM_sender_id string value not found");

    }
      return senderID;

  }

}
