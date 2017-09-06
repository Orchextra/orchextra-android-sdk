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

package gigigo.com.orchextrasdk.demo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public final class CredentialsPreferenceManager {

  private static final String API_KEY = "api_key";
  private static final String API_SECRET = "api_secret";
  private static final String PROJECT_NAME = "project_name";

  private final SharedPreferences sharedPreferences;

  public CredentialsPreferenceManager(Activity activity) {
    this.sharedPreferences = activity.getSharedPreferences("orchextra_credentials", MODE_PRIVATE);
  }

  public String getApiKey() {
    return sharedPreferences.getString(API_KEY, "");
  }

  public String getApiSecret() {
    return sharedPreferences.getString(API_SECRET, "");
  }

  public String getProjectName() {
    return sharedPreferences.getString(PROJECT_NAME, "");
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

  @SuppressLint("ApplySharedPref") public void saveProjectName(String projectName) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(PROJECT_NAME, projectName);
    editor.commit();
  }
}
