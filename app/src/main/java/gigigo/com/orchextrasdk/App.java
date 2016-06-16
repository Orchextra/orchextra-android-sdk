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

  public static final String API_KEY = "34a4654b9804eab82aae05b2a5f949eb2a9f412c";
  public static final String API_SECRET = "2d5bce79e3e6e9cabf6d7b040d84519197dc22f3";

  @Override public void onCreate() {
    super.onCreate();
    Orchextra.setLogLevel(OrchextraLogLevel.NETWORK);
    Log.d("APP", "Hello Application, start onCreate");
    Orchextra.init(App.this, App.this);
    Log.d("APP", "Hello Application, end onCreate");
  }

  @Override public void onSuccess() {
    callStart();
  }

  @Override public void onError(String s) {

  }

  @Override public void onInit(String s) {
    callStart();
  }

  private void callStart() {
    new Handler().post(new Runnable() {
      @Override public void run() {
        Orchextra.start(API_KEY, API_SECRET);
      }
    });
  }
}
