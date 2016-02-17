package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglib.mappers.MapperUtils;
import com.gigigo.orchextra.domain.model.ProximityPointType;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;

import gigigo.com.orchextra.data.datasources.db.model.GeofenceEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;

public class GeofenceEventRealmMapper implements Mapper<OrchextraGeofence, GeofenceEventRealm> {

    private final Mapper<OrchextraPoint, RealmPoint> realmPointMapper;

    public GeofenceEventRealmMapper(Mapper<OrchextraPoint, RealmPoint> realmPointMapper) {
        this.realmPointMapper = realmPointMapper;
    }

    @Override
    public OrchextraGeofence externalClassToModel(GeofenceEventRealm geofenceEventRealm) {
        OrchextraGeofence geofence = new OrchextraGeofence();

        geofence.setRadius(geofenceEventRealm.getRadius());
        geofence.setPoint(MapperUtils.checkNullDataResponse(realmPointMapper, geofenceEventRealm.getPoint()));

        geofence.setCode(geofenceEventRealm.getCode());
        geofence.setNotifyOnEntry(geofenceEventRealm.isNotifyOnEntry());
        geofence.setNotifyOnExit(geofenceEventRealm.isNotifyOnExit());
        geofence.setStayTime(geofenceEventRealm.getStayTime());
        geofence.setType(ProximityPointType.getProximityPointTypeValue(geofenceEventRealm.getType()));

        return geofence;
    }

    @Override
    public GeofenceEventRealm modelToExternalClass(OrchextraGeofence geofence) {
        GeofenceEventRealm geofenceRealm = new GeofenceEventRealm();

        geofenceRealm.setRadius(geofence.getRadius());
        geofenceRealm.setPoint(MapperUtils.checkNullDataRequest(realmPointMapper, geofence.getPoint()));

        geofenceRealm.setCode(geofence.getCode());
        geofenceRealm.setNotifyOnEntry(geofence.isNotifyOnEntry());
        geofenceRealm.setNotifyOnExit(geofence.isNotifyOnExit());
        geofenceRealm.setStayTime(geofence.getStayTime());
        if (geofence.getType() != null) {
            geofenceRealm.setType(geofence.getType().getStringValue());
        }

        return geofenceRealm;
    }
}
