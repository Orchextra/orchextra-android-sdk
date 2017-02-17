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

package com.gigigo.orchextra.sdk.background;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.gigigo.orchextra.di.injector.InjectorImpl;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusAccessor;
import com.gigigo.orchextra.sdk.OrchextraManager;
import com.gigigo.orchextra.domain.abstractions.background.BackgroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppStatusEventsListener;
import com.gigigo.orchextra.sdk.application.applifecycle.OrchextraActivityLifecycle;

import orchextra.javax.inject.Inject;

public class OrchextraBackgroundService extends Service {

  @Inject BackgroundTasksManager backgroundTasksManager;
  @Inject OrchextraActivityLifecycle orchextraActivityLifecycle;
  @Inject OrchextraStatusAccessor orchextraStatusAccessor;
  @Inject OrchextraLogger orchextraLogger;

  boolean mWasInjected = false;

  @Override public IBinder onBind(Intent arg0) {
    return null;
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {

    if (mWasInjected) {
      orchextraLogger.log("Service method :: onStartCommand");
      boolean requestConfig = shouldRequestConfig(intent);
      //refresh
      boolean refreshConfig = shouldRefreshConfig(intent);
      //restart Services
      boolean restartServices = shouldReStartServices(intent);
      //pause Services
      boolean pauseServices = shouldPauseServices(intent);

      if (pauseServices) {
        pauseBackgroundTasks();
        return START_STICKY;
      } else {
        if (restartServices) {
          restartBackgroundTasks();
          return START_STICKY;
        } else {
          if (orchextraStatusAccessor != null && orchextraStatusAccessor.isStarted()
              || refreshConfig) {
            if (refreshConfig) {
              startBackgroundTasks(refreshConfig);
            } else {
              startBackgroundTasks(requestConfig);
            }
            return START_STICKY;
          }
        }
      }
      return START_NOT_STICKY;
    } else {
      return START_NOT_STICKY;
    }
  }

  //region READ INTENT KIND OF ACTION
  private boolean shouldRefreshConfig(Intent intent) {
    if (intent != null) {
      return intent.getBooleanExtra(OrchextraBootBroadcastReceiver.REFRESH_CONFIG_ACTION, false);
    } else {
      return false;
    }
  }

  private boolean shouldRequestConfig(Intent intent) {
    if (intent != null) {
      return intent.getBooleanExtra(OrchextraBootBroadcastReceiver.BOOT_COMPLETED_ACTION, false);
    } else {
      return false;
    }
  }

  private boolean shouldReStartServices(Intent intent) {
    if (intent != null) {
      return intent.getBooleanExtra(OrchextraBootBroadcastReceiver.RESTART_SERVICES, false);
    } else {
      return false;
    }
  }

  private boolean shouldPauseServices(Intent intent) {
    if (intent != null) {
      return intent.getBooleanExtra(OrchextraBootBroadcastReceiver.PAUSE_SERVICES, false);
    } else {
      return false;
    }
  }
  //endregion

  private void startBackgroundTasks(boolean requestConfig) {
    if (mWasInjected) {
      AppStatusEventsListener appStatusEventsListener =
          orchextraActivityLifecycle.getAppStatusEventsListener();
      appStatusEventsListener.onServiceRecreated();
      backgroundTasksManager.startBackgroundTasks();

      if (requestConfig) {
        backgroundTasksManager.requestConfig();
      }
    }
  }

  private void restartBackgroundTasks() {
    if (mWasInjected) {
      AppStatusEventsListener appStatusEventsListener =
          orchextraActivityLifecycle.getAppStatusEventsListener();
      appStatusEventsListener.onServiceRecreated();
      backgroundTasksManager.reStartBackgroundTasks();
    }
  }

  private void pauseBackgroundTasks() {

    if (mWasInjected) backgroundTasksManager.pauseBackgroundTasks();
  }

  @Override public void onCreate() {
    super.onCreate();

    InjectorImpl injector = OrchextraManager.getInjector();
    if (injector != null) {
      injector.injectServiceComponent(this);
      orchextraLogger.log("Service method :: onCreate");
      mWasInjected = true;
    } else {
      mWasInjected = false;
    }
  }

  @Override public void onDestroy() {
    if (mWasInjected) {
      orchextraLogger.log("Service method :: onDestroy");
      backgroundTasksManager.finalizeBackgroundTasks();
    }
    super.onDestroy();
  }
}