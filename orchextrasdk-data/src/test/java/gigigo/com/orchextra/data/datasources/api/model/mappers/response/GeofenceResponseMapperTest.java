package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.builders.ApiGeofenceBuilder;
import gigigo.com.orchextra.data.datasources.api.model.mappers.PointMapper;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiGeofence;

import static org.junit.Assert.assertEquals;

public class GeofenceResponseMapperTest {

    @Test
    public void testDataToModelOk() throws Exception {
        ApiGeofence apiGeofence = ApiGeofenceBuilder.Builder().build();

        GeofenceExternalClassToModelMapper mapper = new GeofenceExternalClassToModelMapper(new PointMapper());
        OrchextraGeofence geofence = mapper.externalClassToModel(apiGeofence);

        assertEquals(ApiGeofenceBuilder.RADIUS, geofence.getRadius());
        assertEquals(ApiGeofenceBuilder.LAT, String.valueOf(geofence.getPoint().getLat()));
        assertEquals(ApiGeofenceBuilder.LNG, String.valueOf(geofence.getPoint().getLng()));
    }
}