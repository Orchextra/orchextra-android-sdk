package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.vo.OrchextraLocationPoint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.builders.GeofenceBuilder;
import gigigo.com.orchextra.data.datasources.builders.GeofenceRealmBuilder;
import gigigo.com.orchextra.data.datasources.builders.PointBuilder;
import gigigo.com.orchextra.data.datasources.builders.PointRealmBuilder;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.KeyWordRealm;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;
import io.realm.RealmList;

import static gigigo.com.orchextra.data.testing.matchers.IsDateEqualTo.isDateEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeofenceRealmMapperTest {

    @Mock RealmPointMapper realmPointMapper;

    @Mock KeyWordRealmMapper keyWordRealmMapper;

    @Test
    public void shouldMapModelToData() throws Exception {
        OrchextraGeofence geofence = GeofenceBuilder.Builder().build();

        RealmPoint realmPoint = new RealmPoint();
        realmPoint.setLat(PointBuilder.LAT);
        realmPoint.setLng(PointBuilder.LNG);
        when(realmPointMapper.modelToExternalClass(any(OrchextraLocationPoint.class))).thenReturn(realmPoint);

        RealmList<KeyWordRealm> keyWordRealmList = new RealmList<>();
        KeyWordRealm keyWordRealm = new KeyWordRealm();
        keyWordRealm.setKeyword(GeofenceBuilder.TAG_NAME);
        keyWordRealmList.add(keyWordRealm);
        when(keyWordRealmMapper.stringKeyWordsToRealmList(geofence.getTags())).thenReturn(keyWordRealmList);

        GeofenceRealmMapper mapper = new GeofenceRealmMapper(realmPointMapper, keyWordRealmMapper);
        GeofenceRealm geofenceRealm = mapper.modelToExternalClass(geofence);

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

        OrchextraLocationPoint realmPoint = new OrchextraLocationPoint();
        realmPoint.setLat(PointRealmBuilder.LAT);
        realmPoint.setLng(PointRealmBuilder.LNG);
        when(realmPointMapper.externalClassToModel(any(RealmPoint.class))).thenReturn(realmPoint);

        List<String> keyWordList = new ArrayList<>();
        keyWordList.add(GeofenceRealmBuilder.TAG_NAME);
        when(keyWordRealmMapper.realmKeyWordsToStringList(geofenceRealm.getTags())).thenReturn(keyWordList);

        GeofenceRealmMapper mapper = new GeofenceRealmMapper(realmPointMapper, keyWordRealmMapper);
        OrchextraGeofence geofence = mapper.externalClassToModel(geofenceRealm);

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