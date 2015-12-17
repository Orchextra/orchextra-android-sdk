package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.orchextra.domain.entities.SdkAuthData;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import gigigo.com.orchextra.data.datasources.api.builders.ApiSdkAuthDataBuilder;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiSdkAuthData;

import static gigigo.com.orchextra.data.testing.matchers.IsDateEqualTo.isDateEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class SdkApiResponseMapperTest {

    public static Date getCalendar(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
        return calendar.getTime();
    }

    private SdkApiResponseMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new SdkApiResponseMapper();
    }

    @Test
    public void testDataToModelOk() throws Exception {
        Date expectedDate = getCalendar(2013, Calendar.SEPTEMBER, 29, 18, 46, 19);

        ApiSdkAuthData apiSdkAuthData = ApiSdkAuthDataBuilder.Builder().build();

        SdkAuthData sdkAuthData = mapper.dataToModel(apiSdkAuthData);

        assertNotNull(sdkAuthData);
        assertEquals(ApiSdkAuthDataBuilder.VALUE, sdkAuthData.getValue());
        assertEquals(ApiSdkAuthDataBuilder.PROJECT_ID, sdkAuthData.getProjectId());
        assertEquals(ApiSdkAuthDataBuilder.EXPIRES_IN, sdkAuthData.getExpiresIn());
        assertThat(expectedDate, isDateEqualTo(sdkAuthData.getExpiresAt()));
    }

    @Test
    public void testDataToModelDateNull() throws Exception {
        ApiSdkAuthData apiSdkAuthData = ApiSdkAuthDataBuilder.Builder().setDate(null).build();

        SdkAuthData sdkAuthData = mapper.dataToModel(apiSdkAuthData);

        assertNotNull(sdkAuthData);
        assertEquals(ApiSdkAuthDataBuilder.VALUE, sdkAuthData.getValue());
        assertEquals(ApiSdkAuthDataBuilder.PROJECT_ID, sdkAuthData.getProjectId());
        assertEquals(ApiSdkAuthDataBuilder.EXPIRES_IN, sdkAuthData.getExpiresIn());
        assertNotNull(sdkAuthData.getExpiresAt());
    }

    @Test
    public void testModelToDataOk() throws Exception {
        SdkAuthData sdkAuthData = new SdkAuthData();
        sdkAuthData.setValue("Test Value");
        sdkAuthData.setProjectId("1234");
        sdkAuthData.setExpiresIn(3000);
        sdkAuthData.setExpiresAt(getCalendar(2013, Calendar.SEPTEMBER, 29, 18, 46, 19));

        ApiSdkAuthData apiSdkAuthData = mapper.modelToData(sdkAuthData);

        assertNotNull(apiSdkAuthData);
        assertEquals("Test Value", apiSdkAuthData.getValue());
        assertEquals("1234", apiSdkAuthData.getProjectId());
        assertEquals(new Integer(3000), apiSdkAuthData.getExpiresIn());
        assertEquals("2013-09-29T18:46:19Z", apiSdkAuthData.getExpiresAt());
    }
}
