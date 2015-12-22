package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.entities.ClientAuthCredentials;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthCredentialsRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class ClientAuthCredentialsRealmMapper implements
    RealmMapper<ClientAuthCredentials, ClientAuthCredentialsRealm> {
  @Override
  public ClientAuthCredentialsRealm modelToData(ClientAuthCredentials clientAuthCredentials) {
    ClientAuthCredentialsRealm clientAuthCredentialsRealm = new ClientAuthCredentialsRealm();

    clientAuthCredentialsRealm.setAdvertiserId(clientAuthCredentials.getAdvertiserId());
    clientAuthCredentialsRealm.setBluetoothMacAddress(
        clientAuthCredentials.getBluetoothMacAddress());
    clientAuthCredentialsRealm.setClientToken(clientAuthCredentials.getClientToken());
    clientAuthCredentialsRealm.setCrmId(clientAuthCredentials.getCrmId());
    clientAuthCredentialsRealm.setInstanceId(clientAuthCredentials.getInstanceId());
    clientAuthCredentialsRealm.setSecureId(clientAuthCredentials.getSecureId());
    clientAuthCredentialsRealm.setSerialNumber(clientAuthCredentials.getSerialNumber());
    clientAuthCredentialsRealm.setVendorId(clientAuthCredentials.getVendorId());
    clientAuthCredentialsRealm.setWifiMacAddress(clientAuthCredentials.getWifiMacAddress());

    return clientAuthCredentialsRealm;
  }

  @Override
  public ClientAuthCredentials dataToModel(ClientAuthCredentialsRealm clientAuthCredentialsRealm) {
    ClientAuthCredentials clientAuthCredentials = new ClientAuthCredentials(
        clientAuthCredentialsRealm.getClientToken(), clientAuthCredentialsRealm.getInstanceId(),
        clientAuthCredentialsRealm.getVendorId());

    clientAuthCredentials.setAdvertiserId(clientAuthCredentialsRealm.getAdvertiserId());
    clientAuthCredentials.setBluetoothMacAddress(clientAuthCredentialsRealm.getBluetoothMacAddress());
    clientAuthCredentials.setCrmId(clientAuthCredentialsRealm.getCrmId());
    clientAuthCredentials.setSecureId(clientAuthCredentialsRealm.getSecureId());
    clientAuthCredentials.setSerialNumber(clientAuthCredentialsRealm.getSerialNumber());
    clientAuthCredentials.setWifiMacAddress(clientAuthCredentialsRealm.getWifiMacAddress());

    return clientAuthCredentials;
  }
}
