/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.mappers.MapperUtils;
import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.vo.GeoLocation;
import gigigo.com.orchextra.data.datasources.api.model.mappers.PointMapper;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiGeoLocation;


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

    apiGeoLocation.setPoint(
        MapperUtils.checkNullDataRequest(pointRequestMapper, geoLocation.getPoint()));

    return apiGeoLocation;
  }
}
