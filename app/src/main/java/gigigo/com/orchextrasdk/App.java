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
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.gigigo.orchextra.CrmUser;
import com.gigigo.orchextra.CustomSchemeReceiver;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.OrchextraBuilder;
import com.gigigo.orchextra.OrchextraCompletionCallback;
import com.gigigo.orchextra.OrchextraLogLevel;
import com.gigigo.vuforiaimplementation.ImageRecognitionVuforiaImpl;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class App extends Application implements OrchextraCompletionCallback, CustomSchemeReceiver {

    //nuevo proyecto test en Pr0
    public static final String API_KEY = "f61bb44ba4059fbe1b043f0c1e990b49a1844c01";
    public static final String API_SECRET = "a503f662b16a70a255b7c6bca16daf57c1c14918";

    // es el proyecto:      [PRO][CSE] - Spark Bratislava -->Quality
    // apikey: af8717703f0d90aed93df28899e44854bd98e244
    // apiSecret: 17e109d3847f5aa3ce48fd61de47106RET = "17e109d3847f5aa3ce48fd61de47106dd4fb44a2";
    //public static final String API_KEY = "af8717703f0d90aed93df28899e44854bd98e244";
    //public static final String API_SECRET = "17e109d3847f5aa3ce48fd61de47106dd4fb44a2";


    //android test de Staging
    //public static final String API_KEY = "34a4654b9804eab82aae05b2a5f949eb2a9f412c";
    //public static final String API_SECRET = "2d5bce79e3e6e9cabf6d7b040d84519197dc22f3";

    //[PROD][MX] - COCA-COLA DESTAPP
    // public static final String API_KEY = "deae9ac7e9db14414074d4103a42379c76f6cf58";
    //public static final String API_SECRET = "9ef0cc1bd0347223c8328a8f28f695e681a4c831";


    public static final String SENDER_ID = "Your_Sender_ID";//if is not valid sender id, orchextra disabled push receive

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("APP", "Hello Application, start onCreate");
        initOrchextra();
        Log.d("APP", "Hello Application, end onCreate");
    }

    public void initOrchextra() {

        OrchextraBuilder builder = new OrchextraBuilder(this)
                .setApiKeyAndSecret(API_KEY, API_SECRET)
                .setLogLevel(OrchextraLogLevel.NETWORK)
                .setOrchextraCompletionCallback(this)
                .setGcmSenderId(SENDER_ID)
                .setImageRecognitionModule(new ImageRecognitionVuforiaImpl());
        Orchextra.initialize(builder);

        Orchextra.setCustomSchemeReceiver(this);
        /**/
        String CRM_ID = getUniqueCRMID();

        Orchextra.bindUser(new CrmUser(CRM_ID,
                new GregorianCalendar(1981, Calendar.MAY, 31),
                CrmUser.Gender.GenderMale));

        //for reset the user
        //Orchextra.unBindUser();
        Orchextra.start(); //for only one time, each time you start Orchextra get orchextra project configuration is call
    }

    private String getUniqueCRMID() {
        String secureAndroidId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String serialNumber = Build.SERIAL;
        String deviceToken = secureAndroidId + BuildConfig.APPLICATION_ID + serialNumber;
        return deviceToken;
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



