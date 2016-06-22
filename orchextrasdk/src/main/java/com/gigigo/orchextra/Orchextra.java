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
import com.gigigo.orchextra.sdk.OrchextraManager;

public final class Orchextra {

    private Orchextra() {
    }

    public static OrchextraBuilder Builder() {
        return new OrchextraBuilder();
    }

    /**
     * @param apiKey
     * @param apiSecret
     */
    public static synchronized void start(String apiKey, String apiSecret) {
        OrchextraManager.sdkStart(apiKey, apiSecret);
    }

    public static synchronized void startImageRecognition() {
        OrchextraManager.startImageRecognition();
    }

    public static synchronized void stop() {
        OrchextraManager.sdkStop();
    }

    /** It gets custom schemes in our app
     * @param customSchemeReceiver Callback with the scheme detected from Orchextra
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
}
