package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.orchextra.domain.entities.Point;
import com.gigigo.orchextra.domain.entities.triggers.BeaconDistanceType;
import com.gigigo.orchextra.domain.entities.triggers.BeaconTrigger;
import com.gigigo.orchextra.domain.entities.triggers.GeoPointEventType;
import com.gigigo.orchextra.domain.entities.triggers.PhoneStatusType;
import com.gigigo.orchextra.domain.entities.triggers.ScanTrigger;
import com.gigigo.orchextra.domain.entities.triggers.Trigger;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ActionQueryRequestMapperTest {

    @Test
    public void testCreateGeofenceTrigger() throws Exception {
        Point point = new Point();
        point.setLat(23.45);
        point.setLng(74.32);
        Trigger trigger = Trigger.createGeofenceTrigger("1234", point, PhoneStatusType.BACKGROUND, 300, GeoPointEventType.ENTER);

        ActionQueryRequestMapper mapper = new ActionQueryRequestMapper();
        Map<String, String> data = mapper.modelToData(trigger);

        assertEquals("1234", data.get("value"));
        assertEquals("geofence", data.get("type"));
        assertEquals("300.0", data.get("distance"));
        assertEquals("background", data.get("phoneStatus"));
        assertEquals("23.45", data.get("lat"));
        assertEquals("74.32", data.get("lng"));
    }

    @Test
    public void testCreateBeaconTrigger() throws Exception {
        Point point = new Point();
        point.setLat(23.45);
        point.setLng(74.32);

        BeaconTrigger beaconTrigger = Trigger.createBeaconTrigger("1234", point, PhoneStatusType.FOREGROUND, BeaconDistanceType.NEAR, GeoPointEventType.EXIT);

        ActionQueryRequestMapper mapper = new ActionQueryRequestMapper();
        Map<String, String> data = mapper.modelToData(beaconTrigger);

        assertEquals("1234", data.get("value"));
        assertEquals("beacon", data.get("type"));
        assertEquals("exit", data.get("event"));
        assertEquals("foreground", data.get("phoneStatus"));
        assertEquals("23.45", data.get("lat"));
        assertEquals("74.32", data.get("lng"));
    }

    @Test
    public void testCreateQrScanTrigger() throws Exception {
        Point point = new Point();
        point.setLat(23.45);
        point.setLng(74.32);

        ScanTrigger qrScanTrigger = Trigger.createQrScanTrigger("1234", point);

        ActionQueryRequestMapper mapper = new ActionQueryRequestMapper();
        Map<String, String> data = mapper.modelToData(qrScanTrigger);

        assertEquals("1234", data.get("value"));
        assertEquals("qr", data.get("type"));
        assertEquals("foreground", data.get("phoneStatus"));
        assertEquals("23.45", data.get("lat"));
        assertEquals("74.32", data.get("lng"));
    }

    @Test
     public void testCreateBarcodeScanTrigger() throws Exception {
        Point point = new Point();
        point.setLat(23.45);
        point.setLng(74.32);

        ScanTrigger barcodeScanTrigger = Trigger.createBarcodeScanTrigger("1234", point);

        ActionQueryRequestMapper mapper = new ActionQueryRequestMapper();
        Map<String, String> data = mapper.modelToData(barcodeScanTrigger);

        assertEquals("1234", data.get("value"));
        assertEquals("barcode", data.get("type"));
        assertEquals("foreground", data.get("phoneStatus"));
        assertEquals("23.45", data.get("lat"));
        assertEquals("74.32", data.get("lng"));
    }

    @Test
    public void testCreateVuforiaScanTrigger() throws Exception {
        Point point = new Point();
        point.setLat(23.45);
        point.setLng(74.32);

        ScanTrigger vuforiaScanTrigger = Trigger.createVuforiaScanTrigger("1234", point);

        ActionQueryRequestMapper mapper = new ActionQueryRequestMapper();
        Map<String, String> data = mapper.modelToData(vuforiaScanTrigger);

        assertEquals("1234", data.get("value"));
        assertEquals("vuforia", data.get("type"));
        assertEquals("foreground", data.get("phoneStatus"));
        assertEquals("23.45", data.get("lat"));
        assertEquals("74.32", data.get("lng"));
    }
}