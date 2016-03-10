package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.orchextra.domain.model.entities.App;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.api.model.requests.ApiApp;

import static org.junit.Assert.assertEquals;

public class AppRequestMapperTest {

    @Test
    public void testModelToData() throws Exception {
        App app = new App();
        app.setAppVersion("3.14");
        app.setBuildVersion("1.1");
        app.setBundleId("Bundle");

        AppModelToExternalClassMapper appRequestMapper = new AppModelToExternalClassMapper();
        ApiApp apiApp = appRequestMapper.modelToExternalClass(app);

        assertEquals("3.14", apiApp.getAppVersion());
        assertEquals("1.1", apiApp.getBuildVersion());
        assertEquals("Bundle", apiApp.getBundleId());
    }
}