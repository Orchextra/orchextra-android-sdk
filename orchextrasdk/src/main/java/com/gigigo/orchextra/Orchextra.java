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

import com.gigigo.orchextra.device.bluetooth.beacons.BeaconBackgroundPeriodBetweenScan;
import com.gigigo.orchextra.domain.abstractions.actions.CustomOrchextraSchemeReceiver;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraManagerCompletionCallback;
import com.gigigo.orchextra.sdk.OrchextraManager;

import java.util.List;
import java.util.Map;

public final class Orchextra {

    private Orchextra() {
    }

    /**
     * Initialize Orchextra library.
     * <p/>
     * You MUST call this method inside of the onCreate method in your Application.
     * <p/>
     * It the FIRST Orchextra method you MUST call.
     */
    public static void initialize(final OrchextraBuilder orchextraBuilder) {

        final OrchextraCompletionCallback orchextraCompletionCallback = orchextraBuilder.getOrchextraCompletionCallback();
        OrchextraManagerCompletionCallback orchextraManagerCompletionCallback = new OrchextraManagerCompletionCallback() {
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
        };

        OrchextraManager.checkInitMethodCall(orchextraBuilder.getApplication(), orchextraManagerCompletionCallback);
        if (orchextraBuilder.getOrchextraLogLevel() != null) {
            OrchextraManager.setLogLevel(orchextraBuilder.getOrchextraLogLevel());
        }
        OrchextraManager.sdkInit(orchextraBuilder.getApplication(), orchextraManagerCompletionCallback);
        OrchextraManager.setGcmSendId(orchextraBuilder.getApplication(), orchextraBuilder.getGcmSenderId());
        OrchextraManager.saveApiKeyAndSecret(orchextraBuilder.getApiKey(), orchextraBuilder.getApiSecret());
        OrchextraManager.setImageRecognition(orchextraBuilder.getImageRecognitionModule());
    }




    /**
     * Start the Orchextra library. Calling this method Orchextra start to send and receive events.
     * <p/>
     * You can call this method in any moment after the calling of the initialize method.
     */
    public static void start() {
        OrchextraManager.sdkStart();
    }


    /**
     * Change the api key and secret defined in the initialization call in any moment.
     * <p/>
     * If the credentials are the same, it doesn't have effects. You don't have to use it, except you have almost 2 different credentials.
     */
    public static synchronized void changeCredentials(String apiKey, String apiSecret) {
        OrchextraManager.changeCredentials(apiKey, apiSecret);
    }

    /**
     * Start a new recognition view to scanner a image.
     * <p/>
     * You have to include Orchextra image recognition module in Gradle dependencies and initializate the module.
     */
    public static synchronized void startImageRecognition() {
        OrchextraManager.startImageRecognition();
    }

    /**
     * Orchextra stop to send and receive events. You can restart Orchextra calling start method.
     */
    public static synchronized void stop() {
        OrchextraManager.sdkStop();
    }

    /**
     * If it is definied in the dashboard a custom scheme action, all the events trigger, which match with of this type, are sending at this callback
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
     * You can define a specific user to associate Orchextra events.
     */
    public static synchronized void setUser(CrmUser crmUser) {
        OrchextraManager.setUser(crmUser);
    }

    /**
     * Open scanner view to scan QR's and barcodes
     */
    public static void startScannerActivity() {
        OrchextraManager.openScannerView();
    }


    /**
     * You can change the period between scanning beacons. The lower scanning period, the bigger battery consumption<br/>
     * For default, the period between scanning is 5 minutes(LIGHT).<p/>
     *
     * You can use this other intensities:<br/>
     * WEAK - 10 minutes<br/>
     * LIGHT - 5 minutes<br/>
     * MODERATE - 2 minutes<br/>
     * STRONG - 1 minute<br/>
     * SEVERE - 30 seconds<br/>
     * EXTREME - 10 seconds
     * <p/>
     * NOTE: We have to change this value when the app is in foreground.<p/>
     * NOTE 2: The beacon scanning period is defined in 10 seconds which is appropiated to discover all beacons nearby.
     */
    public static void updateBackgroundPeriodBetweenScan(BeaconBackgroundPeriodBetweenScan intensity) {
        OrchextraManager.updateBackgroundPeriodBetweenScan(intensity.getIntensity());
    }

    public static List<String> getDeviceTags() {
        return OrchextraManager.getDeviceTags();
    }

    public static void setDeviceTags(List<String> deviceTagList) {
        OrchextraManager.setDeviceTags(deviceTagList);
    }

    public static List<String> getDeviceBusinessUnits() {
        return OrchextraManager.getDeviceBusinessUnits();
    }

    public static void setDeviceBusinessUnits(List<String> deviceBusinessUnits) {
        OrchextraManager.setDeviceBusinessUnits(deviceBusinessUnits);
    }

    public static List<String> getUserTags() {
        return OrchextraManager.getUserTags();
    }

    public static void setUserTags(List<String> userTagList) {
        OrchextraManager.setUserTags(userTagList);
    }

    public static List<String> getUserBusinessUnits() {
        return OrchextraManager.getUserBusinessUnits();
    }

    public static void setUserBusinessUnits(List<String> userBusinessUnits) {
        OrchextraManager.setUserBusinessUnits(userBusinessUnits);
    }

    public static Map<String, String> getUserCustomFields() {
        return OrchextraManager.getUserCustomFields();
    }

    public static void setUserCustomFields(Map<String, String> userCustomFields) {
        OrchextraManager.setUserCustomFields(userCustomFields);
    }
}
