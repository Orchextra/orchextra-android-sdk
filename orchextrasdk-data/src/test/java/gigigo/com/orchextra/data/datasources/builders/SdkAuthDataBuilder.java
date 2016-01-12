package gigigo.com.orchextra.data.datasources.builders;

import com.gigigo.orchextra.domain.entities.SdkAuthData;

public class SdkAuthDataBuilder {

    public static final String VALUE = "Test Value";
    public static final int EXPIRES_IN = 3000;
    public static final String PROJECT_ID = "1234";
    public static final String DATE = "2013-09-29T18:46:19Z";

    public static SdkAuthDataBuilder Builder() {
        return new SdkAuthDataBuilder();
    }

    public SdkAuthData build() {
        SdkAuthData sdkAuthData = new SdkAuthData(VALUE, EXPIRES_IN, PROJECT_ID);
        return sdkAuthData;
    }
}
