package gigigo.com.orchextrasdk.login;

import android.widget.ArrayAdapter;
import gigigo.com.orchextrasdk.settings.CredentialsPreferenceManager;
import java.util.ArrayList;
import java.util.List;

public class LoginPresenter {
  private LoginView view;
  private CredentialsPreferenceManager credentialsPreferenceManager;

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
  }

  private void loadCredentials() {
    apiKey = credentialsPreferenceManager.getApiKey();
    apiSecret = credentialsPreferenceManager.getApiSecret();
  }

  private boolean validateCredentials() {
    return !apiKey.isEmpty() && !apiSecret.isEmpty();
  }

  public void projectSelected(String project) {
    String[] projectConfig = project.split("#");
    if (projectConfig.length == 3) {
      apiKey = projectConfig[1];
      apiSecret = projectConfig[2];
    } else {
      apiKey = "";
      apiSecret = "";
    }

    view.showProjectCredentials(apiKey, apiKey);
    view.enableLogin(validateCredentials());
  }

  public List<String> readDefaultProjects(ArrayAdapter projectsArray) {
    List<String> projects = new ArrayList<>();
    for (int i = 0; i < projectsArray.getCount(); i++) {
      String project = projectsArray.getItem(i).toString();
      String[] projectConfig = project.split("#");
      projects.add(projectConfig[0]);
    }

    return projects;
  }

  public void doLogin() {
    if (validateCredentials()) {
      credentialsPreferenceManager.saveApiKey(apiKey);
      credentialsPreferenceManager.saveApiSecret(apiSecret);

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
