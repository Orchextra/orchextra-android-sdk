package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.entities.Geofence;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.builders.GeofenceBuilder;
import gigigo.com.orchextra.data.datasources.builders.GeofenceRealmBuilder;
import gigigo.com.orchextra.data.datasources.builders.PointBuilder;
import gigigo.com.orchextra.data.datasources.builders.PointRealmBuilder;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;

import static gigigo.com.orchextra.data.testing.matchers.IsDateEqualTo.isDateEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GeofenceRealmMapperTest {

    @Test
    public void shouldMapModelToData() throws Exception {
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

    @Test
    public void shouldMapDataToModel() throws Exception {
        GeofenceRealm geofenceRealm = GeofenceRealmBuilder.Builder().build();

        GeofenceRealmMapper mapper = new GeofenceRealmMapper(new RealmPointMapper(), new KeyWordRealmMapper());
        Geofence geofence = mapper.dataToModel(geofenceRealm);

        assertEquals(PointRealmBuilder.LAT, geofence.getPoint().getLat(), 0.0001);
        assertEquals(PointRealmBuilder.LNG, geofence.getPoint().getLng(), 0.0001);
        assertEquals(GeofenceRealmBuilder.RADIUS, geofence.getRadius());
        assertEquals(GeofenceRealmBuilder.CODE, geofence.getCode());
        assertEquals(GeofenceRealmBuilder.ID, geofence.getId());
        assertEquals(GeofenceRealmBuilder.NAME, geofence.getName());
        assertEquals(GeofenceRealmBuilder.STAY, geofence.getStayTime());
        assertEquals(GeofenceRealmBuilder.TYPE, geofenceRealm.getType());
        assertThat(GeofenceRealmBuilder.CREATED, isDateEqualTo(geofence.getCreatedAt()));
        assertThat(GeofenceRealmBuilder.UPDATED, isDateEqualTo(geofence.getUpdatedAt()));
        assertEquals(1, geofence.getTags().size());
        assertEquals(GeofenceRealmBuilder.TAG_NAME, geofence.getTags().get(0));

    }
}