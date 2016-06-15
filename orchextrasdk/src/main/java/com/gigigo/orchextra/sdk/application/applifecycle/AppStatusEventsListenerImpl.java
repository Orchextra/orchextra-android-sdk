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
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusAccessor;
import com.gigigo.orchextra.sdk.background.OrchextraBackgroundService;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppStatusEventsListener;
import com.gigigo.orchextra.domain.abstractions.foreground.ForegroundTasksManager;


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
    orchextraLogger.log("App goes to background mode ");
    if (orchextraStatusAccessor.isStarted()){
      startServices();
    }
  }

  @Override public void onBackgroundEnd() {
    orchextraLogger.log("App leaves background mode");
    stopServices();
  }

  private void startServices() {
    Intent intent= new Intent(context.getApplicationContext(), OrchextraBackgroundService.class);
    context.getApplicationContext().startService(intent);
  }

  @Override public void onForegroundStart() {
    //Stop Monitoring && startRanging
    orchextraLogger.log("App Come to Foreground mode");
    if (orchextraStatusAccessor.isStarted()){
      startForegroundTasks();
    }
  }

  @Override public void onForegroundEnd() {
    orchextraLogger.log("App leaves Foreground mode");
    foregroundTasksManager.finalizeForegroundTasks();
  }

  @Override public void onServiceRecreated() {}

  private void stopServices() {
    Intent intent= new Intent(context.getApplicationContext(), OrchextraBackgroundService.class);
    context.stopService(intent);
  }

  private void startForegroundTasks() {
    foregroundTasksManager.startForegroundTasks();
  }


}
