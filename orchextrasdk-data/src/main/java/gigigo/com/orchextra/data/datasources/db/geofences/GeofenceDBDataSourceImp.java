package gigigo.com.orchextra.data.datasources.db.geofences;

import android.content.Context;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.proximity.datasource.GeofenceDBDataSource;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;

import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;

public class GeofenceDBDataSourceImp extends RealmDefaultInstance implements GeofenceDBDataSource {

    private final Context context;
    private final GeofenceEventsReader geofenceEventsReader;
    private final GeofenceEventsUpdater geofenceEventsUpdater;

    public GeofenceDBDataSourceImp(Context context, GeofenceEventsReader geofenceEventsReader,
                                   GeofenceEventsUpdater geofenceEventsUpdater) {
        this.context = context;
        this.geofenceEventsReader = geofenceEventsReader;
        this.geofenceEventsUpdater = geofenceEventsUpdater;
    }

    @Override
    public BusinessObject<OrchextraGeofence> storeGeofenceEvent(OrchextraGeofence orchextraGeofence) {
      try {
        OrchextraGeofence geofence = geofenceEventsUpdater.storeGeofenceEvent(getRealmInstance(context),
                orchextraGeofence);
        return new BusinessObject<>(geofence, BusinessError.createOKInstance());
      }catch (Exception e){
        return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
      }
    }

    @Override
    public BusinessObject<OrchextraGeofence> deleteGeofenceEvent(OrchextraGeofence orchextraGeofence) {
      try {
          OrchextraGeofence geofence = geofenceEventsUpdater.deleteGeofenceEvent(getRealmInstance(context),
                orchextraGeofence);
        return new BusinessObject<>(geofence, BusinessError.createOKInstance());
      }catch (Exception e){
        return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
      }
    }

    @Override
    public BusinessObject<OrchextraGeofence> obtainGeofenceEvent(OrchextraGeofence orchextraGeofence) {
      try {
          OrchextraGeofence geofence = geofenceEventsReader.obtainGeofenceEvent(getRealmInstance(context), orchextraGeofence);
        return new BusinessObject<>(geofence, BusinessError.createOKInstance());
      }catch (Exception e){
        return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
      }
    }
}
