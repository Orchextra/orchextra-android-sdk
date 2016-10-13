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

package com.gigigo.orchextra.device.information;

import com.gigigo.orchextra.BuildConfig;
import com.gigigo.orchextra.domain.model.entities.SdkVersionAppInfo;
import com.gigigo.orchextra.sdk.OrchextraManager;

public class AndroidSdkVersionAppInfo {

    public SdkVersionAppInfo getAndroidAppInfo() {
        SdkVersionAppInfo sdkVersionAppInfo = new SdkVersionAppInfo();


        sdkVersionAppInfo.setAppVersion(String.valueOf(OrchextraManager.getApplicationVersion())); //BuildConfig.VERSION_NAME);
        sdkVersionAppInfo.setBuildVersion(String.valueOf(OrchextraManager.getApplicationVersionCode()));//BuildConfig.VERSION_CODE
        sdkVersionAppInfo.setBundleId(OrchextraManager.getApplicationId());//BuildConfig.APPLICATION_ID

        return sdkVersionAppInfo;
    }
}
