package com.gigigo.orchextra.android.service;

import android.os.Bundle;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.android.ViewActionDispatcher;
import com.gigigo.orchextra.android.beacons.actions.ActionsSchedulerGcmImpl;
import com.gigigo.orchextra.android.entities.AndroidBasicAction;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import javax.inject.Inject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class OrchextraGcmTaskService extends GcmTaskService {

  @Inject ViewActionDispatcher viewActionDispatcher;

  @Override
  public void onCreate() {
    super.onCreate();
    Orchextra.getInjector().injectTaskServiceComponent(this);
  }

  @Override public int onRunTask(TaskParams taskParams) {
    try{
      Bundle extras = taskParams.getExtras();
      AndroidBasicAction androidBasicAction = (AndroidBasicAction) extras.get(ActionsSchedulerGcmImpl.BUNDLE_TASK_PARAM_NAME);
      viewActionDispatcher.dispatchViewAction(androidBasicAction);
      GGGLogImpl.log("Service method :: onCreate");
      return GcmNetworkManager.RESULT_SUCCESS;
    }catch (Exception e){
      GGGLogImpl.log("Service method :: onCreate", LogLevel.ERROR);
      return GcmNetworkManager.RESULT_FAILURE;
    }
  }

}
