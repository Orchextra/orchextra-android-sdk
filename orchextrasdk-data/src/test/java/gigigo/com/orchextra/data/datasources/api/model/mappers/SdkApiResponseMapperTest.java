package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.orchextra.domain.entities.SdkAuthData;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiSdkAuthData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

        ApiSdkAuthData apiSdkAuthData = new ApiSdkAuthData();
        apiSdkAuthData.setValue("Test Value");
        apiSdkAuthData.setProjectId("1234");
        apiSdkAuthData.setExpiresIn(3000);
        apiSdkAuthData.setExpiresAt("2013-09-29T18:46:19Z");

        SdkAuthData sdkAuthData = mapper.dataToModel(apiSdkAuthData);

        assertNotNull(sdkAuthData);
        assertEquals("Test Value", sdkAuthData.getValue());
        assertEquals("1234", sdkAuthData.getProjectId());
        assertEquals(3000, sdkAuthData.getExpiresIn());
//        assertThat(expectedDate, isDateEqualTo(sdkAuthData.getExpiresAt()));
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
