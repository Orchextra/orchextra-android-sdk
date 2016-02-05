package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.network.mappers.DateFormatConstants;
import com.gigigo.ggglib.network.mappers.MapperUtils;
import com.gigigo.orchextra.domain.entities.OrchextraGeofence;
import com.gigigo.orchextra.domain.entities.OrchextraPoint;
import com.gigigo.orchextra.domain.entities.ProximityPointType;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class GeofenceRealmMapper implements RealmMapper<OrchextraGeofence, GeofenceRealm> {

  private final RealmMapper<OrchextraPoint, RealmPoint> realmPointMapper;
  private final KeyWordRealmMapper keyWordRealmMapper;


  public GeofenceRealmMapper(RealmMapper<OrchextraPoint, RealmPoint> realmPointMapper,
      KeyWordRealmMapper keyWordRealmMapper) {
    this.realmPointMapper = realmPointMapper;
    this.keyWordRealmMapper = keyWordRealmMapper;
  }

  @Override public GeofenceRealm modelToData(OrchextraGeofence geofence) {
    GeofenceRealm geofenceRealm = new GeofenceRealm();

    geofenceRealm.setRadius(geofence.getRadius());
    geofenceRealm.setPoint(MapperUtils.checkNullDataRequest(realmPointMapper, geofence.getPoint()));

    geofenceRealm.setCode(geofence.getCode());
    geofenceRealm.setId(geofence.getId());
    geofenceRealm.setName(geofence.getName());
    geofenceRealm.setNotifyOnEntry(geofence.isNotifyOnEntry());
    geofenceRealm.setNotifyOnExit(geofence.isNotifyOnExit());
    geofenceRealm.setStayTime(geofence.getStayTime());
    geofenceRealm.setTags(keyWordRealmMapper.stringKeyWordsToRealmList(geofence.getTags()));
    geofenceRealm.setType(geofence.getType().getStringValue());

    geofenceRealm.setCreatedAt(
        DateUtils.dateToStringWithFormat(geofence.getCreatedAt(), DateFormatConstants.DATE_FORMAT));

    geofenceRealm.setUpdatedAt(
        DateUtils.dateToStringWithFormat(geofence.getUpdatedAt(), DateFormatConstants.DATE_FORMAT));


    return geofenceRealm;
  }

  @Override public OrchextraGeofence dataToModel(GeofenceRealm geofenceRealm) {
    OrchextraGeofence geofence = new OrchextraGeofence();

    geofence.setRadius(geofenceRealm.getRadius());
    geofence.setPoint(MapperUtils.checkNullDataResponse(realmPointMapper, geofenceRealm.getPoint()));

    geofence.setCode(geofenceRealm.getCode());
    geofence.setId(geofenceRealm.getId());
    geofence.setName(geofenceRealm.getName());
    geofence.setNotifyOnEntry(geofenceRealm.getNotifyOnEntry());
    geofence.setNotifyOnExit(geofenceRealm.getNotifyOnExit());
    geofence.setStayTime(geofenceRealm.getStayTime());
    geofence.setTags(keyWordRealmMapper.realmKeyWordsToStringList(geofenceRealm.getTags()));
    geofence.setType(ProximityPointType.getProximityPointTypeValue(geofenceRealm.getType()));

    geofence.setCreatedAt(
        DateUtils.stringToDateWithFormat(geofenceRealm.getCreatedAt(), DateFormatConstants.DATE_FORMAT));
    geofence.setUpdatedAt(
        DateUtils.stringToDateWithFormat(geofenceRealm.getUpdatedAt(), DateFormatConstants.DATE_FORMAT));

    return geofence;
  }
}
