package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.orchextra.domain.entities.ClientAuthData;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

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
    public void testDataToModel() throws Exception {
        Date expectedValue = SdkApiResponseMapperTest.getCalendar(2013, Calendar.SEPTEMBER, 29, 18, 46, 19);

        ApiClientAuthData apiClientAuthData = new ApiClientAuthData();
        apiClientAuthData.setValue("Test Value");
        apiClientAuthData.setProjectId("1234");
        apiClientAuthData.setExpiresIn(3000);
        apiClientAuthData.setExpiresAt("2013-09-29T18:46:19Z");
        apiClientAuthData.setUserId("Admin");

        ClientAuthData clientAuthData = mapper.dataToModel(apiClientAuthData);

        assertNotNull(clientAuthData);
        assertEquals("Test Value", clientAuthData.getValue());
        assertEquals("1234", clientAuthData.getProjectId());
        assertEquals(3000, clientAuthData.getExpiresIn());
        assertThat(expectedValue, isDateEqualTo(clientAuthData.getExpiresAt()));
    }

    @Test
    public void testModelToData() throws Exception {
        ClientAuthData sdkAuthData = new ClientAuthData();
        sdkAuthData.setValue("Test Value");
        sdkAuthData.setProjectId("1234");
        sdkAuthData.setExpiresIn(3000);
        sdkAuthData.setExpiresAt(SdkApiResponseMapperTest.getCalendar(2013, Calendar.SEPTEMBER, 29, 18, 46, 19));

        ApiClientAuthData apiClientAuthData = mapper.modelToData(sdkAuthData);

        assertNotNull(apiClientAuthData);
        assertEquals("Test Value", apiClientAuthData.getValue());
        assertEquals("1234", apiClientAuthData.getProjectId());
        assertEquals(new Integer(3000), apiClientAuthData.getExpiresIn());
        assertEquals("2013-09-29T18:46:19Z", apiClientAuthData.getExpiresAt());
    }
}