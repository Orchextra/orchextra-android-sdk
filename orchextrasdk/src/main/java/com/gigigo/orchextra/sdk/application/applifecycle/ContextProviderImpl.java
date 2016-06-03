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

import android.app.Activity;
import android.content.Context;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;

public class ContextProviderImpl implements OrchextraContextProvider {

  private final Context context;
  private final OrchextraLogger orchextraLogger;

  private OrchextraActivityLifecycle orchextraActivityLifecycle;

  public ContextProviderImpl(Context context, OrchextraLogger orchextraLogger) {
    this.context = context;
    this.orchextraLogger = orchextraLogger;
  }

  public void setOrchextraActivityLifecycle(OrchextraActivityLifecycle orchextraActivityLifecycle) {
    this.orchextraActivityLifecycle = orchextraActivityLifecycle;
  }

  //region context provider interface
  @Override public Activity getCurrentActivity() {
    if (orchextraActivityLifecycle==null){
      orchextraLogger.log("Calling activity context before app finished initialization",
          OrchextraSDKLogLevel.WARN);
      return null;
    }
    return orchextraActivityLifecycle.getCurrentActivity();
  }

  @Override public boolean isActivityContextAvailable() {
    if (orchextraActivityLifecycle==null){
      return false;
    }
    //this implementation gives context of paused and stop activities
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
