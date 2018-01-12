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

package com.gigigo.orchextra.sdk.application.applifecycle;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.foreground.ForegroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusAccessor;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppStatusEventsListener;
import com.gigigo.orchextra.sdk.background.OrchextraBackgroundService;

public class AppStatusEventsListenerImpl implements AppStatusEventsListener {

  private final Context context;
  private final ForegroundTasksManager foregroundTasksManager;
  private final OrchextraStatusAccessor orchextraStatusAccessor;
  private final OrchextraLogger orchextraLogger;

  public AppStatusEventsListenerImpl(Context context, ForegroundTasksManager foregroundTasksManager,
      OrchextraStatusAccessor orchextraStatusAccessor, OrchextraLogger orchextraLogger) {
    this.context = context;
    this.foregroundTasksManager = foregroundTasksManager;
    this.orchextraStatusAccessor = orchextraStatusAccessor;
    this.orchextraLogger = orchextraLogger;
  }

  @Override public void onBackgroundStart() {
    orchextraLogger.log("SdkVersionAppInfo goes to background mode ");
    if (orchextraStatusAccessor.isStarted()) {
      orchextraLogger.log("Ox está iniciado  goes to background mode ");
      startServices();
    }
  }

  @Override public void onBackgroundEnd() {
    orchextraLogger.log("SdkVersionAppInfo leaves background mode");
    stopServices();
  }

  private void startServices() {
    Intent intent = new Intent(context.getApplicationContext(), OrchextraBackgroundService.class);
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
      Log.e("", "startServices 1");
      context.getApplicationContext().startForegroundService(intent);
    } else {
      context.getApplicationContext().startService(intent);
    }
  }

  @Override public void onForegroundStart() {
    //Stop Monitoring && startRanging
    orchextraLogger.log("SdkVersionAppInfo Come to Foreground mode");
    if (orchextraStatusAccessor.isStarted()) {
      orchextraLogger.log("Ox está iniciado Come to Foreground mode");
      startForegroundTasks();
    }
  }

  @Override public void onForegroundEnd() {
    orchextraLogger.log("SdkVersionAppInfo leaves Foreground mode");
    foregroundTasksManager.finalizeForegroundTasks();
  }

  @Override public void onServiceRecreated() {
  }

  private void stopServices() {
    Log.e("", "startServices 10");
    if (Build.VERSION.SDK_INT < 26) {
      Intent intent = new Intent(context.getApplicationContext(), OrchextraBackgroundService.class);
      context.stopService(intent);
    }
    else
    {
      Log.e("", "startServices 10 oreo, not stop service until startforeground");
    }
  }

  private void startForegroundTasks() {
    foregroundTasksManager.startForegroundTasks();
  }
}
