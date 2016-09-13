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

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotationox.NonNull;
import android.support.annotationox.Nullable;

import com.gigigo.orchextra.R;
import com.gigigo.orchextra.device.actions.ActionRecovery;
import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.gigigo.orchextra.device.notifications.dtos.AndroidNotification;
import com.gigigo.orchextra.di.injector.InjectorImpl;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.sdk.OrchextraManager;
//import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONObject;

import orchextra.javax.inject.Inject;

public class OrchextraGcmListenerService extends Service //extends GcmListenerService {
{
  @Inject ActionRecovery actionRecovery;

  @Inject OrchextraLogger orchextraLogger;


  public void onCreate() {
    InjectorImpl injector = OrchextraManager.getInjector();
    if (injector != null) {
      injector.injectGcmListenerServiceComponent(this);
    }
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

}
