package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.orchextra.domain.entities.ClientAuthData;

import gigigo.com.orchextra.data.datasources.api.model.mappers.response.ClientApiResponseMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import gigigo.com.orchextra.data.datasources.api.builders.ApiClientAuthDataBuilder;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiClientAuthData;

import static gigigo.com.orchextra.data.testing.matchers.IsDateEqualTo.isDateEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ClientApiResponseMapperTest {

    private ClientApiResponseMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new ClientApiResponseMapper();
    }

    @Test
    public void testDataToModelOk() throws Exception {
        Date expectedValue = SdkApiResponseMapperTest.getCalendar(2013, Calendar.SEPTEMBER, 29, 18, 46, 19);

        ApiClientAuthData apiClientAuthData = ApiClientAuthDataBuilder.Builder().build();

        ClientAuthData clientAuthData = mapper.dataToModel(apiClientAuthData);

        assertNotNull(clientAuthData);
        assertEquals(ApiClientAuthDataBuilder.VALUE, clientAuthData.getValue());
        assertEquals(ApiClientAuthDataBuilder.PROJECT_ID, clientAuthData.getProjectId());
        assertEquals(ApiClientAuthDataBuilder.USER_ID, clientAuthData.getUserId());
        assertEquals(ApiClientAuthDataBuilder.EXPIRES_IN, clientAuthData.getExpiresIn());
        assertThat(expectedValue, isDateEqualTo(clientAuthData.getExpiresAt()));
    }

    @Test
    public void testDataToModelNullDate() throws Exception {
        ApiClientAuthData apiClientAuthData = ApiClientAuthDataBuilder.Builder().setDate(null).build();

        ClientAuthData clientAuthData = mapper.dataToModel(apiClientAuthData);

        assertNotNull(clientAuthData);
        assertEquals(ApiClientAuthDataBuilder.VALUE, clientAuthData.getValue());
        assertEquals(ApiClientAuthDataBuilder.PROJECT_ID, clientAuthData.getProjectId());
        assertEquals(ApiClientAuthDataBuilder.USER_ID, clientAuthData.getUserId());
        assertEquals(ApiClientAuthDataBuilder.EXPIRES_IN, clientAuthData.getExpiresIn());
        assertNotNull(clientAuthData.getExpiresAt());
    }
}