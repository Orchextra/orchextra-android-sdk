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

package com.gigigo.orchextrasdk.demo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public final class FiltersPreferenceManager {

  private static final String BARCODE = "BARCODE";
  private static final String QR = "QR";
  private static final String IMAGE_RECOGNITION = "IMAGE_RECOGNITION";
  private static final String BEACON = "BEACON";
  private static final String BEACON_REGION = "BEACON_REGION";
  private static final String GEOFENCE = "GEOFENCE";
  private static final String EDDYSTONE = "EDDYSTONE";

  private final SharedPreferences sharedPreferences;

  public FiltersPreferenceManager(Context context) {
    this.sharedPreferences =
        context.getSharedPreferences("orchextra_trigger_filters", MODE_PRIVATE);
  }

  public boolean getBarcode() {
    return sharedPreferences.getBoolean(BARCODE, false);
  }

  public boolean getQr() {
    return sharedPreferences.getBoolean(QR, false);
  }

  public boolean getImageRecognition() {
    return sharedPreferences.getBoolean(IMAGE_RECOGNITION, false);
  }

  public boolean getBeacon() {
    return sharedPreferences.getBoolean(BEACON, false);
  }

  public boolean getBeaconRegion() {
    return sharedPreferences.getBoolean(BEACON_REGION, false);
  }

  public boolean getGeofence() {
    return sharedPreferences.getBoolean(GEOFENCE, false);
  }

  public boolean getEddystone() {
    return sharedPreferences.getBoolean(EDDYSTONE, false);
  }

  @SuppressLint("ApplySharedPref") public void saveBarcode(boolean barcode) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(BARCODE, barcode);
    editor.commit();
  }

  @SuppressLint("ApplySharedPref") public void saveQr(boolean qr) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(QR, qr);
    editor.commit();
  }

  @SuppressLint("ApplySharedPref") public void saveImageRecognition(boolean imageRecognition) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(IMAGE_RECOGNITION, imageRecognition);
    editor.commit();
  }

  @SuppressLint("ApplySharedPref") public void saveBeacon(boolean beacon) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(BEACON, beacon);
    editor.commit();
  }

  @SuppressLint("ApplySharedPref") public void saveBeaconRegion(boolean beaconRegion) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(BEACON_REGION, beaconRegion);
    editor.commit();
  }

  @SuppressLint("ApplySharedPref") public void saveGeofence(boolean geofence) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(GEOFENCE, geofence);
    editor.commit();
  }

  @SuppressLint("ApplySharedPref") public void saveEddystone(boolean eddystone) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(EDDYSTONE, eddystone);
    editor.commit();
  }
}
