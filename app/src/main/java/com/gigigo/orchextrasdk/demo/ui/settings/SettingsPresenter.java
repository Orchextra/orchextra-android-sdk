package com.gigigo.orchextrasdk.demo.ui.settings;

import com.gigigo.orchextrasdk.demo.utils.CredentialsPreferenceManager;

/**
 * Created by rui.alonso on 5/9/17.
 */

public class SettingsPresenter {

  private SettingsView view;
  private CredentialsPreferenceManager credentialsPreferenceManager;

  private String apiKey = "";
  private String apiSecret = "";
  private String projectName = "";

  public SettingsPresenter(SettingsView view, CredentialsPreferenceManager credentialsPreferenceManager) {
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
