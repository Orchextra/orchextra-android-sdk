package gigigo.com.orchextra.data.datasources.builders;

import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;

public class VuforiaBuilder {

    public static final String LICENSE_KEY = "LicenseKey";
    public static final String ACCESS_KEY = "AccessKey";
    public static final String SECRET_KEY = "SecretKey";

    private String licenseKey = LICENSE_KEY;
    private String clientAccessKey = ACCESS_KEY;
    private String clientSecretKey = SECRET_KEY;

    public static VuforiaBuilder Builder() {
        return new VuforiaBuilder();
    }

    public VuforiaCredentials build() {
        VuforiaCredentials vuforiaCredentials = new VuforiaCredentials();
        vuforiaCredentials.setClientAccessKey(clientAccessKey);
        vuforiaCredentials.setClientSecretKey(clientSecretKey);
        vuforiaCredentials.setLicenseKey(licenseKey);

        return vuforiaCredentials;
    }
}
