/*
 * Created by Orchextra
 *
 * Copyright (C) 2017 Gigigo Mobile Services SL
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


package com.gigigo.orchextra.core.utils.extensions

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import com.gigigo.orchextra.core.data.datasources.network.models.ApiClientApp
import com.gigigo.orchextra.core.data.datasources.network.models.ApiDeviceInfo
import com.gigigo.orchextra.core.data.datasources.network.models.ApiOxDevice
import java.util.Locale
import java.util.TimeZone

fun Context.getBaseApiOxDevice(): ApiOxDevice = with(this) {

  val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
  val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
  val bluetoothMacAddress = if (bluetoothAdapter != null) {
    bluetoothAdapter.address
  } else {
    ""
  }

  return ApiOxDevice(
      instanceId = "", // TODO  set instance id
      secureId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID),
      serialNumber = Build.SERIAL,
      bluetoothMacAddress = bluetoothMacAddress,
      wifiMacAddress = wifiManager.connectionInfo.macAddress,
      clientApp = getApiClientApp(),
      notificationPush = null, // TODO set notificationPush
      device = getApiDeviceInfo())
}

fun Context.getApiClientApp(): ApiClientApp = with(this) {
  val packageInfo = applicationContext.packageManager.getPackageInfo(packageName, 0)

  return ApiClientApp(
      bundleId = packageName,
      buildVersion = packageInfo.versionCode.toString(),
      appVersion = packageInfo.versionName,
      sdkVersion = "-",
      sdkDevice = "-")
}

fun Context.getApiDeviceInfo(): ApiDeviceInfo = with(this) {
  val timeZone = TimeZone.getDefault()
  val handset = if (Build.MODEL.startsWith(Build.MANUFACTURER)) {
    Build.MODEL.capitalize()
  } else {
    "${Build.MANUFACTURER.capitalize()} ${Build.MODEL}"
  }

  return ApiDeviceInfo(
      timeZone = "${timeZone.getDisplayName(false, TimeZone.SHORT)} ${timeZone.id}",
      osVersion = Build.VERSION.SDK_INT.toString(),
      language = Locale.getDefault().toString(),
      handset = handset,
      type = "ANDROID")
}