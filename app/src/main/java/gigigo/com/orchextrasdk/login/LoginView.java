package gigigo.com.orchextrasdk.login;

public interface LoginView {
  boolean checkPermissions();
  void requestPermission();

  void createOrchextra();
  void initOrchextra(String apiKey, String apiSecret);
  void showProjectCredentials(String apiKey, String apiSecret);
  void enableLogin(boolean enabled);

  void showCredentialsError(String message);
}
