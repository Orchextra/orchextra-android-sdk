package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.gigigo.orchextra.domain.model.triggers.params.BeaconDistanceType;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.BeaconTrigger;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.ScanTrigger;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ActionQueryRequestMapperTest {

    @Test
    public void testCreateGeofenceTrigger() throws Exception {
        OrchextraPoint point = new OrchextraPoint();
        point.setLat(23.45);
        point.setLng(74.32);
        Trigger trigger = Trigger.createGeofenceTrigger("1234", point, AppRunningModeType.BACKGROUND, 300, GeoPointEventType.ENTER);

        ActionQueryModelToExternalClassMapper mapper = new ActionQueryModelToExternalClassMapper();
        Map<String, String> data = mapper.modelToExternalClass(trigger);

        assertEquals("1234", data.get("value"));
        assertEquals("geofence", data.get("type"));
        assertEquals("300.0", data.get("distance"));
        assertEquals("background", data.get("phoneStatus"));
        assertEquals("23.45", data.get("lat"));
        assertEquals("74.32", data.get("lng"));
    }

    @Test
    public void testCreateBeaconTrigger() throws Exception {
        OrchextraPoint point = new OrchextraPoint();
        point.setLat(23.45);
        point.setLng(74.32);

        BeaconTrigger beaconTrigger = Trigger.createBeaconTrigger("1234", point, AppRunningModeType.FOREGROUND, BeaconDistanceType.NEAR, GeoPointEventType.EXIT);

        ActionQueryModelToExternalClassMapper mapper = new ActionQueryModelToExternalClassMapper();
        Map<String, String> data = mapper.modelToExternalClass(beaconTrigger);

        assertEquals("1234", data.get("value"));
        assertEquals("beacon", data.get("type"));
        assertEquals("exit", data.get("event"));
        assertEquals("foreground", data.get("phoneStatus"));
        assertEquals("23.45", data.get("lat"));
        assertEquals("74.32", data.get("lng"));
    }

    @Test
    public void testCreateQrScanTrigger() throws Exception {
        OrchextraPoint point = new OrchextraPoint();
        point.setLat(23.45);
        point.setLng(74.32);

        ScanTrigger qrScanTrigger = Trigger.createQrScanTrigger("1234", point);

        ActionQueryModelToExternalClassMapper mapper = new ActionQueryModelToExternalClassMapper();
        Map<String, String> data = mapper.modelToExternalClass(qrScanTrigger);

        assertEquals("1234", data.get("value"));
        assertEquals("qr", data.get("type"));
        assertEquals("foreground", data.get("phoneStatus"));
        assertEquals("23.45", data.get("lat"));
        assertEquals("74.32", data.get("lng"));
    }

    @Test
     public void testCreateBarcodeScanTrigger() throws Exception {
        OrchextraPoint point = new OrchextraPoint();
        point.setLat(23.45);
        point.setLng(74.32);

        ScanTrigger barcodeScanTrigger = Trigger.createBarcodeScanTrigger("1234", point);

        ActionQueryModelToExternalClassMapper mapper = new ActionQueryModelToExternalClassMapper();
        Map<String, String> data = mapper.modelToExternalClass(barcodeScanTrigger);

        assertEquals("1234", data.get("value"));
        assertEquals("barcode", data.get("type"));
        assertEquals("foreground", data.get("phoneStatus"));
        assertEquals("23.45", data.get("lat"));
        assertEquals("74.32", data.get("lng"));
    }

    @Test
    public void testCreateVuforiaScanTrigger() throws Exception {
        OrchextraPoint point = new OrchextraPoint();
        point.setLat(23.45);
        point.setLng(74.32);

        ScanTrigger vuforiaScanTrigger = Trigger.createVuforiaScanTrigger("1234", point);

        ActionQueryModelToExternalClassMapper mapper = new ActionQueryModelToExternalClassMapper();
        Map<String, String> data = mapper.modelToExternalClass(vuforiaScanTrigger);

        assertEquals("1234", data.get("value"));
        assertEquals("vuforia", data.get("type"));
        assertEquals("foreground", data.get("phoneStatus"));
        assertEquals("23.45", data.get("lat"));
        assertEquals("74.32", data.get("lng"));
    }
}