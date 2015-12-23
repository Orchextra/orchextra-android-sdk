package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.orchextra.domain.entities.Vuforia;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiVuforia;

import static org.junit.Assert.assertEquals;

public class VuforiaResponseMapperTest {

    @Test
    public void testDataToModel() throws Exception {
        ApiVuforia apiVuforia = new ApiVuforia();
        apiVuforia.setClientAccessKey("AccessKey");
        apiVuforia.setClientSecretKey("SecretKey");
        apiVuforia.setLicenseKey("LicenseKey");
        apiVuforia.setServerAccessKey("ServerAccessKey");
        apiVuforia.setServerSecretKey("ServerSecretKey");

        VuforiaResponseMapper mapper = new VuforiaResponseMapper();
        Vuforia vuforia = mapper.dataToModel(apiVuforia);

        assertEquals("AccessKey", vuforia.getClientAccessKey());
        assertEquals("SecretKey", vuforia.getClientSecretKey());
        assertEquals("LicenseKey", vuforia.getLicenseKey());
        assertEquals("ServerAccessKey", vuforia.getServerAccessKey());
        assertEquals("ServerSecretKey", vuforia.getServerSecretKey());
    }


}