package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.mappers.MapperUtils;
import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.vo.GeoLocation;
import gigigo.com.orchextra.data.datasources.api.model.mappers.PointMapper;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiGeoLocation;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class GeoLocationModelToExternalClassMapper
    implements ModelToExternalClassMapper<GeoLocation, ApiGeoLocation> {

  private final PointMapper pointRequestMapper;

  public GeoLocationModelToExternalClassMapper(PointMapper pointRequestMapper) {
    this.pointRequestMapper = pointRequestMapper;
  }

  @Override public ApiGeoLocation modelToExternalClass(GeoLocation geoLocation) {
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
