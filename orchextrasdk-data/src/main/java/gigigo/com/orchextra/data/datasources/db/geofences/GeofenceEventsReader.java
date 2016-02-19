package gigigo.com.orchextra.data.datasources.db.geofences;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;

import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceEventRealm;
import io.realm.Realm;
import io.realm.RealmResults;

public class GeofenceEventsReader {

    private final Mapper<OrchextraGeofence, GeofenceEventRealm> geofenceEventRealmMapper;

    public GeofenceEventsReader(
            Mapper<OrchextraGeofence, GeofenceEventRealm> geofenceEventRealmMapper) {
        this.geofenceEventRealmMapper = geofenceEventRealmMapper;
    }

    public boolean isGeofenceEventStored(Realm realm, OrchextraGeofence geofence) {
        boolean result = false;
        try {
            RealmResults<GeofenceEventRealm> realms = realm.where(GeofenceEventRealm.class)
                    .equalTo(GeofenceEventRealm.CODE_FIELD_NAME, geofence.getCode())
                    .equalTo(GeofenceEventRealm.TYPE_FIELD_NAME, geofence.getType().getStringValue())
                    .findAll();
            result = (!realms.isEmpty());
        }catch (Exception e){
            GGGLogImpl.log(e.getMessage(), LogLevel.ERROR);
        }finally {
            return result;
        }
    }

    public OrchextraGeofence obtainGeofenceEvent(Realm realm, OrchextraGeofence orchextraGeofence) {

        RealmResults<GeofenceEventRealm> results = realm.where(GeofenceEventRealm.class)
                .equalTo(GeofenceEventRealm.CODE_FIELD_NAME, orchextraGeofence.getCode())
                .findAll();

        if (results.size()>1) {
            GGGLogImpl.log("More than one region Event with same Code stored", LogLevel.ERROR);
        } else if (results.size() == 0) {
            throw new NotFountRealmObjectException();
        }

        return geofenceEventRealmMapper.externalClassToModel(results.first());
    }
}
