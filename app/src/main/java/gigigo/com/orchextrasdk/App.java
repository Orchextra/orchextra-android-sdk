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

    public static final String API_KEY = "3805de10dd1b363d3030456a86bf01a7449f4b4f";
    public static final String API_SECRET = "2f15ac2b9d291034a2f66eea784f9b3be6e668e6";
    public static final String SENDER_ID = "Your_Sender_ID";//if is not valid sender id, orchextra disabled push receive

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("APP", "Hello Application, start onCreate");
        initOrchextra();
        Log.d("APP", "Hello Application, end onCreate");
    }

    private void initOrchextra() {

        OrchextraBuilder builder = new OrchextraBuilder(this)
                .setApiKeyAndSecret(API_KEY, API_SECRET)
                .setLogLevel(OrchextraLogLevel.NETWORK)
                .setOrchextraCompletionCallback(this)
                .setGcmSenderId(SENDER_ID)

                .setImageRecognitionModule(new ImageRecognitionVuforiaImpl());

        Orchextra.initialize(builder);

        Orchextra.setCustomSchemeReceiver(this);


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



