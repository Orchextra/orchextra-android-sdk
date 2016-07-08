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

package gigigo.com.orchextra.data.datasources.db.auth;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.CrmUserRealm;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.CrmUserRealmMapper;
import io.realm.Realm;
import io.realm.RealmResults;


public class SessionReader {

  private final ExternalClassToModelMapper<SdkAuthRealm, SdkAuthData> sdkAuthRealmMapper;
  private final ExternalClassToModelMapper<ClientAuthRealm, ClientAuthData> clientAuthRealmMapper;
  private final CrmUserRealmMapper crmUserRealmMapper;
  private final OrchextraLogger orchextraLogger;

  public SessionReader(ExternalClassToModelMapper sdkAuthRealmMapper,
      ExternalClassToModelMapper clientAuthRealmMapper, CrmUserRealmMapper crmUserRealmMapper,
      OrchextraLogger orchextraLogger) {

    this.sdkAuthRealmMapper = sdkAuthRealmMapper;
    this.clientAuthRealmMapper = clientAuthRealmMapper;
    this.crmUserRealmMapper = crmUserRealmMapper;
    this.orchextraLogger = orchextraLogger;

  }

  public ClientAuthData readClientAuthData(Realm realm) throws NullPointerException {
    RealmResults<ClientAuthRealm> clientAuthRealm = realm.where(ClientAuthRealm.class).findAll();
    if (clientAuthRealm.size() > 0) {
      orchextraLogger.log("Client Session found");
      return clientAuthRealmMapper.externalClassToModel(clientAuthRealm.first());
    } else {
      orchextraLogger.log("Client Session not found");
      throw new NotFountRealmObjectException();
    }
  }

  public SdkAuthData readSdkAuthData(Realm realm) throws NullPointerException {
    RealmResults<SdkAuthRealm> sdkAuthRealm = realm.where(SdkAuthRealm.class).findAll();
    if (sdkAuthRealm.size() > 0) {
      orchextraLogger.log("Sdk Session found");
      return sdkAuthRealmMapper.externalClassToModel(sdkAuthRealm.first());
    } else {
      orchextraLogger.log("Sdk Session not found");
      throw new NotFountRealmObjectException();
    }
  }

  public CrmUser readCrm(Realm realm) {
    RealmResults<CrmUserRealm> crmUserRealm = realm.where(CrmUserRealm.class).findAll();
    if (crmUserRealm.size() > 0) {
      orchextraLogger.log("CRM_ID found");
      return crmUserRealmMapper.externalClassToModel(crmUserRealm.first());
    } else {
      orchextraLogger.log("CRM_ID not found");
      throw new NotFountRealmObjectException();
    }
  }
}
