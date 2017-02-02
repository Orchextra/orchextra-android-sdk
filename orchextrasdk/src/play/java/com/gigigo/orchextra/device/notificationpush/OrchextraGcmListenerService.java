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

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.gigigo.orchextra.R;
import com.gigigo.orchextra.device.actions.ActionRecovery;
import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.gigigo.orchextra.device.notifications.dtos.AndroidNotification;
import com.gigigo.orchextra.di.injector.InjectorImpl;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.sdk.OrchextraManager;
import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONObject;

import orchextra.javax.inject.Inject;

public class OrchextraGcmListenerService extends GcmListenerService {

  @Inject ActionRecovery actionRecovery;

  @Inject OrchextraLogger orchextraLogger;

  @Override public void onCreate() {
    super.onCreate();

    InjectorImpl injector = OrchextraManager.getInjector();
    if (injector != null) {
      injector.injectGcmListenerServiceComponent(this);
    }
  }

  @Override public void onMessageReceived(String from, Bundle data) {

    if (OrchextraManager.getInjector() != null) {
      orchextraLogger.log("Notification Push From: " + from);
      orchextraLogger.log("Notification Push Message: " + data.toString());

      String message = getMessageFromBundle(data);

      AndroidBasicAction androidBasicAction = generateAndroidNotification(message);

      actionRecovery.recoverAction(androidBasicAction);
    }
  }

  private String getMessageFromBundle(Bundle data) {
    try {

      String message = (String)data.get("title");
      //String msgOld = new JSONObject(data.getString("data")).getString("alert"); //
      //
      //if (msgOld != "") {
      //  return msgOld;
      //} else {
      return message;
      // }
    } catch (Exception e) {
      return "";
    }
  }

  @NonNull private AndroidBasicAction generateAndroidNotification(String message) {
    AndroidNotification androidNotification = new AndroidNotification();
    androidNotification.setTitle(getString(R.string.ox_notification_push_title));
    androidNotification.setBody(message);
    androidNotification.setShown(false);
    AndroidBasicAction androidBasicAction = new AndroidBasicAction();
    androidBasicAction.setNotification(androidNotification);
    androidBasicAction.setAction(ActionType.NOTIFICATION_PUSH.getStringValue());
    return androidBasicAction;
  }
}
