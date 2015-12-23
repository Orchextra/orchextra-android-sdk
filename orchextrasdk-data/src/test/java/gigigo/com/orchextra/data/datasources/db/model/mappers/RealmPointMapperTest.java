package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.entities.Point;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.builders.PointBuilder;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;

import static org.junit.Assert.assertEquals;

public class RealmPointMapperTest {


    @Test
    public void should_map_model_to_data() throws Exception {
        Point point = PointBuilder.Builder().build();

        RealmPointMapper mapper = new RealmPointMapper();
        RealmPoint realmPoint = mapper.modelToData(point);

        assertEquals(PointBuilder.LAT, realmPoint.getLat(), 0.001);
        assertEquals(PointBuilder.LNG, realmPoint.getLng(), 0.001);
    }

    @Test
    public void should_map_data_to_model() throws Exception {
        RealmPoint realmPoint = new RealmPoint();
        realmPoint.setLat(PointBuilder.LAT);
        realmPoint.setLng(PointBuilder.LNG);

        RealmPointMapper mapper = new RealmPointMapper();
        Point point = mapper.dataToModel(realmPoint);

        assertEquals(PointBuilder.LAT, point.getLat(), 0.001);
        assertEquals(PointBuilder.LNG, point.getLng(), 0.001);

    }
}