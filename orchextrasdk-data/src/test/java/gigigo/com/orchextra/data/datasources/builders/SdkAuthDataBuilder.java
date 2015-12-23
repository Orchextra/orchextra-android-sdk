package gigigo.com.orchextra.data.datasources.builders;

import com.gigigo.orchextra.domain.entities.SdkAuthData;

import java.util.Calendar;
import java.util.Date;

public class SdkAuthDataBuilder {

    public static final Date DATE = DateBuilder.getCalendar(2013, Calendar.SEPTEMBER, 29, 18, 46, 19);

    public static SdkAuthDataBuilder Builder() {
        return new SdkAuthDataBuilder();
    }

    public SdkAuthData build() {
        SdkAuthData sdkAuthData = new SdkAuthData();
        sdkAuthData.setValue(ApiSdkAuthDataBuilder.VALUE);
        sdkAuthData.setProjectId(ApiSdkAuthDataBuilder.PROJECT_ID);
        sdkAuthData.setExpiresIn(ApiSdkAuthDataBuilder.EXPIRES_IN);
        sdkAuthData.setExpiresAt(DATE);

        return sdkAuthData;
    }
}
