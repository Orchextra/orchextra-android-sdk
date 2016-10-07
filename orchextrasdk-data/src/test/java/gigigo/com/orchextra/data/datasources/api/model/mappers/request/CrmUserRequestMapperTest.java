package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.GenderType;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gigigo.com.orchextra.data.datasources.api.model.requests.ApiCrmUser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CrmUserRequestMapperTest {

    private java.util.Date getCalendar(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
        return calendar.getTime();
    }

    @Test
    public void testModelToDataMaleWithKeywords() throws Exception {
        List<String> keywords = new ArrayList<>();
        keywords.add("palabra");
        CrmUser crmUser = new CrmUser("1234", GenderType.MALE, getCalendar(2012, 9, 21, 11, 22, 34));

        CrmModelToExternalClassMapper mapper = new CrmModelToExternalClassMapper();
        ApiCrmUser apiCrmUser = mapper.modelToExternalClass(crmUser);

        assertEquals("1234", apiCrmUser.getCrmId());
        assertEquals("m", apiCrmUser.getGender());
        assertEquals("2012-10-21", apiCrmUser.getBirthDate());

    }

    @Test
    public void testModelToDataFeMaleWithoutKeywords() throws Exception {
        CrmUser crmUser = new CrmUser("1234", GenderType.FEMALE, getCalendar(2012, 9, 21, 11, 22, 34));

        CrmModelToExternalClassMapper mapper = new CrmModelToExternalClassMapper();
        ApiCrmUser apiCrmUser = mapper.modelToExternalClass(crmUser);

        assertEquals("1234", apiCrmUser.getCrmId());
        assertEquals("f", apiCrmUser.getGender());
        assertEquals("2012-10-21", apiCrmUser.getBirthDate());
    }

    @Test
    public void testModelToDataNDAndNullKeywords() throws Exception {
        CrmUser crmUser = new CrmUser("1234", GenderType.ND, getCalendar(1970, 0, 01, 01, 00, 00));

        CrmModelToExternalClassMapper mapper = new CrmModelToExternalClassMapper();
        ApiCrmUser apiCrmUser = mapper.modelToExternalClass(crmUser);

        assertEquals("1234", apiCrmUser.getCrmId());
        assertEquals("n", apiCrmUser.getGender());
        assertEquals("1970-01-01", apiCrmUser.getBirthDate());
    }
}
