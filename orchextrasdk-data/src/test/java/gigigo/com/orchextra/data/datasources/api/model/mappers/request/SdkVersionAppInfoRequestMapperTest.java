package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.orchextra.domain.model.entities.SdkVersionAppInfo;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.api.model.requests.ApiSdkVersionAppInfo;

import static org.junit.Assert.assertEquals;

public class SdkVersionAppInfoRequestMapperTest {

    @Test
    public void testModelToData() throws Exception {
        SdkVersionAppInfo sdkVersionAppInfo = new SdkVersionAppInfo();
        sdkVersionAppInfo.setAppVersion("3.14");
        sdkVersionAppInfo.setBuildVersion("1.1");
        sdkVersionAppInfo.setBundleId("Bundle");

        AppModelToExternalClassMapper appRequestMapper = new AppModelToExternalClassMapper();
        ApiSdkVersionAppInfo apiSdkVersionAppInfo = appRequestMapper.modelToExternalClass(sdkVersionAppInfo);

        assertEquals("3.14", apiSdkVersionAppInfo.getAppVersion());
        assertEquals("1.1", apiSdkVersionAppInfo.getBuildVersion());
        assertEquals("Bundle", apiSdkVersionAppInfo.getBundleId());
    }
}