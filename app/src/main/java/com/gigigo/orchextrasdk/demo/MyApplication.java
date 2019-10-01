/*
 * Created by Orchextra
 *
 * Copyright (C) 2017 Gigigo Mobile Services SL
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

package com.gigigo.orchextrasdk.demo;

import androidx.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import io.fabric.sdk.android.Fabric;

public class MyApplication extends MultiDexApplication {

  public void onCreate() {
    super.onCreate();

    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return;
    }
    LeakCanary.install(this);
    Stetho.initializeWithDefaults(this);
    Fabric.with(this, new Crashlytics());
  }
}