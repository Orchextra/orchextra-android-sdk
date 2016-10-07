package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import com.gigigo.orchextra.domain.model.triggers.params.BeaconDistanceType;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.params.TriggerType;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import com.gigigo.orchextra.domain.model.vo.OrchextraLocationPoint;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ActionQueryRequestMapperTest {
    @Test
    public void testCreateGeofenceTrigger() throws Exception {
        OrchextraLocationPoint point = new OrchextraLocationPoint();
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
        OrchextraBeacon orchextraBeacon = new OrchextraBeacon("1234", 1, 2, BeaconDistanceType.NEAR);
        orchextraBeacon.setCode("999");

        Trigger beaconTrigger = Trigger.createBeaconTrigger(AppRunningModeType.FOREGROUND, orchextraBeacon);

        assertEquals("999", beaconTrigger.getCode());
        assertEquals(TriggerType.BEACON, beaconTrigger.getTriggerType());
        assertEquals(BeaconDistanceType.NEAR, beaconTrigger.getBeaconDistanceType());
        assertEquals(AppRunningModeType.FOREGROUND, beaconTrigger.getAppRunningModeType());
    }

    @Test
    public void testCreateQrScanTrigger() throws Exception {
        OrchextraLocationPoint point = new OrchextraLocationPoint();
        point.setLat(23.45);
        point.setLng(74.32);

        Trigger qrScanTrigger = Trigger.createQrScanTrigger("1234", point);

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
        OrchextraLocationPoint point = new OrchextraLocationPoint();
        point.setLat(23.45);
        point.setLng(74.32);

        Trigger barcodeScanTrigger = Trigger.createBarcodeScanTrigger("1234", point);

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
        OrchextraLocationPoint point = new OrchextraLocationPoint();
        point.setLat(23.45);
        point.setLng(74.32);

        Trigger vuforiaScanTrigger = Trigger.createVuforiaScanTrigger("1234", point);

        ActionQueryModelToExternalClassMapper mapper = new ActionQueryModelToExternalClassMapper();
        Map<String, String> data = mapper.modelToExternalClass(vuforiaScanTrigger);

        assertEquals("1234", data.get("value"));
        assertEquals("vuforia", data.get("type"));
        assertEquals("foreground", data.get("phoneStatus"));
        assertEquals("23.45", data.get("lat"));
        assertEquals("74.32", data.get("lng"));
    }
}