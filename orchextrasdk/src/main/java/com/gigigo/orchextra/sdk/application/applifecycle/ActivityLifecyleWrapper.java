package com.gigigo.orchextra.sdk.application.applifecycle;

import android.app.Activity;
import java.lang.ref.WeakReference;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 18/1/16.
 */
public class ActivityLifecyleWrapper {
  private WeakReference<Activity> activity;
  private boolean isPaused;
  private boolean isStopped;

  public ActivityLifecyleWrapper(Activity activity, boolean isPaused, boolean isStopped) {
    this.activity = new WeakReference<>(activity);
    this.isPaused = isPaused;
    this.isStopped = isStopped;
  }

  public Activity getActivity() {
    return activity.get();
  }

  public void cleanActivityReference() {
    activity = null;
  }

  public boolean isPaused() {
    return isPaused;
  }

  public void setIsPaused(boolean isPaused) {
    this.isPaused = isPaused;
  }

  public boolean isStopped() {
    return isStopped;
  }

  public void setIsStopped(boolean isStopped) {
    this.isStopped = isStopped;
  }

  public void restoreActivityReference(Activity activity) {
    this.activity = new WeakReference<>(activity);
  }
}
