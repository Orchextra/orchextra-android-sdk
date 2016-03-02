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

  private void prinStatusOfStack(String s) {
    GGGLogImpl.log("STACK Status :: " + s);
    GGGLogImpl.log("STACK Status :: Elements in Stack :" + activityStack.size());
    for (int i = 0; i<activityStack.size(); i++){
      GGGLogImpl.log("STACK Status :: Activity " + i +
          " Stopped: " + activityStack.get(i).isStopped() +
          " Paused: " + activityStack.get(i).isPaused() +
          " Has Context: " + (activityStack.get(i).getActivity()!=null) +
          " Activity Context: " + activityStack.get(i).getActivity());
    }

    GGGLogImpl.log("STACK Status :: Lifecycle, Is app in Background: " + isInBackground());

    GGGLogImpl.log("STACK Status :: isActivityContextAvailable: " + isActivityContextAvailable());

    GGGLogImpl.log("STACK Status :: Last paused Activity: " + lastPausedActivity());

    GGGLogImpl.log("STACK Status :: getCurrentActivity: " + getCurrentActivity());

    GGGLogImpl.log("STACK Status :: AppWill go To background: " + appWillGoToBackground());

    GGGLogImpl.log("------------- STACK Status ---------------");
  }

  //region Activity lifecycle Management

  @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    prinStatusOfStack("Before onActivityCreated");
    this.activityStack.push(new ActivityLifecyleWrapper(activity, true, true));

    notificationDispatcher.manageBackgroundNotification(activity);
    prinStatusOfStack("After onActivityCreated");
  }

  @Override public void onActivityStarted(Activity activity) {
    prinStatusOfStack("Before onActivityStarted");
    try {
      boolean wasInBackground = endBackgroundModeIfNeeded();
      setCurrentStackActivityAsNotStopped();
      if (wasInBackground) {
        startForegroundMode();
      }
      cleanZombieWrappersAtStack();
    } catch (EmptyStackException e) {
      GGGLogImpl.log("OrchextraSDK init MUST be called in App onCreate, are you sure you did it? \n "
          + "Activity stack monitoring Exception was thrown. Trace: " +e.getMessage(), LogLevel.ERROR);
    }
    prinStatusOfStack("After onActivityStarted");
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
    prinStatusOfStack("Before onActivityResumed");
    try {
      ConsistencyUtils.checkNotEmpty(activityStack);
      activityStack.peek().setIsPaused(false);
    } catch (Exception e) {
      GGGLogImpl.log("Exception :" + e.getMessage(), LogLevel.ERROR);
    }
    prinStatusOfStack("After onActivityResumed");
  }

  @Override public void onActivityPaused(Activity activity) {
    prinStatusOfStack("Before onActivityPaused");
    try {
      ConsistencyUtils.checkNotEmpty(activityStack);
      activityStack.peek().setIsPaused(true);
    } catch (Exception e) {
      GGGLogImpl.log("Exception :" + e.getMessage(), LogLevel.ERROR);
    }
    prinStatusOfStack("After onActivityPaused");
  }

  @Override public void onActivityStopped(Activity activity) {
    prinStatusOfStack("Before onActivityStopped");
    try {
      ConsistencyUtils.checkNotEmpty(activityStack);

      if (appWillGoToBackground()) {
        appStatusEventsListener.onForegroundEnd();
      }
      setCurrentStackActivityAsStopped();
      setBackgroundModeIfNeeded();
    } catch (Exception e) {
      GGGLogImpl.log("Exception :" + e.getMessage(), LogLevel.ERROR);
    }
    prinStatusOfStack("After onActivityStopped");
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
    prinStatusOfStack("Before onActivityDestroyed");
    try {
      ConsistencyUtils.checkNotEmpty(activityStack);
      this.activityStack.pop();
    } catch (Exception e) {
      GGGLogImpl.log("Exception :" + e.getMessage(), LogLevel.ERROR);
    }
    prinStatusOfStack("After onActivityDestroyed");
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

  private Activity lastPausedActivity() {

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