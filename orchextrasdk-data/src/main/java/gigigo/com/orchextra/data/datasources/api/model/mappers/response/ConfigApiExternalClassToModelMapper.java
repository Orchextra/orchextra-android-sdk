package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.ggglib.mappers.MapperUtils;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.vo.Theme;
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigInfoResult;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiBeaconRegion;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiConfigData;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiGeofence;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class ConfigApiExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiConfigData, ConfigInfoResult> {

  private static final int ONE_SECOND = 1000;

  private final GeofenceExternalClassToModelMapper geofenceResponseMapper;
  private final BeaconExternalClassToModelMapper beaconResponseMapper;
  private final ThemeExternalClassToModelMapper themeResponseMapper;
  private final VuforiaExternalClassToModelMapper vuforiaResponseMapper;

  public ConfigApiExternalClassToModelMapper(VuforiaExternalClassToModelMapper vuforiaResponseMapper,
      ThemeExternalClassToModelMapper themeResponseMapper,
      BeaconExternalClassToModelMapper beaconResponseMapper,
      GeofenceExternalClassToModelMapper geofenceResponseMapper) {
    this.vuforiaResponseMapper = vuforiaResponseMapper;
    this.themeResponseMapper = themeResponseMapper;
    this.beaconResponseMapper = beaconResponseMapper;
    this.geofenceResponseMapper = geofenceResponseMapper;
  }

  @Override public ConfigInfoResult externalClassToModel(ApiConfigData apiConfigData) {

    List<OrchextraRegion> beacons = mapBeacons(apiConfigData.getProximity());
    List<OrchextraGeofence> geofences = mapGeofences(apiConfigData.getGeoMarketing());
    Theme theme = MapperUtils.checkNullDataResponse(themeResponseMapper, apiConfigData.getTheme());
    Vuforia vuforia = MapperUtils.checkNullDataResponse(vuforiaResponseMapper, apiConfigData.getVuforia());

    return new ConfigInfoResult.Builder(apiConfigData.getRequestWaitTime() * ONE_SECOND, geofences,beacons, theme,
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
