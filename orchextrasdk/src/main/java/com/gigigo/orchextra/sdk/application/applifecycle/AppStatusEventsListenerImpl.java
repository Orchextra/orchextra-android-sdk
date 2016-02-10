package com.gigigo.orchextra.sdk.application.applifecycle;

import android.content.Context;
import android.content.Intent;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.sdk.background.OrchextraBackgroundService;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppStatusEventsListener;
import com.gigigo.orchextra.domain.abstractions.foreground.ForegroundTasksManager;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
public class AppStatusEventsListenerImpl implements AppStatusEventsListener {

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
    GGGLogImpl.log("App goes to backgroundmode ");
    startServices();
  }

  @Override public void onBackgroundEnd() {
    GGGLogImpl.log("App leaves background mode");
    stopServices();
  }

  private void startServices() {
    Intent intent= new Intent(context.getApplicationContext(), OrchextraBackgroundService.class);
    context.getApplicationContext().startService(intent);
  }

  @Override public void onForegroundStart() {
    //Stop Monitoring && startRanging
    GGGLogImpl.log("App Come to Foreground mode");
    startForegroundTasks();
  }

  @Override public void onForegroundEnd() {
    GGGLogImpl.log("App leaves Foreground mode");
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
