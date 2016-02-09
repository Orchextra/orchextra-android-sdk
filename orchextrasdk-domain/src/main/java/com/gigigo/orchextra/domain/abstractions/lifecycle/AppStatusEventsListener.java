package com.gigigo.orchextra.domain.abstractions.lifecycle;

import com.gigigo.orchextra.domain.abstractions.foreground.ForegroundTasksManager;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
public interface AppStatusEventsListener {

  void onBackgroundStart();

  void onBackgroundEnd();

  void onForegroundStart();

  void onForegroundEnd();

  void onServiceRecreated();

  void setForegroundTasksManager(ForegroundTasksManager foregroundTasksManager);

}
