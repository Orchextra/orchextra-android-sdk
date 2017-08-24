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

package gigigo.com.orchextrasdk.settings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public final class OrchextraPreferenceManager {

  private static final String API_KEY = "api_key";
  private static final String API_SECRET = "api_secret";

  private final SharedPreferences sharedPreferences;

  public OrchextraPreferenceManager(Activity activity) {
    this.sharedPreferences = activity.getPreferences(MODE_PRIVATE);
  }

  public String getApiKey() {
    return sharedPreferences.getString(API_KEY, "34a4654b9804eab82aae05b2a5f949eb2a9f412c");
  }

  public String getApiSecret() {
    return sharedPreferences.getString(API_SECRET, "2d5bce79e3e6e9cabf6d7b040d84519197dc22f3");
  }

  @SuppressLint("ApplySharedPref") public void saveApiKey(String apiKey) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(API_KEY, apiKey);
    editor.commit();
  }

  @SuppressLint("ApplySharedPref") public void saveApiSecret(String apiSecret) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(API_SECRET, apiSecret);
    editor.commit();
  }
}
