package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.entities.Geofence;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.builders.GeofenceBuilder;
import gigigo.com.orchextra.data.datasources.builders.PointBuilder;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;

import static org.junit.Assert.assertEquals;

public class GeofenceRealmMapperTest {

    @Test
    public void should_map_model_to_data() throws Exception {
        Geofence geofence = GeofenceBuilder.Builder().build();

        GeofenceRealmMapper mapper = new GeofenceRealmMapper(new RealmPointMapper(), new KeyWordRealmMapper());
        GeofenceRealm geofenceRealm = mapper.modelToData(geofence);

        assertEquals(PointBuilder.LAT, geofenceRealm.getPoint().getLat(), 0.0001);
        assertEquals(PointBuilder.LNG, geofenceRealm.getPoint().getLng(), 0.0001);
        assertEquals(GeofenceBuilder.RADIUS, geofenceRealm.getRadius());
        assertEquals(GeofenceBuilder.CODE, geofenceRealm.getCode());
        assertEquals(GeofenceBuilder.ID, geofenceRealm.getId());
        assertEquals(GeofenceBuilder.NAME, geofenceRealm.getName());
        assertEquals(GeofenceBuilder.STAY, geofenceRealm.getStayTime());
        assertEquals(GeofenceBuilder.TYPE.getStringValue(), geofenceRealm.getType());
        assertEquals(GeofenceBuilder.CREATEDS, geofenceRealm.getCreatedAt());
        assertEquals(GeofenceBuilder.UPDATEDS, geofenceRealm.getUpdatedAt());
        assertEquals(1, geofenceRealm.getTags().size());
        assertEquals(GeofenceBuilder.TAG_NAME, geofenceRealm.getTags().get(0).getKeyword());
    }
}