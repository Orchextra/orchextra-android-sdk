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

package com.gigigo.orchextra.core.utils

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle

class ActivityLifecycleManager(app: Application,
    private val onActivityResumed: () -> Unit,
    private val onActivityPaused: () -> Unit) {

  init {
    app.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
      override fun onActivityPaused(activity: Activity?) {
        onActivityPaused()
      }

      override fun onActivityResumed(activity: Activity?) {
        onActivityResumed()
      }

      override fun onActivityStarted(activity: Activity?) {

      }

      override fun onActivityDestroyed(activity: Activity?) {

      }

      override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

      }

      override fun onActivityStopped(activity: Activity?) {

      }

      override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {

      }
    })
  }
}