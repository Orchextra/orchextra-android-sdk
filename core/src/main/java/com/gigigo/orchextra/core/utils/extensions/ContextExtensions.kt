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
import com.gigigo.orchextra.core.domain.entities.AppData
import com.gigigo.orchextra.core.domain.entities.DeviceData
import java.util.Locale
import java.util.TimeZone

fun Context.getAppData(): AppData = with(this) {
  val packageInfo = applicationContext.packageManager.getPackageInfo(packageName, 0)

  return AppData(
      appVersion = packageInfo.versionName,
      buildVersion = packageInfo.versionCode.toString(),
      bundleId = packageName)
}

fun Context.getDeviceData(): DeviceData = with(this) {

  val timeZone = TimeZone.getDefault()
  val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
  val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

  return DeviceData(
      handset = if (Build.MODEL.startsWith(Build.MANUFACTURER)) {
        Build.MODEL.capitalize()
      } else {
        "${Build.MANUFACTURER.capitalize()} ${Build.MODEL}"
      },
      osVersion = Build.VERSION.SDK_INT.toString(),
      language = Locale.getDefault().toString(),
      timeZone = "${timeZone.getDisplayName(false, TimeZone.SHORT)} ${timeZone.id}",
      instanceId = "",
      secureId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID),
      serialNumber = Build.SERIAL,
      bluetoothMacAddress = if (bluetoothAdapter != null) {
        bluetoothAdapter.address
      } else {
        ""
      },
      wifiMacAddress = wifiManager.connectionInfo.macAddress,
      tags = listOf(),
      businessUnits = listOf())
}