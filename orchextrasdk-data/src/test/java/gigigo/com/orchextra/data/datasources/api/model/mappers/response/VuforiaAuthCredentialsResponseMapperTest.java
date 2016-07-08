package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiVuforia;

import static org.junit.Assert.assertEquals;

public class VuforiaAuthCredentialsResponseMapperTest {

    @Test
    public void testDataToModel() throws Exception {
        ApiVuforia apiVuforia = new ApiVuforia();
        apiVuforia.setClientAccessKey("AccessKey");
        apiVuforia.setClientSecretKey("SecretKey");
        apiVuforia.setLicenseKey("LicenseKey");

        VuforiaExternalClassToModelMapper mapper = new VuforiaExternalClassToModelMapper();
        VuforiaCredentials vuforiaCredentials = mapper.externalClassToModel(apiVuforia);

        assertEquals("AccessKey", vuforiaCredentials.getClientAccessKey());
        assertEquals("SecretKey", vuforiaCredentials.getClientSecretKey());
        assertEquals("LicenseKey", vuforiaCredentials.getLicenseKey());
    }


}