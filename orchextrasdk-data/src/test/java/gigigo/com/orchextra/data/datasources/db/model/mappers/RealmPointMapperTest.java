package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.builders.PointBuilder;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;

import static org.junit.Assert.assertEquals;

public class RealmPointMapperTest {

    @Test
    public void shouldMapModelToData() throws Exception {
        OrchextraPoint point = PointBuilder.Builder().build();

        RealmPointMapper mapper = new RealmPointMapper();
        RealmPoint realmPoint = mapper.modelToExternalClass(point);

        assertEquals(PointBuilder.LAT, realmPoint.getLat(), 0.001);
        assertEquals(PointBuilder.LNG, realmPoint.getLng(), 0.001);
    }

    @Test
    public void shouldMapDataToModel() throws Exception {
        RealmPoint realmPoint = new RealmPoint();
        realmPoint.setLat(PointBuilder.LAT);
        realmPoint.setLng(PointBuilder.LNG);

        RealmPointMapper mapper = new RealmPointMapper();
        OrchextraPoint point = mapper.externalClassToModel(realmPoint);

        assertEquals(PointBuilder.LAT, point.getLat(), 0.001);
        assertEquals(PointBuilder.LNG, point.getLng(), 0.001);

    }
}