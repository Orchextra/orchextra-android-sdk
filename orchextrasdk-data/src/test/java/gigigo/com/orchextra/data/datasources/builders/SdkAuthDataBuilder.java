package gigigo.com.orchextra.data.datasources.builders;

import com.gigigo.orchextra.domain.entities.SdkAuthData;

public class SdkAuthDataBuilder {

    public static SdkAuthDataBuilder Builder() {
        return new SdkAuthDataBuilder();
    }

    public SdkAuthData build() {
        SdkAuthData sdkAuthData = new SdkAuthData(ApiSdkAuthDataBuilder.VALUE, ApiSdkAuthDataBuilder.EXPIRES_IN, ApiSdkAuthDataBuilder.PROJECT_ID);
        return sdkAuthData;
    }
}
