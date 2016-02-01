package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.orchextra.domain.entities.Device;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.api.model.requests.ApiDevice;

import static org.junit.Assert.assertEquals;

public class DeviceRequestMapperTest {

    @Test
    public void testModelToDataOk() throws Exception {
        Device device = new Device();
        device.setBluetoothMacAddress("22:33:22:22:33:22");
        device.setHandset("Phone");
        device.setInstanceId("1324");
        device.setLanguage("Spanish");
        device.setOs("Android");
        device.setOsVersion("4.4");
        device.setSecureId("111");
        device.setSerialNumber("12345");
        device.setTimeZone("Madrid");
        device.setVendorId("VenId");
        device.setWifiMacAddress("11:22:11:11:22:11");

        DeviceRequestMapper mapper = new DeviceRequestMapper();
        ApiDevice apiDevice = mapper.modelToData(device);

        assertEquals("22:33:22:22:33:22",apiDevice.getBluetoothMacAddress());
        assertEquals("Phone",apiDevice.getHandset());
        assertEquals("1324",apiDevice.getInstanceId());
        assertEquals("Spanish",apiDevice.getLanguage());
        assertEquals("Android",apiDevice.getOs());
        assertEquals("4.4",apiDevice.getOsVersion());
        assertEquals("111",apiDevice.getSecureId());
        assertEquals("12345",apiDevice.getSerialNumber());
        assertEquals("Madrid",apiDevice.getTimeZone());
        assertEquals("VenId",apiDevice.getVendorId());
        assertEquals("11:22:11:11:22:11",apiDevice.getWifiMacAddress());

    }
}