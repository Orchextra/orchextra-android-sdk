package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.orchextra.domain.entities.Geofence;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmMapper;
import io.realm.Realm;
import io.realm.RealmResults;

public class ConfigGeofenceUpdater {

    private final RealmMapper<Geofence, GeofenceRealm> geofencesRealmMapper;

    public ConfigGeofenceUpdater(RealmMapper<Geofence, GeofenceRealm> geofencesRealmMapper) {
        this.geofencesRealmMapper = geofencesRealmMapper;
    }

    public void saveGeofences(Realm realm, List<Geofence> geofences) {
        List<Geofence> newGeofences = new ArrayList<>();
        List<Geofence> updateGeofences = new ArrayList<>();
        List<Geofence> deleteGeofences = new ArrayList<>();

        List<String> used = new ArrayList<>();

        for (Geofence geofence : geofences) {
            GeofenceRealm newGeofence = geofencesRealmMapper.modelToData(geofence);
            GeofenceRealm geofenceRealm = realm.where(GeofenceRealm.class).equalTo("code", geofence.getCode()).findFirst();
            if(geofenceRealm == null) {
                newGeofences.add(geofence);

                realm.copyToRealm(newGeofence);
            } else {
                boolean hasChangedGeofence = !checkGeofenceAreEquals(geofenceRealm, newGeofence);
                if (hasChangedGeofence) {
                    updateGeofences.add(geofence);
                    realm.copyToRealmOrUpdate(newGeofence);
                }
            }
            used.add(geofence.getCode());
        }

        RealmResults<GeofenceRealm> all = realm.where(GeofenceRealm.class).findAll();
        for (GeofenceRealm geofenceRealm : all) {
            if (!used.contains(geofenceRealm.getCode())) {
                deleteGeofences.add(geofencesRealmMapper.dataToModel(geofenceRealm));
                geofenceRealm.removeFromRealm();
            }
        }
    }

    public boolean checkGeofenceAreEquals(GeofenceRealm geofenceRealm, GeofenceRealm newGeofence) {
        return geofenceRealm.getCode().equals(newGeofence.getCode()) &&
                geofenceRealm.getPoint().getLat() == newGeofence.getPoint().getLat() &&
                geofenceRealm.getPoint().getLng() == newGeofence.getPoint().getLng() &&
                geofenceRealm.getRadius() == newGeofence.getRadius() &&
                geofenceRealm.getNotifyOnEntry() == newGeofence.getNotifyOnEntry() &&
                geofenceRealm.getNotifyOnExit() == newGeofence.getNotifyOnExit() &&
                geofenceRealm.getStayTime() == newGeofence.getStayTime();
    }
}
