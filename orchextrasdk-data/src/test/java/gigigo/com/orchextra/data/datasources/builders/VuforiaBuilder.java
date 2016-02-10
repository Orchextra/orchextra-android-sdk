package gigigo.com.orchextra.data.datasources.builders;

import com.gigigo.orchextra.domain.model.entities.Vuforia;

public class VuforiaBuilder {

    public static final String LICENSE_KEY = "LicenseKey";
    public static final String ACCESS_KEY = "AccessKey";
    public static final String SECRET_KEY = "SecretKey";
    public static final String SERVER_ACCESS_KEY = "ServerAccessKey";
    public static final String SERVER_SECRET_KEY = "ServerSecretKey";
    
    private String licenseKey = LICENSE_KEY;
    private String clientAccessKey = ACCESS_KEY;
    private String clientSecretKey = SECRET_KEY;
    private String serverAccessKey = SERVER_ACCESS_KEY;
    private String serverSecretKey = SERVER_SECRET_KEY;

    public static VuforiaBuilder Builder() {
        return new VuforiaBuilder();
    }

    public Vuforia build() {
        Vuforia vuforia = new Vuforia();
        vuforia.setClientAccessKey(clientAccessKey);
        vuforia.setClientSecretKey(clientSecretKey);
        vuforia.setLicenseKey(licenseKey);
        vuforia.setServerAccessKey(serverAccessKey);
        vuforia.setServerSecretKey(serverSecretKey);

        return vuforia;
    }
}
