package gigigo.com.orchextra.data.datasources.builders;

import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;

public class SdkAuthCredentialsBuilder {

    public static final String KEY = "KEY";
    public static final String SECRET = "SECRET";

    private String key = KEY;
    private String secret = SECRET;

    public static SdkAuthCredentialsBuilder Builder() {
        return new SdkAuthCredentialsBuilder();
    }

    public SdkAuthCredentials build() {
        SdkAuthCredentials sdkAuthCredentials = new SdkAuthCredentials(key, secret);
        return sdkAuthCredentials;
    }
}
