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

package com.gigigo.orchextra.device.permissions;

import android.content.Context;

import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureListener;
import com.gigigo.orchextra.sdk.features.GooglePlayServicesFeature;
import com.google.android.gms.common.GoogleApiAvailability;

public class GoogleApiPermissionChecker {

    private final Context context;
    private final FeatureListener featureListener;

    public GoogleApiPermissionChecker(Context context, FeatureListener featureListener) {
        this.context = context;
        this.featureListener = featureListener;
    }

    public int checkPlayServicesStatus() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(context);
        featureListener.onFeatureStatusChanged(new GooglePlayServicesFeature(status));
        return status;
    }
}
