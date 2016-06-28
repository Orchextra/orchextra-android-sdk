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
package gigigo.com.orchextrasdk;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.OrchextraCompletionCallback;
import com.gigigo.orchextra.OrchextraLogLevel;

public class App extends Application implements OrchextraCompletionCallback {
//internal demo
  public static final String API_KEY = "338d65a6572be208f25a9a5815861543adaa4abb";
  public static final String API_SECRET = "b29dac01598f9d8e2102aef73ac816c0786843ef";

  @Override public void onCreate() {
    super.onCreate();
    Orchextra.setLogLevel(OrchextraLogLevel.ALL);
    Log.d("APP", "Hello Application, start onCreate");
    Orchextra.init(App.this, App.this);
    Log.d("APP", "Hello Application, end onCreate");
  }

  @Override public void onSuccess() {
    Log.d("APP", "Hello Application, end onCreate");
  }

  @Override public void onError(String s) {
    Log.d("APP", "onError"+ s);
  }

  @Override public void onInit(String s) {
    callStart();
    Log.d("APP", "onInit"+ s);
  }

  private void callStart() {
    new Handler().post(new Runnable() {
      @Override public void run() {
        Orchextra.start(API_KEY, API_SECRET);
      }
    });
  }
}
