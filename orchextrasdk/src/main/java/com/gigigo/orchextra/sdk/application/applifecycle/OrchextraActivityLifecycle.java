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

package com.gigigo.orchextra.sdk.application.applifecycle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import com.gigigo.gggjavalib.general.utils.ConsistencyUtils;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppStatusEventsListener;
import com.gigigo.orchextra.domain.abstractions.lifecycle.LifeCycleAccessor;

import java.util.Iterator;
import java.util.Stack;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) public class OrchextraActivityLifecycle
    implements Application.ActivityLifecycleCallbacks, LifeCycleAccessor {

  private final AppStatusEventsListener appStatusEventsListener;
  private final OrchextraLogger orchextraLogger;

  private Stack<ActivityLifecyleWrapper> activityStack = new Stack<>();

  public OrchextraActivityLifecycle(AppStatusEventsListener listener, OrchextraLogger orchextraLogger) {
    this.appStatusEventsListener = listener;
    this.orchextraLogger = orchextraLogger;
  }

  @Override public boolean isInBackground() {
    return activityStack.isEmpty();
  }

  @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
  }

  @Override public void onActivityStarted(Activity activity) {
    boolean wasInBackground = activityStack.empty();
    if (wasInBackground) {
      appStatusEventsListener.onBackgroundEnd();
    }

    this.activityStack.push(new ActivityLifecyleWrapper(activity, true, false));

    if (wasInBackground) {
      startForegroundMode();
    }
  }

  @Override public void onActivityResumed(Activity activity) {
    try {
      ConsistencyUtils.checkNotEmpty(activityStack);
      activityStack.peek().setIsPaused(false);
    } catch (Exception e) {
      orchextraLogger.log("Exception :" + e.getMessage(), OrchextraSDKLogLevel.ERROR);
    }
  }

  @Override public void onActivityPaused(Activity activity) {
    try {
      ConsistencyUtils.checkNotEmpty(activityStack);
      activityStack.peek().setIsPaused(true);
    } catch (Exception e) {
      orchextraLogger.log("Exception :" + e.getMessage(), OrchextraSDKLogLevel.ERROR);
    }
  }

  @Override public void onActivityStopped(Activity activity) {
    try {
      ConsistencyUtils.checkNotEmpty(activityStack);

      if (activityStack.size() == 1) {
        appStatusEventsListener.onForegroundEnd();
      }
      removeActivityFromStack(activity);
      setBackgroundModeIfNeeded();
    } catch (Exception e) {
      orchextraLogger.log("Exception :" + e.getMessage(), OrchextraSDKLogLevel.ERROR);
    }
  }

  @Override public void onActivityDestroyed(Activity activity) {
  }

  @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
  }

  public boolean isActivityContextAvailable() {
    return (getCurrentActivity() != null);
  }

  public AppStatusEventsListener getAppStatusEventsListener() {
    return appStatusEventsListener;
  }

  public Activity getCurrentActivity() {

    for (ActivityLifecyleWrapper activityLifecyleWrapper : activityStack) {
      if (!activityLifecyleWrapper.isPaused()) {
        return activityLifecyleWrapper.getActivity();
      }
    }

    for (ActivityLifecyleWrapper activityLifecyleWrapper : activityStack) {
      if (!activityLifecyleWrapper.isStopped()) {
        return activityLifecyleWrapper.getActivity();
      }
    }

    return null;
  }

  private void removeActivityFromStack(Activity activity) {
    Iterator<ActivityLifecyleWrapper> iter = activityStack.iterator();
    while (iter.hasNext()) {
      ActivityLifecyleWrapper activityLifecyleWrapper = iter.next();
      if (activityLifecyleWrapper.getActivity().equals(activity)) {
        //activityStack.remove(activityLifecyleWrapper);
        iter.remove();
      }
    }
  }

  private void setBackgroundModeIfNeeded() {
    if (activityStack.empty()) {
      appStatusEventsListener.onBackgroundStart();
    }
  }

  private void startForegroundMode() {
    appStatusEventsListener.onForegroundStart();
  }
  //todo notcomplete
  private void prinStatusOfStack(String s) {
    orchextraLogger.log("STACK Status :: " + s);
    orchextraLogger.log("STACK Status :: Elements in Stack :" + activityStack.size());
    for (int i = 0; i < activityStack.size(); i++) {
      orchextraLogger.log("STACK Status :: Activity " + i +
          " Stopped: " + activityStack.get(i).isStopped() +
          " Paused: " + activityStack.get(i).isPaused() +
          " Has Context: " + (activityStack.get(i).getActivity() != null) +
          " Activity Context: " + activityStack.get(i).getActivity());
    }

    orchextraLogger.log("STACK Status :: Lifecycle, Is app in Background: " + isInBackground());

    orchextraLogger.log("STACK Status :: isActivityContextAvailable: " + isActivityContextAvailable());

    orchextraLogger.log("STACK Status :: getCurrentActivity: " + getCurrentActivity());

    orchextraLogger.log("------------- STACK Status ---------------");
  }
}