package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.entities.SdkAuthData;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import gigigo.com.orchextra.data.datasources.builders.ApiSdkAuthDataBuilder;
import gigigo.com.orchextra.data.datasources.builders.DateBuilder;
import gigigo.com.orchextra.data.datasources.builders.SdkAuthDataBuilder;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthRealm;

import static gigigo.com.orchextra.data.testing.matchers.IsDateEqualTo.isDateEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class SdkAuthReamlMapperTest {

    private SdkAuthReamlMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new SdkAuthReamlMapper();
    }

    @Test
    public void should_map_model_to_data() throws Exception {
        Date date = DateBuilder.getCalendar(2013, Calendar.SEPTEMBER, 29, 18, 46, 19);

        SdkAuthData sdkAuthData = SdkAuthDataBuilder.Builder().build();

        SdkAuthRealm sdkAuthRealm = mapper.modelToData(sdkAuthData);

        assertNotNull(sdkAuthRealm);
        assertEquals(ApiSdkAuthDataBuilder.VALUE, sdkAuthRealm.getValue());
        assertEquals(ApiSdkAuthDataBuilder.PROJECT_ID, sdkAuthRealm.getProjectId());
        assertEquals(ApiSdkAuthDataBuilder.EXPIRES_IN, sdkAuthRealm.getExpiresIn());
        assertEquals(ApiSdkAuthDataBuilder.EXPIRES_AT, sdkAuthRealm.getExpiresAt());
    }

    @Test
    public void should_map_data_to_model() throws Exception {
        Date date = SdkAuthDataBuilder.DATE;

        SdkAuthRealm sdkAuthRealm = new SdkAuthRealm();
        sdkAuthRealm.setValue(ApiSdkAuthDataBuilder.VALUE);
        sdkAuthRealm.setProjectId(ApiSdkAuthDataBuilder.PROJECT_ID);
        sdkAuthRealm.setExpiresIn(ApiSdkAuthDataBuilder.EXPIRES_IN);
        sdkAuthRealm.setExpiresAt(ApiSdkAuthDataBuilder.EXPIRES_AT);

        SdkAuthData sdkAuthData = mapper.dataToModel(sdkAuthRealm);

        assertNotNull(sdkAuthData);
        assertEquals(ApiSdkAuthDataBuilder.VALUE, sdkAuthData.getValue());
        assertEquals(ApiSdkAuthDataBuilder.PROJECT_ID, sdkAuthData.getProjectId());
        assertEquals(ApiSdkAuthDataBuilder.EXPIRES_IN, sdkAuthData.getExpiresIn());
        assertThat(date, isDateEqualTo(sdkAuthData.getExpiresAt()));
    }
}