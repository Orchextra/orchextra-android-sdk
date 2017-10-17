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
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import com.gigigo.orchextra.CustomSchemeReceiver;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.OrchextraBuilder;
import com.gigigo.orchextra.OrchextraCompletionCallback;
import com.gigigo.orchextra.OrchextraLogLevel;
import com.gigigo.orchextra.device.bluetooth.beacons.BeaconBackgroundModeScan;
import gigigo.com.orchextrasdk.adonservices.UpdateConfigReceiver;

public class App extends Application implements OrchextraCompletionCallback, CustomSchemeReceiver {
  //public static String API_KEY = "338d65a6572be208f25a9a5815861543adaa4abb";
  //public static String API_SECRET = "b29dac01598f9d8e2102aef73ac816c0786843ef";

  public static final String SENDER_ID = "Your_Sender_ID";
  //if is not valid sender id, orchextra disabled push receive(only inform for using pushnotifications)
  public static final String GIGIGO_URL = "http://research.gigigo.com";
  public static final String CUSTOM_SCHEME = "webview://";

  // public static MotionServiceUtility mMotionServiceUtility;
  //eddystone test
  //public static String API_KEY = "97f4c2325557f4bfcb508fff587a3f4a5a7dffb0";
  //public static String API_SECRET = "d4a2c0f5b725f83b20d561768c80e09c39602a49";

  //eddystone test with real configuration
  //public static String API_KEY = "ef08c4dccb7649b9956296a863db002a68240be2";
  //public static String API_SECRET = "6bc18c500546f253699f61c11a62827679178400";

  //test geofences
  public static String API_KEY = "3805de10dd1b363d3030456a86bf01a7449f4b4f";
  public static String API_SECRET = "2f15ac2b9d291034a2f66eea784f9b3be6e668e6";


  @Override public void onCreate() {
    super.onCreate();
    initOrchextra();
  }

  public void initOrchextra() {
    System.out.println(
        "REALM /////////////////////////////////////\n\n\n\n\n\n\n REALM se ejecuta el onCreate ");
    String oxKey = API_KEY;
    String oxSecret = API_SECRET;
    //oxKey = "fake";
    //oxSecret = "fake ";
    OrchextraBuilder builder = new OrchextraBuilder(this)
        .setApiKeyAndSecret(oxKey, oxSecret)
        .setLogLevel(OrchextraLogLevel.NETWORK)
        .setOrchextraCompletionCallback(this)
        .setGcmSenderId(null)
        .setNotificationActivityClass(MainActivity.class.toString())
        //  .setImageRecognitionModule(new ImageRecognitionVuforiaImpl())
        .setBackgroundBeaconScanMode(BeaconBackgroundModeScan.HARDCORE);
    //init Orchextra with builder configuration
    Orchextra.initialize(builder);
    //your can re set custom Scheme in other places(activities,services..)
    Orchextra.setCustomSchemeReceiver(this);
    //start Orchextra running, you can call stop() if you need
     Orchextra.start(); //for only one time, each time you start Orchextra get orchextra project configuration is call

    //region AdOns
    // mMotionServiceUtility = new MotionServiceUtility(this);

    //bluetooth //reset BT each 65min is a test, is not c00l, but prevents beaconsscan errors
    // BluetoothResetUtility bluetoothResetUtility = new BluetoothResetUtility(this);
    // bluetoothResetUtility.createAlarmResetBluetoothEachTime(60 * 1000 * 65);

    //UpdateConfigWrapper Only when Start
   /* UpdateConfigUtility updater = new UpdateConfigUtility(this);
    updater.createUpdateConfigurationByTime(60 * 1000 * 60);
    enablerUpdateConfigReBootService(this, true);
    */
    //endregion
  }

  private void enablerUpdateConfigReBootService(Application application, boolean bState) {
    int componentEnabledState;
    if (!bState) {
      componentEnabledState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
    } else {
      componentEnabledState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
    }
    ComponentName component = new ComponentName(application, UpdateConfigReceiver.class);
    application.getPackageManager()
        .setComponentEnabledSetting(component, componentEnabledState, PackageManager.DONT_KILL_APP);
  }

  @Override public void onSuccess() {
    Log.d("APP", "onSuccess");

    //new Handler(Looper.getMainLooper()).post(new Runnable() {
    //  @Override public void run() {
    //    Toast.makeText(App.this, "onSuccess:  app" , Toast.LENGTH_LONG).show();
    //  }
    //});
  }

  @Override public void onError(final String s) {
    // OrchextraBusinessErrors errorCode= (OrchextraBusinessErrors)s;
    //OrchextraBusinessErrors {
    //  NO_AUTH_EXPIRED(401),
    //  NO_AUTH_CREDENTIALS(403),
    //  VALIDATION_ERROR(11200),
    //  INTERNAL_SERVER_ERROR(500),
    //  GENERIC_UNKNOWN_ERROR(-999);

    Log.d("APP", "onError: " + s);
    System.out.println("onError: "
        + s
        + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    //new Handler(Looper.getMainLooper()).post(new Runnable() {
    //  @Override public void run() {
    //    Toast.makeText(App.this, "onError:  app" + s, Toast.LENGTH_LONG).show();
    //  }
    //});
  }

  @Override public void onInit(final String s) {
    Log.d("APP", "onInit: " + s);
    //new Handler(Looper.getMainLooper()).post(new Runnable() {
    //  @Override public void run() {
    //    Toast.makeText(App.this, "onInit:  app", Toast.LENGTH_LONG).show();
    //  }
    //});
  }

  @Override public void onConfigurationReceive(final String s) {
    Log.i("", "Access Token:" + s);
    Log.d("APP", "onInit: " + s);
System.out.println("Access Token:" + s);
    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override public void run() {
        Toast.makeText(App.this, "onConfigurationReceive:  app" + s, Toast.LENGTH_LONG).show();
      }
    });
  }

  @Override public void onReceive(final String scheme) {
    Log.d("APP", "Scheme: " + scheme);
    //new Handler(Looper.getMainLooper()).post(new Runnable() {
    //  @Override public void run() {
    //    Toast.makeText(App.this, "onReceive:  app" + scheme, Toast.LENGTH_LONG).show();
    //  }
    //});
  }
}



