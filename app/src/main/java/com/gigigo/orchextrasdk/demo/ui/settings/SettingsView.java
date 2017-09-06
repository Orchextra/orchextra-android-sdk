package com.gigigo.orchextrasdk.demo.ui.settings;

/**
 * Created by rui.alonso on 5/9/17.
 */

interface SettingsView {
  void setupOrchextra();
  void finishOrchextra();

  void enableLogout();

  void showProjectName(String projectName);
  void showProjectCredentials(String apiKey, String apiSecret);
}
