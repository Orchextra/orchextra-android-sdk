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
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import gigigo.com.orchextra.data.datasources.db.model.OrchextraStatusRealm;

public class OrchextraStatusRealmMapper implements Mapper<OrchextraStatus, OrchextraStatusRealm> {

  @Override public OrchextraStatus externalClassToModel(OrchextraStatusRealm data) {
    OrchextraStatus orchextraStatus = new OrchextraStatus();
    orchextraStatus.setInitialized(data.isInitialized());
    orchextraStatus.setStarted(data.isStarted());

    System.out.println("\n\n\n\nOrchextraStatusRealmMapper externalClassToModel\n\n\n\n"+data.isStarted());
    return orchextraStatus;
  }

  @Override public OrchextraStatusRealm modelToExternalClass(OrchextraStatus orchextraStatus) {

    OrchextraStatusRealm orchextraStatusRealm = new OrchextraStatusRealm();
    orchextraStatusRealm.setInitialized(orchextraStatus.isInitialized());
    orchextraStatusRealm.setStarted(orchextraStatus.isStarted());
    return orchextraStatusRealm;
  }

}
