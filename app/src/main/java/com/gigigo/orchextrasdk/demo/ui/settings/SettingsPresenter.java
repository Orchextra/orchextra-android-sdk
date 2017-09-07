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

package com.gigigo.orchextrasdk.demo.ui.settings;

import com.gigigo.orchextrasdk.demo.utils.CredentialsPreferenceManager;

public class SettingsPresenter {

  private SettingsView view;
  private CredentialsPreferenceManager credentialsPreferenceManager;

  private String apiKey = "";
  private String apiSecret = "";
  private String projectName = "";

  public SettingsPresenter(SettingsView view,
      CredentialsPreferenceManager credentialsPreferenceManager) {
    this.view = view;
    this.credentialsPreferenceManager = credentialsPreferenceManager;
  }

  public void uiReady() {
    loadCredentials();
    view.setupOrchextra();
    view.showProjectName(projectName);
    view.showProjectCredentials(apiKey, apiSecret);

    view.enableLogout();
  }

  public void doLogout() {
    view.finishOrchextra();

    clearCredentials();
  }

  private void loadCredentials() {
    apiKey = credentialsPreferenceManager.getApiKey();
    apiSecret = credentialsPreferenceManager.getApiSecret();
    projectName = credentialsPreferenceManager.getProjectName();
  }

  private void saveCredentials() {
    credentialsPreferenceManager.saveApiKey(apiKey);
    credentialsPreferenceManager.saveApiSecret(apiSecret);
    credentialsPreferenceManager.saveProjectName(projectName);
  }

  private void clearCredentials() {
    apiKey = "";
    apiSecret = "";
    projectName = "";
    saveCredentials();
  }
}
