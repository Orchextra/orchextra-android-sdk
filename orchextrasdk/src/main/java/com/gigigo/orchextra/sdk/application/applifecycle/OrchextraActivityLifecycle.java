package com.gigigo.orchextra.sdk.application.applifecycle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import com.gigigo.gggjavalib.general.utils.ConsistencyUtils;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.device.notifications.NotificationDispatcher;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppStatusEventsListener;
import com.gigigo.orchextra.domain.abstractions.lifecycle.LifeCycleAccessor;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

/*
  * TODO: Dev note: would be nice refactor this class by splitting state and behavior
 */

/**
 * Created by Sergio Martinez Rodriguez
 * Date 18/1/16.
 */
public class OrchextraActivityLifecycle implements Application.ActivityLifecycleCallbacks,
    LifeCycleAccessor {

  private final NotificationDispatcher notificationDispatcher;
  private final AppStatusEventsListener appStatusEventsListener;

  private Stack<ActivityLifecyleWrapper> activityStack = new Stack<>();

  public OrchextraActivityLifecycle(AppStatusEventsListener listener,
                        NotificationDispatcher notificationDispatcher) {
    this.appStatusEventsListener = listener;
    this.notificationDispatcher = notificationDispatcher;
  }

  //region Activity lifecycle Management

  @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    ActivityLifecyleWrapper previousStackActivity = null;

    if (!activityStack.empty()){
      previousStackActivity = this.activityStack.peek();
    }

    this.activityStack.push(new ActivityLifecyleWrapper(activity, true, true));

    if (previousStackActivity!=null){
      previousStackActivity.cleanActivityReference();
    }

    notificationDispatcher.manageBackgroundNotification(activity);
  }

  @Override public void onActivityStarted(Activity activity) {
    try {
      boolean wasInBackground = endBackgroundModeIfNeeded();
      setCurrentStackActivityAsNotStopped();
      if (wasInBackground) {
        restoreActivityContext(activity);
        startForegroundMode();
      }
      cleanZombieWrappersAtStack();
    } catch (EmptyStackException e) {
      GGGLogImpl.log("OrchextraSDK init MUST be called in App onCreate, are you sure you did it? \n "
          + "Activity stack monitoring Exception was thrown. Trace: " +e.getMessage(), LogLevel.ERROR);
    }
  }

  private void startForegroundMode() {
    appStatusEventsListener.onForegroundStart();
  }

  private boolean endBackgroundModeIfNeeded() {
    //NOTE last paused activity == null means app is in background
    if (lastPausedActivity() == null) {
      appStatusEventsListener.onBackgroundEnd();
      return true;
    } else {
      return false;
    }
  }

  private void setCurrentStackActivityAsNotStopped() {
    activityStack.peek().setIsStopped(false);
  }

  @Override public void onActivityResumed(Activity activity) {
    try {
      ConsistencyUtils.checkNotEmpty(activityStack);
      ActivityLifecyleWrapper activityLifecyleWrapper = activityStack.peek();
      activityLifecyleWrapper.setIsPaused(false);
      restoreActivityContext(activity);
    } catch (Exception e) {
      GGGLogImpl.log("Exception :" + e.getMessage(), LogLevel.ERROR);
    }
  }

  private void restoreActivityContext(Activity activity) {
    ActivityLifecyleWrapper activityLifecyleWrapper = activityStack.peek();
    activityLifecyleWrapper.restoreActivityReference(activity);
  }

  @Override public void onActivityPaused(Activity activity) {
    try {
      ConsistencyUtils.checkNotEmpty(activityStack);
      activityStack.peek().setIsPaused(true);
    } catch (Exception e) {
      GGGLogImpl.log("Exception :" + e.getMessage(), LogLevel.ERROR);
    }
  }

  @Override public void onActivityStopped(Activity activity) {
    try {
      ConsistencyUtils.checkNotEmpty(activityStack);

      if (appWillGoToBackground()) {
        appStatusEventsListener.onForegroundEnd();
        activityStack.peek().cleanActivityReference();
      }
      setCurrentStackActivityAsStopped();
      setBackgroundModeIfNeeded();
    } catch (Exception e) {
      GGGLogImpl.log("Exception :" + e.getMessage(), LogLevel.ERROR);
    }
  }

  private void setCurrentStackActivityAsStopped() {
    activityStack.peek().setIsStopped(true);
  }

  private void setBackgroundModeIfNeeded() {
    //Note that when last paused activity is null means app is in background
    if (lastPausedActivity() == null) {
      appStatusEventsListener.onBackgroundStart();
    }
  }

  @Override public void onActivityDestroyed(Activity activity) {
    try {
      ConsistencyUtils.checkNotEmpty(activityStack);
      this.activityStack.pop();
    } catch (Exception e) {
      GGGLogImpl.log("Exception :" + e.getMessage(), LogLevel.ERROR);
    }
  }

  @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
  }

  //endregion

  //region StackUtils

  //region last activities recovery

  private boolean appWillGoToBackground() {
    Iterator<ActivityLifecyleWrapper> iter = activityStack.iterator();

    int i = 0;
    while (iter.hasNext()) {
      ActivityLifecyleWrapper activityLifecyleWrapper = iter.next();
      if (!activityLifecyleWrapper.isStopped()) {
        i++;
      }
    }
    return (i == 1);
  }

  public Activity lastPausedActivity() {

    for (ActivityLifecyleWrapper activityLifecyleWrapper : activityStack) {
      if (!activityLifecyleWrapper.isStopped()) {
        return activityLifecyleWrapper.getActivity();
      }
    }
    return null;
  }

  private Activity lastForegroundActivity() {

    for (ActivityLifecyleWrapper activityLifecyleWrapper : activityStack) {
      if (!activityLifecyleWrapper.isPaused()) {
        return activityLifecyleWrapper.getActivity();
      }
    }
    return null;
  }

  //endregion

  //region CleanStack

  private void cleanZombieWrappersAtStack() {

    Iterator<ActivityLifecyleWrapper> iter = activityStack.iterator();

    while (iter.hasNext()) {
      ActivityLifecyleWrapper activityLifecyleWrapper = iter.next();
      if (isActivityDestroyed(activityLifecyleWrapper.getActivity())) {
        iter.remove();
      }
    }
  }

  private boolean isActivityDestroyed(Activity activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      return checkActivityDestroyedUnderV17(activity);
    } else {
      return checkActivityDestroyedV17(activity);
    }
  }

  private boolean checkActivityDestroyedUnderV17(Activity activity) {
    return activity == null || activity.getBaseContext() == null;
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  private boolean checkActivityDestroyedV17(Activity activity) {
    return activity == null || activity.isDestroyed();
  }

  public AppStatusEventsListener getAppStatusEventsListener() {
    return appStatusEventsListener;
  }

  public Activity getCurrentActivity() {
    Activity activity = lastForegroundActivity();

    if (activity != null) {
      return activity;
    }

    activity = lastPausedActivity();

    if (activity != null) {
      return activity;
    }

    if (activityStack.size() > 0) {
      activity = activityStack.peek().getActivity();

      if (activity != null) {
        return activity;
      }
    }

    return null;
  }

  public boolean isActivityContextAvailable() {
    return (getCurrentActivity() != null);
  }

  @Override public boolean isInBackground() {
    return lastPausedActivity() == null;
  }

  //endregion

  //endregion
}