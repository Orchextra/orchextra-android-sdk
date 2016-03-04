package com.gigigo.orchextra.sdk;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/3/16.
 */
public interface OrchextraTasksManager {
  void initBackgroundTasks();
  void initForegroundTasks();
  void stopAllTasks();
  void stopBackgroundServices();

  void stopForegroundTasks();
}
