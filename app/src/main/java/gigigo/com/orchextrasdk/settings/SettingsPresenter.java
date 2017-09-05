package gigigo.com.orchextrasdk.settings;

/**
 * Created by rui.alonso on 5/9/17.
 */

public class SettingsPresenter {

  private SettingsView view;
  private CredentialsPreferenceManager credentialsPreferenceManager;

  private String apiKey = "";
  private String apiSecret = "";

  public SettingsPresenter(SettingsView view, CredentialsPreferenceManager credentialsPreferenceManager) {
    this.view = view;
    this.credentialsPreferenceManager = credentialsPreferenceManager;
  }

  public void uiReady() {
    loadCredentials();
    view.setupOrchextra();
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
  }

  private void saveCredentials() {
    credentialsPreferenceManager.saveApiKey(apiKey);
    credentialsPreferenceManager.saveApiSecret(apiSecret);
  }

  private void clearCredentials() {
    apiKey = "";
    apiSecret = "";
    saveCredentials();
  }
}
