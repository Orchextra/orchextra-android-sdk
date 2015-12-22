package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.network.mappers.MapperUtils;
import com.gigigo.ggglib.network.mappers.ResponseMapper;
import com.gigigo.orchextra.domain.entities.Beacon;
import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.Theme;
import com.gigigo.orchextra.domain.entities.Vuforia;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiBeacon;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiConfigData;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiGeofence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class ConfigApiResponseMapper implements ResponseMapper<ConfigInfoResult, ApiConfigData> {

  private final GeofenceResponseMapper geofenceResponseMapper;
  private final BeaconResponseMapper beaconResponseMapper;
  private final ThemeResponseMapper themeResponseMapper;
  private final VuforiaResponseMapper vuforiaResponseMapper;

  public ConfigApiResponseMapper(VuforiaResponseMapper vuforiaResponseMapper,
      ThemeResponseMapper themeResponseMapper, BeaconResponseMapper beaconResponseMapper,
      GeofenceResponseMapper geofenceResponseMapper) {
    this.vuforiaResponseMapper = vuforiaResponseMapper;
    this.themeResponseMapper = themeResponseMapper;
    this.beaconResponseMapper = beaconResponseMapper;
    this.geofenceResponseMapper = geofenceResponseMapper;
  }

  @Override public ConfigInfoResult dataToModel(ApiConfigData apiConfigData) {

    List<Beacon> beacons = mapBeacons(apiConfigData.getProximity());
    List<Geofence> geofences = mapGeofences(apiConfigData.getGeoMarketing());
    Theme theme = MapperUtils.checkNullDataResponse(themeResponseMapper, apiConfigData.getTheme());
    Vuforia vuforia = MapperUtils.checkNullDataResponse(vuforiaResponseMapper, apiConfigData.getVuforia());

    return new ConfigInfoResult.ConfigInfoResultBuilder(geofences,beacons, theme,
        apiConfigData.getRequestWaitTime(), vuforia).build();

  }

  private List<Geofence> mapGeofences(List<ApiGeofence> apiGeofences) {
    List<Geofence> geofences =  new ArrayList<>();

    if (apiGeofences == null)
      return geofences;

    for (ApiGeofence apiGeofence : apiGeofences){
      geofences.add(MapperUtils.checkNullDataResponse(geofenceResponseMapper, apiGeofence));
    }

    return geofences;
  }

  private List<Beacon> mapBeacons(List<ApiBeacon> apiBeacons) {
    List<Beacon> beacons = new ArrayList<>();

    if (beacons == null)
      return beacons;

    for (ApiBeacon apiBeacon : apiBeacons){
      beacons.add(MapperUtils.checkNullDataResponse(beaconResponseMapper, apiBeacon));
    }

    return beacons;
  }
}
