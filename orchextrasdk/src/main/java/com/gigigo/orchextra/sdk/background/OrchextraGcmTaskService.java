package com.gigigo.orchextra.sdk.background;

import android.os.Bundle;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.device.actions.ActionRecovery;
import com.gigigo.orchextra.device.actions.scheduler.ActionsSchedulerGcmImpl;
import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import javax.inject.Inject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class OrchextraGcmTaskService extends GcmTaskService {

  @Inject ActionRecovery actionRecovery;

  @Override
  public void onCreate() {
    super.onCreate();
    Orchextra.getInjector().injectTaskServiceComponent(this);
  }

  @Override public int onRunTask(TaskParams taskParams) {
    try{
      Bundle extras = taskParams.getExtras();
      AndroidBasicAction androidBasicAction = (AndroidBasicAction) extras.get(ActionsSchedulerGcmImpl.BUNDLE_TASK_PARAM_NAME);
      actionRecovery.recoverAction(androidBasicAction);
      GGGLogImpl.log("Service method :: onCreate");
      return GcmNetworkManager.RESULT_SUCCESS;
    }catch (Exception e){
      GGGLogImpl.log("Service method :: onCreate", LogLevel.ERROR);
      return GcmNetworkManager.RESULT_FAILURE;
    }
  }

}
