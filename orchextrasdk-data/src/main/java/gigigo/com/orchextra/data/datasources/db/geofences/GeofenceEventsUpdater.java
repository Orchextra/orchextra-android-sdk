package gigigo.com.orchextra.data.datasources.db.geofences;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;

import java.util.NoSuchElementException;

import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceEventRealm;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class GeofenceEventsUpdater {

    private final Mapper<OrchextraGeofence, GeofenceEventRealm> geofenceEventRealmMapper;

    public GeofenceEventsUpdater(
            Mapper<OrchextraGeofence, GeofenceEventRealm> geofenceEventRealmMapper) {

        this.geofenceEventRealmMapper = geofenceEventRealmMapper;
    }

    public OrchextraGeofence storeGeofenceEvent(Realm realm, OrchextraGeofence geofence) {
        GeofenceEventRealm geofenceEventRealm = geofenceEventRealmMapper.modelToExternalClass(geofence);
        storeElement(realm, geofenceEventRealm);
        return geofenceEventRealmMapper.externalClassToModel(geofenceEventRealm);
    }

    public OrchextraGeofence deleteGeofenceEvent(Realm realm, OrchextraGeofence geofence) {

        RealmResults<GeofenceEventRealm> results = realm.where(GeofenceEventRealm.class)
                .equalTo(BeaconRegionEventRealm.CODE_FIELD_NAME, geofence.getCode()).findAll();

        if (results.size()>1){
            GGGLogImpl.log("More than one region Event with same Code stored", LogLevel.ERROR);
        }

        OrchextraGeofence removedGeofence = geofenceEventRealmMapper.externalClassToModel(
                results.first());

        purgeResults(realm, results);

        return removedGeofence;
    }

    private void storeElement(Realm realm, RealmObject element) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(element);
        realm.commitTransaction();
    }

    private void purgeResults(Realm realm, RealmResults resultsToPurge) {
        realm.beginTransaction();
        resultsToPurge.clear();
        realm.commitTransaction();
    }

    public OrchextraGeofence addActionToGeofence(Realm realm, OrchextraGeofence geofence) {
        RealmResults<GeofenceEventRealm> results = realm.where(GeofenceEventRealm.class)
                .equalTo(BeaconRegionEventRealm.CODE_FIELD_NAME, geofence.getCode()).findAll();

        if (results.isEmpty()){
            GGGLogImpl.log("Required region does not Exist", LogLevel.ERROR);
            throw new NoSuchElementException("Required region does not Exist");
        }else if(results.size()>1){
            GGGLogImpl.log("More than one region Event with same Code stored", LogLevel.ERROR);
        }

        GeofenceEventRealm geofenceEventRealm = results.first();
        geofenceEventRealm.setActionRelated(geofence.getActionRelated());
        storeElement(realm, geofenceEventRealm);

        return geofenceEventRealmMapper.externalClassToModel(geofenceEventRealm);
    }
}
