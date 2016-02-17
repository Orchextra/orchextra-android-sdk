package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglogger.GGGLogImpl;
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
        List<OrchextraGeofence> deleteGeofences = new ArrayList<>();

        List<String> used = new ArrayList<>();

        if (geofences != null) {
            addOrUpdateGeofences(realm, newGeofences, used, geofences);
            deleteGeofences = removeUnusedGeofences(realm, used);
        }

        return new OrchextraGeofenceUpdates(newGeofences, deleteGeofences);
    }

    private void addOrUpdateGeofences(Realm realm, List<OrchextraGeofence> newGeofences, List<String> used, List<OrchextraGeofence> geofences) {
        for (OrchextraGeofence geofence : geofences) {
            GeofenceRealm newGeofence = geofencesRealmMapper.modelToExternalClass(geofence);
            GeofenceRealm geofenceRealm = realm.where(GeofenceRealm.class).equalTo("code", geofence.getCode()).findFirst();

            if(!checkGeofenceAreEquals(geofenceRealm, newGeofence)) {
                newGeofences.add(geofence);
                realm.copyToRealmOrUpdate(newGeofence);
                GGGLogImpl.log("Añadida geofence a la base de datos: " + geofence.getCode());
            }

            used.add(geofence.getCode());
        }
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
            GGGLogImpl.log("Eliminada geofence de la base de datos: " + code);
        }

        return deleteGeofences;
    }

    private boolean checkGeofenceAreEquals(GeofenceRealm geofenceRealm, GeofenceRealm newGeofence) {
        if (geofenceRealm == null || newGeofence == null) {
            return false;
        }
        return geofenceRealm.getCode().equals(newGeofence.getCode()) &&
                geofenceRealm.getPoint().getLat() == newGeofence.getPoint().getLat() &&
                geofenceRealm.getPoint().getLng() == newGeofence.getPoint().getLng() &&
                geofenceRealm.getRadius() == newGeofence.getRadius() &&
                geofenceRealm.getNotifyOnEntry() == newGeofence.getNotifyOnEntry() &&
                geofenceRealm.getNotifyOnExit() == newGeofence.getNotifyOnExit() &&
                geofenceRealm.getStayTime() == newGeofence.getStayTime();
    }
}
