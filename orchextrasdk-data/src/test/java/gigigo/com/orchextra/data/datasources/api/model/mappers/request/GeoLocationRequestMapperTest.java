package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.orchextra.domain.model.vo.GeoLocation;
import com.gigigo.orchextra.domain.model.vo.OrchextraLocationPoint;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.api.model.mappers.PointMapper;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiGeoLocation;

import static org.junit.Assert.assertEquals;

public class GeoLocationRequestMapperTest {

    @Test
    public void testModelToDataOk() throws Exception {
        OrchextraLocationPoint point = new OrchextraLocationPoint();
        point.setLat(3.45);
        point.setLng(5.67);

        GeoLocation geoLocation = new GeoLocation();
        geoLocation.setPoint(point);
        geoLocation.setStreet("Calle Alcala, 345");
        geoLocation.setLocality("Madrid");
        geoLocation.setZip("28027");
        geoLocation.setCountry("Spain");
        geoLocation.setCountryCode("34");

        GeoLocationModelToExternalClassMapper mapper = new GeoLocationModelToExternalClassMapper(new PointMapper());
        ApiGeoLocation apiGeoLocation = mapper.modelToExternalClass(geoLocation);

        assertEquals("Calle Alcala, 345", apiGeoLocation.getStreet());
        assertEquals("Madrid", apiGeoLocation.getLocality());
        assertEquals("28027", apiGeoLocation.getZip());
        assertEquals("Spain", apiGeoLocation.getCountry());
        assertEquals("34", apiGeoLocation.getCountryCode());
        assertEquals("3.45", apiGeoLocation.getPoint().getLat());
        assertEquals("5.67", apiGeoLocation.getPoint().getLng());
    }
}