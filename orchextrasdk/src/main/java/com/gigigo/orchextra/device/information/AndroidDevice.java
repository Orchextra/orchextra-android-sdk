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

import android.content.Context;

import com.gigigo.ggglib.device.DeviceInfoProvider;
import com.gigigo.orchextra.domain.model.vo.Device;

import java.util.Locale;
import java.util.TimeZone;

public class AndroidDevice {

    private final Context context;

    public AndroidDevice(Context context) {
        this.context = context;
    }

    public Device getAndroidDeviceInfo() {
        Device device = new Device();

        device.setHandset(DeviceInfoProvider.getHandset());
        device.setOsVersion(DeviceInfoProvider.getOsVersion());
        device.setInstanceId(DeviceInfoProvider.getAndroidInstanceId(context));
        device.setSecureId(DeviceInfoProvider.getAndroidSecureId(context));
        device.setSerialNumber(DeviceInfoProvider.getAndroidSerialNumber());
        device.setBluetoothMacAddress(DeviceInfoProvider.getBluetoothMac());
        device.setWifiMacAddress(DeviceInfoProvider.getWifiMac(context));

        device.setLanguage(Locale.getDefault().toString());


        device.setTimeZone(getTimeZone());

        return device;
    }

    public String getTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getDisplayName(false, TimeZone.SHORT) + " " + tz.getID();
    }

}
