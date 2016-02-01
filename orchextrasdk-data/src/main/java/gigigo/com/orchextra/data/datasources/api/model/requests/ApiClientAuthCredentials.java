package gigigo.com.orchextra.data.datasources.api.model.requests;

import com.gigigo.gggjavalib.general.utils.ConsistencyUtils;
import com.gigigo.orchextra.domain.entities.ClientAuthCredentials;
import com.gigigo.orchextra.domain.entities.Credentials;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public class ApiClientAuthCredentials implements ApiCredentials{

  @Expose @SerializedName("clientToken")
  private final String clientToken;
  @Expose @SerializedName("instanceId")
  private final String instanceId;

  @Expose @SerializedName("secureId")
  private final String secureId;
  @Expose @SerializedName("crmId")
  private final String crmId;
  @Expose @SerializedName("serialNumber")
  private final String serialNumber;
  @Expose @SerializedName("wifiMacAddress")
  private final String wifiMacAddress;
  @Expose @SerializedName("bluetoothMacAddress")
  private final String bluetoothMacAddress;


  public ApiClientAuthCredentials(Credentials credentials) {

    ClientAuthCredentials clientCredentials = ConsistencyUtils.checkInstance(credentials,
            ClientAuthCredentials.class);

    this.clientToken = clientCredentials.getClientToken();
    this.instanceId = clientCredentials.getInstanceId();
    this.secureId = clientCredentials.getSecureId();
    this.crmId = clientCredentials.getCrmId();
    this.serialNumber = clientCredentials.getSerialNumber();
    this.wifiMacAddress = clientCredentials.getWifiMacAddress();
    this.bluetoothMacAddress = clientCredentials.getBluetoothMacAddress();
  }

  public String getClientToken() {
    return clientToken;
  }

  public String getInstanceId() {
    return instanceId;
  }

  public String getSecureId() {
    return secureId;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public String getWifiMacAddress() {
    return wifiMacAddress;
  }

  public String getBluetoothMacAddress() {
    return bluetoothMacAddress;
  }

  public String getCrmId() {
    return crmId;
  }
}
