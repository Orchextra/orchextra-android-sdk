package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofenceUpdates;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import io.realm.Realm;
import io.realm.RealmResults;

public class ConfigGeofenceUpdater {

    private final Mapper<OrchextraGeofence, GeofenceRealm> geofencesRealmMapper;

    public ConfigGeofenceUpdater(Mapper<OrchextraGeofence, GeofenceRealm> geofencesRealmMapper) {
        this.geofencesRealmMapper = geofencesRealmMapper;
    }

    public OrchextraGeofenceUpdates saveGeofences(Realm realm, List<OrchextraGeofence> geofences) {
        List<OrchextraGeofence> newGeofences = new ArrayList<>();
        List<OrchextraGeofence> updateGeofences = new ArrayList<>();
        List<OrchextraGeofence> deleteGeofences = new ArrayList<>();

        List<String> used = new ArrayList<>();

        if (geofences != null) {
            for (OrchextraGeofence geofence : geofences) {
                addOrUpdateGeofences(realm, newGeofences, updateGeofences, used, geofence);
            }

            deleteGeofences = removeUnusedGeofences(realm, used);
        }

        return new OrchextraGeofenceUpdates(newGeofences, updateGeofences, deleteGeofences);
    }

    private void addOrUpdateGeofences(Realm realm, List<OrchextraGeofence> newGeofences, List<OrchextraGeofence> updateGeofences, List<String> used, OrchextraGeofence geofence) {
        GeofenceRealm newGeofence = geofencesRealmMapper.modelToExternalClass(geofence);
        GeofenceRealm geofenceRealm = realm.where(GeofenceRealm.class).equalTo("code", geofence.getCode()).findFirst();
        if (geofenceRealm == null) {
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

    private List<OrchextraGeofence> removeUnusedGeofences(Realm realm, List<String> used) {
        List<OrchextraGeofence> deleteGeofences = new ArrayList<>();

        List<String> geofenceToDelete = new ArrayList<>();
        RealmResults<GeofenceRealm> all = realm.where(GeofenceRealm.class).findAll();
        for (GeofenceRealm geofenceRealm : all) {
            if (!used.contains(geofenceRealm.getCode())) {
                deleteGeofences.add(geofencesRealmMapper.externalClassToModel(geofenceRealm));
                geofenceToDelete.add(geofenceRealm.getCode());
            }
        }
        for (String code : geofenceToDelete) {
            realm.where(GeofenceRealm.class).equalTo("code", code).findFirst().removeFromRealm();
        }

        return deleteGeofences;
    }

    private boolean checkGeofenceAreEquals(GeofenceRealm geofenceRealm, GeofenceRealm newGeofence) {
        return geofenceRealm.getCode().equals(newGeofence.getCode()) &&
                geofenceRealm.getPoint().getLat() == newGeofence.getPoint().getLat() &&
                geofenceRealm.getPoint().getLng() == newGeofence.getPoint().getLng() &&
                geofenceRealm.getRadius() == newGeofence.getRadius() &&
                geofenceRealm.getNotifyOnEntry() == newGeofence.getNotifyOnEntry() &&
                geofenceRealm.getNotifyOnExit() == newGeofence.getNotifyOnExit() &&
                geofenceRealm.getStayTime() == newGeofence.getStayTime();
    }
}
