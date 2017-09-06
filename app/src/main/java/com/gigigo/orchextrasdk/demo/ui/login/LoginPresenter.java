package com.gigigo.orchextrasdk.demo.ui.login;

import android.widget.ArrayAdapter;
import com.gigigo.orchextrasdk.demo.utils.CredentialsPreferenceManager;


public class LoginPresenter {
  private LoginView view;
  private CredentialsPreferenceManager credentialsPreferenceManager;

  private String projectName = "";
  private String apiKey = "";
  private String apiSecret = "";

  public LoginPresenter(LoginView view, CredentialsPreferenceManager credentialsPreferenceManager) {
    this.view = view;
    this.credentialsPreferenceManager = credentialsPreferenceManager;
  }

  public void uiReady() {
    view.createOrchextra();

    loadCredentials();
    checkLogged();
  }

  private void checkLogged() {
    if(validateCredentials()) {
      view.initOrchextra(apiKey, apiSecret);
    }
    else {
      view.loadDefaultProject();
    }

    view.enableLogin(true);
  }

  private void loadCredentials() {
    apiKey = credentialsPreferenceManager.getApiKey();
    apiSecret = credentialsPreferenceManager.getApiSecret();
  }

  private boolean validateCredentials() {
    return !apiKey.isEmpty() && !apiSecret.isEmpty();
  }

  public void readDefaultProject(ArrayAdapter projectsArray) {
    String project = projectsArray.getItem(0).toString();
    String[] projectConfig = project.split("#");

    if (projectConfig.length == 3) {
      projectName = projectConfig[0];
      apiKey = projectConfig[1];
      apiSecret = projectConfig[2];
    } else {
      projectName = "";
      apiKey = "";
      apiSecret = "";
    }

    view.showProjectName(projectName);
    view.showProjectCredentials(apiKey, apiKey);
  }

  public void doLogin() {
    if (validateCredentials()) {
      credentialsPreferenceManager.saveApiKey(apiKey);
      credentialsPreferenceManager.saveApiSecret(apiSecret);
      credentialsPreferenceManager.saveProjectName(projectName);

      boolean granted = view.checkPermissions();

      if (granted) {
        view.initOrchextra(apiKey, apiSecret);
      } else {
        view.requestPermission();
      }
    }
    else {
      view.showCredentialsError("Introduce las credenciales");
    }
  }

  public void permissionGranted(boolean granted) {
    if (granted) {
      view.initOrchextra(apiKey, apiSecret);
    } else {
      view.showCredentialsError("Lo necesitamos!!!");
    }
  }

}
