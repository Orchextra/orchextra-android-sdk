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
import android.util.Log;
import com.gigigo.orchextra.CustomSchemeReceiver;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.OrchextraBuilder;
import com.gigigo.orchextra.OrchextraCompletionCallback;
import com.gigigo.orchextra.OrchextraLogLevel;
import com.gigigo.vuforiaimplementation.ImageRecognitionVuforiaImpl;

public class App extends Application implements OrchextraCompletionCallback, CustomSchemeReceiver {

    //testdoublepush en Pr0
    public static final String API_KEY = "3805de10dd1b363d3030456a86bf01a7449f4b4f";
    public static final String API_SECRET = "2f15ac2b9d291034a2f66eea784f9b3be6e668e6";

    //test con blas
    //quality [PRO][CSE] - Spark Bratislava
    //public static final String API_KEY = "deae9ac7e9db14414074d4103a42379c76f6cf58";
    //public static final String API_SECRET = "17e109d3847f5aa3ce48fd61de47106dd4fb44a2";

    public static final String SENDER_ID = "Your_Sender_ID";//if is not valid sender id, orchextra disabled push receive(only inform for using pushnotifications)
    public static final String GIGIGO_URL = "http://research.gigigo.com";
    public static final String CUSTOM_SCHEME = "webview://";

    @Override
    public void onCreate() {
        super.onCreate();

        initOrchextra();

    }

    public void initOrchextra() {

        OrchextraBuilder builder = new OrchextraBuilder(this)
                .setApiKeyAndSecret(API_KEY, API_SECRET)
                .setLogLevel(OrchextraLogLevel.NETWORK)
                .setOrchextraCompletionCallback(this)
                .setGcmSenderId(SENDER_ID)
                .setImageRecognitionModule(new ImageRecognitionVuforiaImpl());

        //init Orchextra with builder configuration
        Orchextra.initialize(builder);

        //your can re set custom Scheme in other places(activities,services..)
        Orchextra.setCustomSchemeReceiver(this);

        //start Orchextra running, you can call stop() if you need
        Orchextra.start(); //for only one time, each time you start Orchextra get orchextra project configuration is call
    }


    @Override
    public void onSuccess() {
        Log.d("APP", "onSuccess");
    }

    @Override
    public void onError(String s) {
        Log.d("APP", "onError: " + s);
    }

    @Override
    public void onInit(String s) {
        Log.d("APP", "onInit: " + s);
    }

    @Override
    public void onReceive(String scheme) {
        Log.d("APP", "Scheme: " + scheme);
    }
}



