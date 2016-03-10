package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.network.mappers.DateFormatConstants;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import gigigo.com.orchextra.data.datasources.builders.ApiSdkAuthDataBuilder;
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
    public void shouldMapModelToData() throws Exception {
        SdkAuthData sdkAuthData = SdkAuthDataBuilder.Builder().build();

        SdkAuthRealm sdkAuthRealm = mapper.modelToExternalClass(sdkAuthData);

        assertNotNull(sdkAuthRealm);
        assertEquals(ApiSdkAuthDataBuilder.VALUE, sdkAuthRealm.getValue());
        assertEquals(ApiSdkAuthDataBuilder.PROJECT_ID, sdkAuthRealm.getProjectId());
        assertEquals(ApiSdkAuthDataBuilder.EXPIRES_IN, sdkAuthRealm.getExpiresIn());

        String expectedDate = DateUtils.dateToStringWithFormat(new Date(System.currentTimeMillis() + ApiSdkAuthDataBuilder.EXPIRES_IN), DateFormatConstants.DATE_FORMAT);
        assertEquals(expectedDate, sdkAuthRealm.getExpiresAt());
    }

    @Test
    public void shouldMapDataToModel() throws Exception {
        Date expectedDate = new Date(System.currentTimeMillis() + ApiSdkAuthDataBuilder.EXPIRES_IN);

        SdkAuthRealm sdkAuthRealm = new SdkAuthRealm();
        sdkAuthRealm.setValue(ApiSdkAuthDataBuilder.VALUE);
        sdkAuthRealm.setProjectId(ApiSdkAuthDataBuilder.PROJECT_ID);
        sdkAuthRealm.setExpiresIn(ApiSdkAuthDataBuilder.EXPIRES_IN);
        sdkAuthRealm.setExpiresAt(DateUtils.dateToStringWithFormat(expectedDate, DateFormatConstants.DATE_FORMAT));

        SdkAuthData sdkAuthData = mapper.externalClassToModel(sdkAuthRealm);

        assertNotNull(sdkAuthData);
        assertEquals(ApiSdkAuthDataBuilder.VALUE, sdkAuthData.getValue());
        assertEquals(ApiSdkAuthDataBuilder.PROJECT_ID, sdkAuthData.getProjectId());
        assertEquals(ApiSdkAuthDataBuilder.EXPIRES_IN, sdkAuthData.getExpiresIn());
        assertThat(expectedDate, isDateEqualTo(sdkAuthData.getExpiresAt()));
    }
}