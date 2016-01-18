package com.gigigo.orchextra;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import com.gigigo.orchextra.domain.entities.triggers.PhoneStatusType;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 18/1/16.
 */
public class OrchextraActivityLifecycle implements Application.ActivityLifecycleCallbacks{

  private Stack<ActivityLifecyleWrapper> activityStack = new Stack<>();

  public Activity getCurrentActivity() {

    Activity activity = lastForegroundActivity();

    if (activity!=null){
      return activity;
    }

    activity = lastPausedActivity();

    if (activity!=null){
      return activity;
    }

    if (activityStack.size()>0){
      activity = activityStack.peek().getActivity();

      if (activity!=null){
        return activity;
      }
    }

    return null;
  }

  public PhoneStatusType getPhoneStatusType(){
    Activity activity = lastPausedActivity();

    if (activity!=null){
      return PhoneStatusType.FOREGROUND;
    }else{
      return PhoneStatusType.BACKGROUND;
    }
  }

  //region Activity lifecycle Management

  @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    this.activityStack.push(new ActivityLifecyleWrapper(activity, true, true));
  }

  @Override public void onActivityStarted(Activity activity) {
    activityStack.peek().setIsStopped(false);
    cleanZombieWrappersAtStack();
  }

  @Override public void onActivityResumed(Activity activity) {
    //TODO Register for phase 2 shake detector
    activityStack.peek().setIsPaused(false);
  }

  @Override public void onActivityPaused(Activity activity) {
    //TODO Unregister for phase 2 shake detector
    activityStack.peek().setIsPaused(true);
  }


  @Override public void onActivityStopped(Activity activity) {
    activityStack.peek().setIsStopped(true);
  }

  @Override public void onActivityDestroyed(Activity activity) {
    this.activityStack.pop();
  }

  @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

  //endregion

  //region StackUtils

  //region last activities recovery
  private Activity lastPausedActivity() {
    Iterator<ActivityLifecyleWrapper> iter = activityStack.iterator();

    while (iter.hasNext()){
      ActivityLifecyleWrapper activityLifecyleWrapper = iter.next();
      if (!activityLifecyleWrapper.isStopped()){
        return activityLifecyleWrapper.getActivity();
      }
    }
    return null;
  }

  private Activity lastForegroundActivity() {

    Iterator<ActivityLifecyleWrapper> iter = activityStack.iterator();

    while (iter.hasNext()){
      ActivityLifecyleWrapper activityLifecyleWrapper = iter.next();
      if (!activityLifecyleWrapper.isPaused()){
        return activityLifecyleWrapper.getActivity();
      }
    }
    return null;
  }

  //endregion

  //region CleanStack

  private void cleanZombieWrappersAtStack() {

    Iterator<ActivityLifecyleWrapper> iter = activityStack.iterator();

    while (iter.hasNext()){
      ActivityLifecyleWrapper activityLifecyleWrapper = iter.next();
      if (isActivityDestroyed(activityLifecyleWrapper.getActivity())){
        iter.remove();
      }
    }
  }

  private boolean isActivityDestroyed(Activity activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
      return checkActivityDestroyedUnderV17(activity);
    }else{
      return checkActivityDestroyedV17(activity);
    }
  }

  private boolean checkActivityDestroyedUnderV17(Activity activity) {
    if (activity == null || activity.getBaseContext() == null){
      return true;
    }
    else return false;
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  private boolean checkActivityDestroyedV17(Activity activity) {
    if (activity == null || activity.isDestroyed()){
      return true;
    }
    else return false;
  }

  //endregion

  //endregion
}
