package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.orchextra.domain.model.entities.Vuforia;

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

        VuforiaExternalClassToModelMapper mapper = new VuforiaExternalClassToModelMapper();
        Vuforia vuforia = mapper.externalClassToModel(apiVuforia);

        assertEquals("AccessKey", vuforia.getClientAccessKey());
        assertEquals("SecretKey", vuforia.getClientSecretKey());
        assertEquals("LicenseKey", vuforia.getLicenseKey());
    }


}