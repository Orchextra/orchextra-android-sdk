package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.network.mappers.DateFormatConstants;
import com.gigigo.ggglib.network.mappers.MapperUtils;
import com.gigigo.ggglib.network.mappers.ResponseMapper;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.ProximityPointType;
import gigigo.com.orchextra.data.datasources.api.model.mappers.PointMapper;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiGeofence;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class GeofenceResponseMapper implements ResponseMapper<Geofence, ApiGeofence>{

  private final PointMapper pointMapper;

  public GeofenceResponseMapper(PointMapper pointMapper) {
    this.pointMapper = pointMapper;
  }

  @Override public Geofence dataToModel(ApiGeofence apiGeofence) {
    Geofence geofence = new Geofence();
    geofence.setRadius(apiGeofence.getRadius());
    geofence.setCode(apiGeofence.getCode());
    geofence.setId(apiGeofence.getId());
    geofence.setName(apiGeofence.getName());
    geofence.setNotifyOnEntry(apiGeofence.getNotifyOnEntry());
    geofence.setNotifyOnExit(apiGeofence.getNotifyOnExit());
    geofence.setStayTime(apiGeofence.getStayTime());
    geofence.setTags(apiGeofence.getTags());
    geofence.setType(ProximityPointType.getProximityPointTypeValue(apiGeofence.getType()));

    geofence.setCreatedAt(DateUtils.stringToDateWithFormat(apiGeofence.getCreatedAt(),
        DateFormatConstants.DATE_FORMAT));
    geofence.setUpdatedAt(DateUtils.stringToDateWithFormat(apiGeofence.getUpdatedAt(),
        DateFormatConstants.DATE_FORMAT));

    geofence.setPoint(MapperUtils.checkNullDataResponse(pointMapper, apiGeofence.getPoint()));

    return geofence;
  }

}
