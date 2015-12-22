package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.orchextra.domain.entities.Beacon;
import com.gigigo.orchextra.domain.entities.ProximityPointType;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import gigigo.com.orchextra.data.datasources.api.builders.ApiBeaconBuilder;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiBeacon;

import static gigigo.com.orchextra.data.testing.matchers.IsDateEqualTo.isDateEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class BeaconResponseMapperTest {

    private Date getCalendar(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
        return calendar.getTime();
    }



    @Test
    public void testDataToModelOk() throws Exception {
        ApiBeacon apiBeacon = ApiBeaconBuilder.Builder().build();

        BeaconResponseMapper mapper = new BeaconResponseMapper();
        Beacon beacon = mapper.dataToModel(apiBeacon);

        assertEquals(ApiBeaconBuilder.MAJOR, beacon.getMajor());
        assertEquals(ApiBeaconBuilder.MINOR, beacon.getMinor());
        assertEquals(ApiBeaconBuilder.UUID, beacon.getUuid());
        assertEquals(ApiBeaconBuilder.CODE, beacon.getCode());
        assertEquals(ApiBeaconBuilder.ID, beacon.getId());
        assertEquals(ApiBeaconBuilder.NAME, beacon.getName());
        assertEquals(true, beacon.isNotifyOnEntry());
        assertEquals(true, beacon.isNotifyOnExit());
        assertEquals(ApiBeaconBuilder.STAY_TIME, beacon.getStayTime());
        assertEquals(ProximityPointType.BEACON, beacon.getType());
        assertEquals(1, beacon.getTags().size());
        assertEquals(ApiBeaconBuilder.BEACON_TAG_NAME, beacon.getTags().get(0));

        Date createdAt = getCalendar(2015, Calendar.OCTOBER, 21, 11, 46, 13);
        Date updateAt = getCalendar(2015, Calendar.OCTOBER, 21, 18, 41, 39);

        assertThat(createdAt, isDateEqualTo(beacon.getCreatedAt()));
        assertThat(updateAt, isDateEqualTo(beacon.getUpdatedAt()));
    }

    @Test
    public void testDataToModelEmptyValues() throws Exception {
        ApiBeacon apiBeacon = new ApiBeacon();

        BeaconResponseMapper mapper = new BeaconResponseMapper();
        Beacon beacon = mapper.dataToModel(apiBeacon);

        assertEquals(0, beacon.getMajor());
        assertEquals(0, beacon.getMinor());
        assertNull(beacon.getUuid());
        assertNull(beacon.getCode());
        assertNull(beacon.getId());
        assertNull(beacon.getName());
        assertEquals(false, beacon.isNotifyOnEntry());
        assertEquals(false, beacon.isNotifyOnExit());
        assertEquals(0, beacon.getStayTime());
        assertNull(beacon.getType());
        assertNull(beacon.getTags());

        Date createdAt = getCalendar(1970, Calendar.JANUARY, 1, 1, 0, 0);
        Date updateAt = getCalendar(1970, Calendar.JANUARY, 1, 1, 0, 0);

        assertThat(createdAt, isDateEqualTo(beacon.getCreatedAt()));
        assertThat(updateAt, isDateEqualTo(beacon.getUpdatedAt()));
    }
}