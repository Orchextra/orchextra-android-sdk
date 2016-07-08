package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiVuforiaCredentials;

import static org.junit.Assert.assertEquals;

public class VuforiaAuthCredentialsResponseMapperTest {

    @Test
    public void testDataToModel() throws Exception {
        ApiVuforiaCredentials apiVuforiaCredentials = new ApiVuforiaCredentials();
        apiVuforiaCredentials.setClientAccessKey("AccessKey");
        apiVuforiaCredentials.setClientSecretKey("SecretKey");
        apiVuforiaCredentials.setLicenseKey("LicenseKey");

        VuforiaExternalClassToModelMapper mapper = new VuforiaExternalClassToModelMapper();
        VuforiaCredentials vuforiaCredentials = mapper.externalClassToModel(apiVuforiaCredentials);

        assertEquals("AccessKey", vuforiaCredentials.getClientAccessKey());
        assertEquals("SecretKey", vuforiaCredentials.getClientSecretKey());
        assertEquals("LicenseKey", vuforiaCredentials.getLicenseKey());
    }


}