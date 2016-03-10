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

import android.os.Bundle;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.sdk.OrchextraManager;
import com.gigigo.orchextra.device.actions.ActionRecovery;
import com.gigigo.orchextra.device.actions.scheduler.ActionsSchedulerGcmImpl;
import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.google.gson.Gson;
import javax.inject.Inject;


public class OrchextraGcmTaskService extends GcmTaskService {

  @Inject ActionRecovery actionRecovery;

  @Inject Gson gson;

  @Override
  public void onCreate() {
    super.onCreate();

    injectTaskServiceComponent();

  }

  private void injectTaskServiceComponent() {
    if (OrchextraManager.getInjector() != null) {
      OrchextraManager.getInjector().injectTaskServiceComponent(this);
    }
  }

  @Override public int onRunTask(TaskParams taskParams) {

    GGGLogImpl.log("Executing Scheduled action " + taskParams.getTag());

    try{
      Bundle extras = taskParams.getExtras();
      String stringAndroidBasicAction = extras.getString(ActionsSchedulerGcmImpl.BUNDLE_TASK_PARAM_NAME);
      AndroidBasicAction androidBasicAction = gson.fromJson(stringAndroidBasicAction, AndroidBasicAction.class);
      actionRecovery.recoverAction(androidBasicAction);
      GGGLogImpl.log("Scheduled action Executed and deleted " + taskParams.getTag());
      return GcmNetworkManager.RESULT_SUCCESS;
    }catch (Exception e){
      GGGLogImpl.log("Error retrieving Scheduled action", LogLevel.ERROR);
      return GcmNetworkManager.RESULT_FAILURE;
    }
  }

}
