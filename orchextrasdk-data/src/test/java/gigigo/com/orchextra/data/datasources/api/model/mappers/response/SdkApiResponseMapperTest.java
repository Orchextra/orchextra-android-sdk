package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import gigigo.com.orchextra.data.datasources.builders.ApiSdkAuthDataBuilder;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiSdkAuthData;

import static gigigo.com.orchextra.data.testing.matchers.IsDateEqualTo.isDateEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class SdkApiResponseMapperTest {

    private SdkApiExternalClassToModelMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new SdkApiExternalClassToModelMapper();
    }

    @Test
    public void testDataToModelOk() throws Exception {
        Date expectedDate = new Date(Calendar.getInstance().getTimeInMillis()+ApiSdkAuthDataBuilder.EXPIRES_IN);

        ApiSdkAuthData apiSdkAuthData = ApiSdkAuthDataBuilder.Builder().build();

        SdkAuthData sdkAuthData = mapper.externalClassToModel(apiSdkAuthData);

        assertNotNull(sdkAuthData);
        assertEquals(ApiSdkAuthDataBuilder.VALUE, sdkAuthData.getValue());
        assertEquals(ApiSdkAuthDataBuilder.PROJECT_ID, sdkAuthData.getProjectId());
        assertEquals(ApiSdkAuthDataBuilder.EXPIRES_IN, sdkAuthData.getExpiresIn());
        assertThat(expectedDate, isDateEqualTo(sdkAuthData.getExpiresAt()));
    }

    @Test
    public void testDataToModelDateNull() throws Exception {
        ApiSdkAuthData apiSdkAuthData = ApiSdkAuthDataBuilder.Builder().build();

        SdkAuthData sdkAuthData = mapper.externalClassToModel(apiSdkAuthData);

        assertNotNull(sdkAuthData);
        assertEquals(ApiSdkAuthDataBuilder.VALUE, sdkAuthData.getValue());
        assertEquals(ApiSdkAuthDataBuilder.PROJECT_ID, sdkAuthData.getProjectId());
        assertEquals(ApiSdkAuthDataBuilder.EXPIRES_IN, sdkAuthData.getExpiresIn());
        assertNotNull(sdkAuthData.getExpiresAt());
    }
}
