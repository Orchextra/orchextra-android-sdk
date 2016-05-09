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

import com.gigigo.gggjavalib.general.utils.DateFormatConstants;
import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthRealm;


public class ClientAuthRealmMapper implements Mapper<ClientAuthData, ClientAuthRealm> {

  @Override public ClientAuthRealm modelToExternalClass(ClientAuthData clientAuthData) {
    ClientAuthRealm clientAuthRealm = new ClientAuthRealm();
    clientAuthRealm.setExpiresAt(DateUtils.dateToStringWithFormat(clientAuthData.getExpiresAt(),
        DateFormatConstants.DATE_FORMAT_TIME));
    clientAuthRealm.setExpiresIn(clientAuthData.getExpiresIn());
    clientAuthRealm.setProjectId(clientAuthData.getProjectId());
    clientAuthRealm.setUserId(clientAuthData.getUserId());
    clientAuthRealm.setValue(clientAuthData.getValue());
    return clientAuthRealm;
  }

  @Override public ClientAuthData externalClassToModel(ClientAuthRealm clientAuthRealm) {

    ClientAuthData clientAuthData =
        new ClientAuthData(clientAuthRealm.getProjectId(), clientAuthRealm.getUserId(),
            clientAuthRealm.getValue(), clientAuthRealm.getExpiresIn(),
            DateUtils.stringToDateWithFormat(clientAuthRealm.getExpiresAt(),
                DateFormatConstants.DATE_FORMAT_TIME));

    return clientAuthData;
  }
}
