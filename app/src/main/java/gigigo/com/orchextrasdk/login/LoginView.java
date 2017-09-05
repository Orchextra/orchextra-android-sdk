package gigigo.com.orchextrasdk.login;

public interface LoginView {
  void createOrchextra();
  void initOrchextra(String apiKey, String apiSecret);

  void loadDefaultProject();
  void showProjectName(String projectName);
  void showProjectCredentials(String apiKey, String apiSecret);
  void enableLogin(boolean enabled);

  void showCredentialsError(String message);

  boolean checkPermissions();
  void requestPermission();
}
