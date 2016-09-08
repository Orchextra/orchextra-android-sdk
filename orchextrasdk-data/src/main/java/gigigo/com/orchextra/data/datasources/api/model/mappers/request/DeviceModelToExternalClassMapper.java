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

package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.vo.Device;

import gigigo.com.orchextra.data.datasources.api.model.requests.ApiDevice;


public class DeviceModelToExternalClassMapper
        implements ModelToExternalClassMapper<Device, ApiDevice> {

    @Override
    public ApiDevice modelToExternalClass(Device device) {
        ApiDevice apiDevice = new ApiDevice();

        apiDevice.setInstanceId(device.getInstanceId());
        apiDevice.setLanguage(device.getLanguage());
        apiDevice.setOsVersion(device.getOsVersion());
        apiDevice.setSecureId(device.getSecureId());
        apiDevice.setSerialNumber(device.getSerialNumber());
        apiDevice.setTimeZone(device.getTimeZone());
        apiDevice.setWifiMacAddress(device.getWifiMacAddress());
        apiDevice.setHandset(device.getHandset());
        apiDevice.setBluetoothMacAddress(device.getBluetoothMacAddress());

        if (device.getTags() != null && device.getTags().size() > 0) {
            apiDevice.setTags(device.getTags());
        }

        if (device.getBusinessUnits() != null && device.getBusinessUnits().size() > 0) {
            apiDevice.setBusinessUnits(device.getBusinessUnits());
        }

        return apiDevice;
    }
}
