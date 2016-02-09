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
