package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.orchextra.domain.entities.Point;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiPoint;

import static org.junit.Assert.assertEquals;

public class PointMapperTest {

    @Test
    public void testModelToData() throws Exception {
        Point point = new Point();
        point.setLat(34.45);
        point.setLng(42.42);

        PointMapper mapper = new PointMapper();
        ApiPoint apiPoint = mapper.modelToData(point);

        assertEquals(apiPoint.getLat(), String.valueOf(point.getLat()));
        assertEquals(apiPoint.getLng(), String.valueOf(point.getLng()));
    }

    @Test
    public void testDataToModel() throws Exception {
        ApiPoint apiPoint = new ApiPoint();
        apiPoint.setLat("67.43");
        apiPoint.setLng("45.54");

        PointMapper mapper = new PointMapper();
        Point point = mapper.dataToModel(apiPoint);

        assertEquals(String.valueOf(point.getLat()), apiPoint.getLat());
        assertEquals(String.valueOf(point.getLng()), apiPoint.getLng());
    }

    @Test
    public void testDataToModelBadFormat() throws Exception {
        ApiPoint apiPoint = new ApiPoint();
        apiPoint.setLat("Prueba");
        apiPoint.setLng("Prueba");

        PointMapper mapper = new PointMapper();
        Point point = mapper.dataToModel(apiPoint);

        assertEquals(point.getLat(), 0.0, 0.001);
        assertEquals(point.getLng(), 0.0, 0.001);
    }
}