package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import gigigo.com.orchextra.data.datasources.builders.ApiRegionBuilder;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiBeaconRegion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BeaconResponseMapperTest {

    private Date getCalendar(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
        return calendar.getTime();
    }



    @Test
    public void testDataToModelOk() throws Exception {
        ApiBeaconRegion apiBeaconRegion = ApiRegionBuilder.Builder().build();

        BeaconExternalClassToModelMapper mapper = new BeaconExternalClassToModelMapper();
        OrchextraRegion region = mapper.externalClassToModel(apiBeaconRegion);

        assertEquals(ApiRegionBuilder.MAJOR, region.getMajor());
        assertEquals(ApiRegionBuilder.MINOR, region.getMinor());
        assertEquals(ApiRegionBuilder.UUID, region.getUuid());
        assertEquals(ApiRegionBuilder.CODE, region.getCode());
        assertEquals(true, region.isNotifyOnEntry());
        assertEquals(true, region.isNotifyOnExit());
        assertEquals(1, region.getTags().size());
        assertEquals(ApiRegionBuilder.BEACON_TAG_NAME, region.getTags().get(0));
    }

    @Test
    public void testDataToModelEmptyValues() throws Exception {
        ApiBeaconRegion apiBeaconRegion = new ApiBeaconRegion();

        BeaconExternalClassToModelMapper mapper = new BeaconExternalClassToModelMapper();
        OrchextraRegion region = mapper.externalClassToModel(apiBeaconRegion);

        assertEquals(-1, region.getMajor());
        assertEquals(-1, region.getMinor());
        assertNull(region.getUuid());
        assertNull(region.getCode());
        assertNull(region.getId());
        assertNull(region.getName());
        assertEquals(false, region.isNotifyOnEntry());
        assertEquals(false, region.isNotifyOnExit());
        assertEquals(0, region.getStayTime());
        assertNull(region.getType());
        assertNull(region.getTags());

        assertNull(region.getCreatedAt());
        assertNull(region.getUpdatedAt());
    }
}