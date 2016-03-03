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

package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;


public class RealmPointMapper implements Mapper<OrchextraPoint, RealmPoint> {

  @Override public RealmPoint modelToExternalClass(OrchextraPoint point) {
    RealmPoint realmPoint = new RealmPoint();
    realmPoint.setLat(point.getLat());
    realmPoint.setLng(point.getLng());
    return realmPoint;
  }

  @Override public OrchextraPoint externalClassToModel(RealmPoint realmPoint) {
    OrchextraPoint point = new OrchextraPoint();
    point.setLat(realmPoint.getLat());
    point.setLng(realmPoint.getLng());
    return point;
  }
}
