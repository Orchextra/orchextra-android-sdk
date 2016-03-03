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

package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiPoint;


public class PointMapper implements Mapper<OrchextraPoint, ApiPoint> {

  @Override public ApiPoint modelToExternalClass(OrchextraPoint point) {
    ApiPoint apiPoint = new ApiPoint();

    apiPoint.setLat(String.valueOf(point.getLat()));
    apiPoint.setLng(String.valueOf(point.getLng()));

    return apiPoint;
  }

  @Override public OrchextraPoint externalClassToModel(ApiPoint apiPoint) {
    OrchextraPoint point = new OrchextraPoint();

    Double lat, lon;

    try {

      lat = Double.valueOf(apiPoint.getLat());
      lon = Double.valueOf(apiPoint.getLng());
    } catch (NumberFormatException nfe) {
      lat = lon = 0.0;
    }

    point.setLat(lat);
    point.setLng(lon);

    return point;
  }
}
