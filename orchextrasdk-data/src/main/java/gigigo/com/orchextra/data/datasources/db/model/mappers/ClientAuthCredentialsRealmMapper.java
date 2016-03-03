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
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthCredentialsRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class ClientAuthCredentialsRealmMapper
    implements Mapper<ClientAuthCredentials, ClientAuthCredentialsRealm> {
  @Override public ClientAuthCredentialsRealm modelToExternalClass(
      ClientAuthCredentials clientAuthCredentials) {
    ClientAuthCredentialsRealm clientAuthCredentialsRealm = new ClientAuthCredentialsRealm();

    clientAuthCredentialsRealm.setBluetoothMacAddress(
        clientAuthCredentials.getBluetoothMacAddress());
    clientAuthCredentialsRealm.setClientToken(clientAuthCredentials.getClientToken());
    clientAuthCredentialsRealm.setCrmId(clientAuthCredentials.getCrmId());
    clientAuthCredentialsRealm.setInstanceId(clientAuthCredentials.getInstanceId());
    clientAuthCredentialsRealm.setSecureId(clientAuthCredentials.getSecureId());
    clientAuthCredentialsRealm.setSerialNumber(clientAuthCredentials.getSerialNumber());
    clientAuthCredentialsRealm.setWifiMacAddress(clientAuthCredentials.getWifiMacAddress());

    return clientAuthCredentialsRealm;
  }

  @Override public ClientAuthCredentials externalClassToModel(
      ClientAuthCredentialsRealm clientAuthCredentialsRealm) {
    ClientAuthCredentials clientAuthCredentials =
        new ClientAuthCredentials(clientAuthCredentialsRealm.getClientToken(),
            clientAuthCredentialsRealm.getInstanceId());

    clientAuthCredentials.setBluetoothMacAddress(
        clientAuthCredentialsRealm.getBluetoothMacAddress());
    clientAuthCredentials.setCrmId(clientAuthCredentialsRealm.getCrmId());
    clientAuthCredentials.setSecureId(clientAuthCredentialsRealm.getSecureId());
    clientAuthCredentials.setSerialNumber(clientAuthCredentialsRealm.getSerialNumber());
    clientAuthCredentials.setWifiMacAddress(clientAuthCredentialsRealm.getWifiMacAddress());

    return clientAuthCredentials;
  }
}
