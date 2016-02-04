package com.gigigo.orchextra.android.applifecycle;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.gigigo.orchextra.android.service.OrchextraBackgroundService;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
public class AppStatusEventsListenerImpl implements AppStatusEventsListener {

  private final String TAG = "AppStatusEventsListener";
  private final Context context;
  private final ForegroundTasksManager foregroundTasksManager;

  public AppStatusEventsListenerImpl(Context context, ForegroundTasksManager foregroundTasksManager) {
    this.context = context;
    this.foregroundTasksManager = foregroundTasksManager;
  }

  public void setForegroundTasksManager(ForegroundTasksManager foregroundTasksManager) {
    //this.foregroundTasksManager = foregroundTasksManager;
  }

  @Override public void onBackgroundStart() {
    Log.d(TAG, "LOG :: App goes to backgroundmode ");
    startServices();
  }

  @Override public void onBackgroundEnd() {
    Log.d(TAG, "LOG :: App leaves background mode");
    stopServices();
  }

  private void startServices() {
    Intent intent= new Intent(context.getApplicationContext(), OrchextraBackgroundService.class);
    context.getApplicationContext().startService(intent);
  }

  @Override public void onForegroundStart() {
    //Stop Monitoring && startRanging
    Log.d(TAG, "LOG :: App Come to Foreground mode");
    startForegroundTasks();
  }

  @Override public void onForegroundEnd() {
    Log.d(TAG, "LOG :: App leaves Foreground mode");
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
