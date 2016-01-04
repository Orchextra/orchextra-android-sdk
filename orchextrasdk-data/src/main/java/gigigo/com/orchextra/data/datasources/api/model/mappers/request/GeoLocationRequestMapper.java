package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.network.mappers.MapperUtils;
import com.gigigo.ggglib.network.mappers.RequestMapper;
import com.gigigo.orchextra.domain.entities.GeoLocation;
import gigigo.com.orchextra.data.datasources.api.model.mappers.PointMapper;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiGeoLocation;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class GeoLocationRequestMapper implements RequestMapper<GeoLocation, ApiGeoLocation> {

  private final PointMapper pointRequestMapper;

  public GeoLocationRequestMapper(PointMapper pointRequestMapper) {
    this.pointRequestMapper = pointRequestMapper;
  }

  @Override public ApiGeoLocation modelToData(GeoLocation geoLocation) {
    ApiGeoLocation apiGeoLocation = new ApiGeoLocation();

    apiGeoLocation.setCountry(geoLocation.getCountry());
    apiGeoLocation.setCountryCode(geoLocation.getCountryCode());
    apiGeoLocation.setLocality(geoLocation.getLocality());
    apiGeoLocation.setStreet(geoLocation.getStreet());
    apiGeoLocation.setZip(geoLocation.getZip());

    apiGeoLocation.setPoint(MapperUtils.checkNullDataRequest(
        pointRequestMapper, geoLocation.getPoint()));

    return apiGeoLocation;
  }

}
