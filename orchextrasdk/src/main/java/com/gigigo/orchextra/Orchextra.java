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

import com.gigigo.orchextra.domain.abstractions.actions.CustomOrchextraSchemeReceiver;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraManagerCompletionCallback;
import com.gigigo.orchextra.sdk.OrchextraManager;

public final class Orchextra {

    private Orchextra() {
    }

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

        OrchextraManager.setLogLevel(orchextraBuilder.getOrchextraLogLevel());
        OrchextraManager.sdkInit(orchextraBuilder.getApplication(), orchextraManagerCompletionCallback);
        OrchextraManager.saveApiKeyAndSecret(orchextraBuilder.getApiKey(), orchextraBuilder.getApiSecret());
        OrchextraManager.setImageRecognition(orchextraBuilder.getImageRecognitionModule());
    }

    public static void start() {
        OrchextraManager.sdkStart();
    }

    public static synchronized void changeCredentials(String apiKey, String apiSecret) {
        OrchextraManager.changeCredentials(apiKey, apiSecret);
    }

    public static synchronized void startImageRecognition() {
        OrchextraManager.startImageRecognition();
    }

    public static synchronized void stop() {
        OrchextraManager.sdkStop();
    }

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

    public static synchronized void setUser(ORCUser orcUser) {
        OrchextraManager.setUser(orcUser);
    }

    public static void startScannerActivity() {
        OrchextraManager.openScannerView();
    }


}
