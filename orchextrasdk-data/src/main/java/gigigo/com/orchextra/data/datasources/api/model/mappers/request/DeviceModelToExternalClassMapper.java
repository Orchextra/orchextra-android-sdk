package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.vo.Device;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiDevice;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class DeviceModelToExternalClassMapper
    implements ModelToExternalClassMapper<Device, ApiDevice> {

  @Override public ApiDevice modelToExternalClass(Device device) {
    ApiDevice apiDevice = new ApiDevice();

    apiDevice.setInstanceId(device.getInstanceId());
    apiDevice.setLanguage(device.getLanguage());
    apiDevice.setOsVersion(device.getOsVersion());
    apiDevice.setSecureId(device.getSecureId());
    apiDevice.setSerialNumber(device.getSerialNumber());
    apiDevice.setTimeZone(device.getTimeZone());
    apiDevice.setWifiMacAddress(device.getWifiMacAddress());
    apiDevice.setHandset(device.getHandset());
    apiDevice.setBluetoothMacAddress(device.getBluetoothMacAddress());

    return apiDevice;
  }
}
