package gigigo.com.orchextra.data.datasources.api.model.resquests;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public enum GrantType {
    AUTH_SDK("auth_sdk"),
    AUTH_USER("auth_user");

    private final String text;

    GrantType(final String text) {
      this.text = text;
    }

    public String getStringValue() {
      return text;
    }
}
