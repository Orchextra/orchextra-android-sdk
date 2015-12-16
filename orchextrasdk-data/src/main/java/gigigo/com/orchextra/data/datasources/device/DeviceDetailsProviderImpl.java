package gigigo.com.orchextra.data.datasources.device;

import android.content.Context;

import com.gigigo.ggglib.device.DeviceInfoProvider;
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
    return DeviceInfoProvider.getVendorId();
  }

  @Override public String getAndroidInstanceId() {
    return DeviceInfoProvider.getAndroidInstanceId(context);
  }

  @Override public String getAndroidSecureId() {
    return DeviceInfoProvider.getAndroidSecureId(context);
  }

  @Override public String getAndroidSerialNumber() {
    return DeviceInfoProvider.getAndroidSerialNumber();
  }

  @Override public String getWifiMac() {
    return DeviceInfoProvider.getWifiMac(context);
  }

  @Override public String getBluetoothMac() {
    return DeviceInfoProvider.getBluetoothMac();
  }
}
