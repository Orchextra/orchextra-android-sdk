package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.network.mappers.MapperUtils;
import com.gigigo.ggglib.network.mappers.ResponseMapper;
import com.gigigo.orchextra.domain.entities.OrchextraGeofence;
import com.gigigo.orchextra.domain.entities.OrchextraRegion;
import com.gigigo.orchextra.domain.entities.Theme;
import com.gigigo.orchextra.domain.entities.Vuforia;
import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiBeaconRegion;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiConfigData;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiGeofence;

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

    List<OrchextraRegion> beacons = mapBeacons(apiConfigData.getProximity());
    List<OrchextraGeofence> geofences = mapGeofences(apiConfigData.getGeoMarketing());
    Theme theme = MapperUtils.checkNullDataResponse(themeResponseMapper, apiConfigData.getTheme());
    Vuforia vuforia = MapperUtils.checkNullDataResponse(vuforiaResponseMapper, apiConfigData.getVuforia());

    return new ConfigInfoResult.Builder(apiConfigData.getRequestWaitTime(), geofences,beacons, theme,
         vuforia).build();

  }

  private List<OrchextraGeofence> mapGeofences(List<ApiGeofence> apiGeofences) {
    List<OrchextraGeofence> geofences =  new ArrayList<>();

    if (apiGeofences == null)
      return geofences;

    for (ApiGeofence apiGeofence : apiGeofences){
      geofences.add(MapperUtils.checkNullDataResponse(geofenceResponseMapper, apiGeofence));
    }

    return geofences;
  }

  private List<OrchextraRegion> mapBeacons(List<ApiBeaconRegion> apiBeaconRegions) {
    List<OrchextraRegion> beacons = new ArrayList<>();

    if (beacons == null)
      return beacons;

    for (ApiBeaconRegion apiBeaconRegion : apiBeaconRegions){
      beacons.add(MapperUtils.checkNullDataResponse(beaconResponseMapper, apiBeaconRegion));
    }

    return beacons;
  }
}
