package com.gigigo.orchextra.sdk.application.applifecycle;

import android.app.Activity;
import android.content.Context;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 29/1/16.
 */
public class ContextProviderImpl implements OrchextraContextProvider {

  private final Context context;

  private OrchextraActivityLifecycle orchextraActivityLifecycle;

  public ContextProviderImpl(Context context) {
    this.context = context;
  }

  public void setOrchextraActivityLifecycle(OrchextraActivityLifecycle orchextraActivityLifecycle) {
    this.orchextraActivityLifecycle = orchextraActivityLifecycle;
  }

  //region context provider interface
  @Override public Activity getCurrentActivity() {
    if (orchextraActivityLifecycle==null){
      GGGLogImpl.log("Calling activity context before app finished initialization", LogLevel.WARN);
      return null;
    }
    return orchextraActivityLifecycle.getCurrentActivity();
  }

  @Override public boolean isActivityContextAvailable() {
    if (orchextraActivityLifecycle==null){
      return false;
    }
    //this implementation gives context of paused and stopped activities
    return orchextraActivityLifecycle.isActivityContextAvailable();
  }

  @Override public Context getApplicationContext() {
    return context;
  }

  @Override public boolean isApplicationContextAvailable() {
    return (context != null);
  }

  //endregion

}
