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
package com.gigigo.orchextra;

import android.app.Application;
import android.content.ComponentName;
import android.content.pm.PackageManager;

import com.gigigo.imagerecognitioninterface.ImageRecognition;
import com.gigigo.orchextra.device.notificationpush.OrchextraGcmListenerService;
import com.gigigo.orchextra.domain.abstractions.actions.CustomOrchextraSchemeReceiver;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraManagerCompletionCallback;
import com.gigigo.orchextra.sdk.OrchextraManager;

public class Orchextra {

    /**
     * @param application
     * @param orchextraCompletionCallback
     */
    public static synchronized void init(Application application,
                                         final OrchextraCompletionCallback orchextraCompletionCallback) {

        enabledOrchextraNotificationPush(application);

        OrchextraManager.sdkInit(application, new OrchextraManagerCompletionCallback() {
            @Override
            public void onSuccess() {
                if (orchextraCompletionCallback != null) {
                    orchextraCompletionCallback.onSuccess();
                }
            }

            @Override
            public void onError(String s) {
                if (orchextraCompletionCallback != null) {
                    orchextraCompletionCallback.onError(s);
                }
            }

            @Override
            public void onInit(String s) {
                if (orchextraCompletionCallback != null) {
                    orchextraCompletionCallback.onInit(s);
                }
            }
        });


    }

    //this method enabled or disabled the service with the intent-filter for RECEIVER the push, this is necesary
    //because you must to declare always in the manifest file, you can not do it with code. Beacause that we
    //keep the service OrchextraGcmListenerService and the intent filter in manifest, but weenabled or disabled
    //the service if the sender ID in Orchextra are not setted
    private static void enabledOrchextraNotificationPush(Application application) {

        String senderID = application.getApplicationContext().getString(R.string.ox_notifications_GCM_sender_id);
        if (senderID.equals(application.getApplicationContext().getString(R.string.ox_notifications_demo_GCM_sender_id))) {
            ComponentName component = new ComponentName(application, OrchextraGcmListenerService.class);
            application.getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        } else {
            ComponentName component = new ComponentName(application, OrchextraGcmListenerService.class);
            application.getPackageManager().setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        }
    }

    /**
     * @param apiKey
     * @param apiSecret
     */
    public static synchronized void start(String apiKey, String apiSecret) {
        OrchextraManager.sdkStart(apiKey, apiSecret);

    }

    public static synchronized void setImageRecognitionModule(ImageRecognition imageRecognitionModule) {
        OrchextraManager.setImageRecognition(imageRecognitionModule);
    }

    public static synchronized void startImageRecognition() {
        OrchextraManager.startImageRecognition();
    }

    public static synchronized void stop() {
        OrchextraManager.sdkStop();
    }

    /**
     * @param customSchemeReceiver
     */
    public static synchronized void setCustomSchemeReceiver(final CustomSchemeReceiver customSchemeReceiver) {
        if (customSchemeReceiver != null) {
            OrchextraManager.setCustomSchemeReceiver(new CustomOrchextraSchemeReceiver() {
                @Override
                public void onReceive(String scheme) {
                    customSchemeReceiver.onReceive(scheme);
                }
            });
        }
    }

    /**
     * @param orcUser
     */
    public static synchronized void setUser(ORCUser orcUser) {
        OrchextraManager.setUser(orcUser);
    }

    public static void startScannerActivity() {
        OrchextraManager.openScannerView();
    }

    /**
     * @param orchextraLogLevel
     */
    public static void setLogLevel(OrchextraLogLevel orchextraLogLevel) {
        OrchextraManager.setLogLevel(orchextraLogLevel);
    }

}
