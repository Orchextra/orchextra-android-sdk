package gigigo.com.orchextra.data.datasources.db.geofences;

import android.content.Context;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.dataprovision.proximity.datasource.GeofenceDBDataSource;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;

import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import io.realm.Realm;

public class GeofenceDBDataSourceImp implements GeofenceDBDataSource {

    private final Context context;
    private final GeofenceEventsReader geofenceEventsReader;
    private final GeofenceEventsUpdater geofenceEventsUpdater;
    private final RealmDefaultInstance realmDefaultInstance;

    public GeofenceDBDataSourceImp(Context context, GeofenceEventsReader geofenceEventsReader,
                                   GeofenceEventsUpdater geofenceEventsUpdater,
                                   RealmDefaultInstance realmDefaultInstance) {
        this.context = context;
        this.geofenceEventsReader = geofenceEventsReader;
        this.geofenceEventsUpdater = geofenceEventsUpdater;
        this.realmDefaultInstance = realmDefaultInstance;
    }

    @Override
    public BusinessObject<OrchextraGeofence> storeGeofenceEvent(OrchextraGeofence orchextraGeofence) {
        Realm realm = realmDefaultInstance.createRealmInstance(context);
      try {
        OrchextraGeofence geofence = geofenceEventsUpdater.storeGeofenceEvent(realm, orchextraGeofence);
        return new BusinessObject<>(geofence, BusinessError.createOKInstance());
      }catch (Exception e){
        return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
      }finally {
          if (realm != null) {
              realm.close();
          }
      }
    }

    @Override
    public BusinessObject<OrchextraGeofence> deleteGeofenceEvent(OrchextraGeofence orchextraGeofence) {
        Realm realm = realmDefaultInstance.createRealmInstance(context);
      try {
          OrchextraGeofence geofence = geofenceEventsUpdater.deleteGeofenceEvent(realm, orchextraGeofence);
        return new BusinessObject<>(geofence, BusinessError.createOKInstance());
      }catch (Exception e){
        return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
      }finally {
          if (realm != null) {
              realm.close();
          }
    }
    }

    @Override
    public BusinessObject<OrchextraGeofence> obtainGeofenceEvent(OrchextraGeofence orchextraGeofence) {
        Realm realm = realmDefaultInstance.createRealmInstance(context);
      try {
          return geofenceEventsReader.obtainGeofenceEvent(realm, orchextraGeofence);
      }catch (Exception e){
        return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
      }finally {
          if (realm != null) {
              realm.close();
          }
      }
    }

    @Override
    public BusinessObject<OrchextraGeofence> updateGeofenceWithActionId(OrchextraGeofence geofence) {
        Realm realm = realmDefaultInstance.createRealmInstance(context);
        try {
            OrchextraGeofence orchextraGeofence = geofenceEventsUpdater.addActionToGeofence(
                    realm, geofence);

            return new BusinessObject<>(orchextraGeofence, BusinessError.createOKInstance());
        }catch (Exception e){
            return new BusinessObject<>(null,
                    BusinessError.createKoInstance(e.getMessage()));
        }finally {
            if (realm != null) {
                realm.close();
            }
        }
    }
}
