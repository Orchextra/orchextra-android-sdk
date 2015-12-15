package gigigo.com.orchextra.data.datasources.device;

import android.content.Context;
import com.gigigo.orchextra.domain.device.DeviceDetailsProvider;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public class DeviceDetailsProviderImpl implements DeviceDetailsProvider {

  private Context context;

  public DeviceDetailsProviderImpl(Context context) {
    this.context = context;
  }

  @Override public String getVendorId() {
    //TODO Implement using gggLib
    return null;
  }

  @Override public String getAndroidInstanceId() {
    //TODO Implement using gggLib
    return null;
  }

  @Override public String getAndroidCrmId() {
    //TODO Implement using gggLib
    return null;
  }

  @Override public String getAndroidSecureId() {
    //TODO Implement using gggLib
    return null;
  }

  @Override public String getAndroidSerialNumber() {
    //TODO Implement using gggLib
    return null;
  }

  @Override public String getWifiMac() {
    //TODO Implement using gggLib
    return null;
  }

  @Override public String bluetoothMac() {
    //TODO Implement using gggLib
    return null;
  }
}
