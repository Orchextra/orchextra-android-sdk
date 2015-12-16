package gigigo.com.orchextra.data.datasources.device;

import com.gigigo.orchextra.domain.device.DeviceDetailsProvider;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public class DeviceDetailsFakeProviderImpl implements DeviceDetailsProvider {
  //TODO this class MUST be deleted when DeviceDetatilsProviderImpl was ready
  
  @Override public String getVendorId() {
    return "iouweoi-vendor";
  }

  @Override public String getAndroidInstanceId() {
    return "iouweoi-vendor";
  }

  @Override public String getAndroidSecureId() {
    return "dfksdjf300304mmdfm05i30mvm";
  }

  @Override public String getAndroidSerialNumber() {
    return "fsfdhas8i3jfjdsh84u3irjdfj49olklskd9ewj";
  }

  @Override public String getWifiMac() {
    return "00:50:F2:7E:2F:9B";
  }

  @Override public String getBluetoothMac() {
    return "00:50:F2:7E:2F:9B";
  }
}
