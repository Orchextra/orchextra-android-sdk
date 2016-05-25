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
import com.gigigo.vuforiaimplementation.ImageRecognitionVuforiaImpl;

public class App extends Application implements OrchextraCompletionCallback {
  @Override public void onCreate() {
    super.onCreate();
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
        Orchextra.start("eeb3720bc39ae0df4ab2947f47da64d4d25aae96", "5be2e5b6b5eefe04c14314d2be80abff50dae548");
      }
    });
  }
}
