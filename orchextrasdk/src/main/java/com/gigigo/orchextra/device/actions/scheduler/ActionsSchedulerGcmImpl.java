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

package com.gigigo.orchextra.device.actions.scheduler;

import android.content.Context;
import android.os.Bundle;

import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.device.permissions.GoogleApiPermissionChecker;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsScheduler;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.model.actions.ScheduledAction;
import com.gigigo.orchextra.sdk.background.OrchextraGcmTaskService;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.Task;
import com.google.gson.Gson;

import java.util.Date;


public class ActionsSchedulerGcmImpl implements ActionsScheduler {

  private static final long DEFAULT_DELAY_MAX = 3;
  public static final String BUNDLE_TASK_PARAM_NAME = "TASK";
  private static final long ONE_SECOND = 1000;

  private final GcmNetworkManager gcmNetworkManager;
  private final AndroidBasicActionMapper androidBasicActionMapper;
  private final Gson gson;
  private final OrchextraLogger orchextraLogger;

  public ActionsSchedulerGcmImpl(Context context, Gson gson,
                                 AndroidBasicActionMapper androidBasicActionMapper,
                                 GoogleApiPermissionChecker googleApiPermissionChecker,
                                 OrchextraLogger orchextraLogger) {

    googleApiPermissionChecker.checkPlayServicesStatus();

    this.gcmNetworkManager = GcmNetworkManager.getInstance(context);
    this.androidBasicActionMapper = androidBasicActionMapper;
    this.gson = gson;
    this.orchextraLogger = orchextraLogger;
  }

  @Override public void scheduleAction(ScheduledAction action) {

    AndroidBasicAction androidBasicAction = androidBasicActionMapper.modelToExternalClass(
        action.getBasicAction());

    Bundle bundle = new Bundle();
    bundle.putString(BUNDLE_TASK_PARAM_NAME, gson.toJson(androidBasicAction));

    OneoffTask task = new OneoffTask.Builder()
        .setService(OrchextraGcmTaskService.class)
        .setTag(action.getId())
        .setExecutionWindow(action.getScheduleTime()/ONE_SECOND, (action.getScheduleTime()/ONE_SECOND)+DEFAULT_DELAY_MAX)
        .setPersisted(true)
        .setUpdateCurrent(true)
        .setRequiresCharging(false)
        .setRequiredNetwork(Task.NETWORK_STATE_ANY)
        .setExtras(bundle)
        .build();

    logShowTime(action);

    orchextraLogger.log("Scheduled action "+ action.getId());

    gcmNetworkManager.schedule(task);
  }

  @Override public void cancelAction(ScheduledAction action) {
    orchextraLogger.log("Canceled Scheduled action "+ action.getId());
    gcmNetworkManager.cancelTask(action.getId(), OrchextraGcmTaskService.class);
  }

  @Override public boolean hasPersistence() {
    return true;
  }

  public long logShowTime(ScheduledAction action) {
    long time = System.currentTimeMillis() + action.getScheduleTime();
    orchextraLogger.log("Scheduled Notification will be shown at minimum " + new Date(time).toString() + " max " + new Date(time+(DEFAULT_DELAY_MAX*ONE_SECOND)).toString() );
    return time;
  }
}
