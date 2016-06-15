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

import com.gigigo.orchextra.ORCUser;
import com.gigigo.orchextra.ORCUserTag;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.OrchextraCompletionCallback;
import com.gigigo.orchextra.OrchextraLogLevel;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class App extends Application implements OrchextraCompletionCallback {

public static final String API_KEY = "key";
  public static final String API_SECRET = "secret";

	Orchextra.setLogLevel(OrchextraLogLevel.ALL);
     Log.d("APP", "Hello Application, start onCreate");
        Orchextra.init(App.this, App.this);
        Orchextra.start("a370a3ec9a01dc8235b28f37082cf873907d3218", "69b5252c3e8c848014223e94d967db087393d526");
      Log.d("APP", "Hello Application, end onCreate");


     
         

   
    @Override
    public void onError(String s) {
    }

  
    @Override
    public void onInit(String s) {

   private void callStart() {
    new Handler().post(new Runnable() {
      @Override public void run() {
        Orchextra.start(API_KEY, API_SECRET);
      }
    });
  }
 }
