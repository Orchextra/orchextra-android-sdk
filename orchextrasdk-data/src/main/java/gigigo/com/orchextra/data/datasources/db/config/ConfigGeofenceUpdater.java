/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofenceUpdates;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

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

  private void addOrUpdateGeofences(Realm realm, List<OrchextraGeofence> newGeofences,
      List<String> used, List<OrchextraGeofence> geofences) {
    for (OrchextraGeofence geofence : geofences) {
      GeofenceRealm newGeofence = geofencesRealmMapper.modelToExternalClass(geofence);
      RealmResults<GeofenceRealm> geofenceRealm =
          realm.where(GeofenceRealm.class).equalTo("code", geofence.getCode()).findAll();

      if (!checkGeofenceAreEquals(geofenceRealm, newGeofence)) {
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
      RealmResults<GeofenceRealm> geofenceRealms =
          realm.where(GeofenceRealm.class).equalTo("code", code).findAll();
      if (geofenceRealms.size() > 0) {
        geofenceRealms.first().removeFromRealm();
      }
      GGGLogImpl.log("Eliminada geofence de la base de datos: " + code);
    }

    return deleteGeofences;
  }

  private boolean checkGeofenceAreEquals(RealmResults<GeofenceRealm> geofenceRealm,
      GeofenceRealm newGeofence) {
    if (geofenceRealm.size() == 0 || newGeofence == null) {
      return false;
    }
    GeofenceRealm savedGeofence = geofenceRealm.first();
    return savedGeofence.getCode().equals(newGeofence.getCode()) &&
        savedGeofence.getPoint().getLat() == newGeofence.getPoint().getLat() &&
        savedGeofence.getPoint().getLng() == newGeofence.getPoint().getLng() &&
        savedGeofence.getRadius() == newGeofence.getRadius() &&
        savedGeofence.getNotifyOnEntry() == newGeofence.getNotifyOnEntry() &&
        savedGeofence.getNotifyOnExit() == newGeofence.getNotifyOnExit() &&
        savedGeofence.getStayTime() == newGeofence.getStayTime();
  }
}
