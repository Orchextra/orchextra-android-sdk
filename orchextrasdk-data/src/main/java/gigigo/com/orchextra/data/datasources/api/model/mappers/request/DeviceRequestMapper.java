package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.network.mappers.RequestMapper;
import com.gigigo.orchextra.domain.entities.Device;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiDevice;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class DeviceRequestMapper implements RequestMapper<Device, ApiDevice>{

  @Override public ApiDevice modelToData(Device device) {
    ApiDevice apiDevice = new ApiDevice();

    apiDevice.setInstanceId(device.getInstanceId());
    apiDevice.setLanguage(device.getLanguage());
    apiDevice.setOs(device.getOs());
    apiDevice.setOsVersion(device.getOsVersion());
    apiDevice.setSecureId(device.getSecureId());
    apiDevice.setSerialNumber(device.getSerialNumber());
    apiDevice.setTimeZone(device.getTimeZone());
    apiDevice.setVendorId(device.getVendorId());
    apiDevice.setWifiMacAddress(device.getWifiMacAddress());
    apiDevice.setHandset(device.getHandset());
    apiDevice.setBluetoothMacAddress(device.getBluetoothMacAddress());

    return apiDevice;
  }

}
