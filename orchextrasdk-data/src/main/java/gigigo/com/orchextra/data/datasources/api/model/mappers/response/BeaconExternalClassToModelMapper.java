package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiBeaconRegion;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class BeaconExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiBeaconRegion, OrchextraRegion> {

  @Override public OrchextraRegion externalClassToModel(ApiBeaconRegion apiBeaconRegion) {

    OrchextraRegion region = new OrchextraRegion(
        apiBeaconRegion.getCode(),
        apiBeaconRegion.getUuid(),
        apiBeaconRegion.getMajor(),
        apiBeaconRegion.getMinor(),
        apiBeaconRegion.getActive());

    //Set supper fields
    region.setNotifyOnEntry(apiBeaconRegion.getNotifyOnEntry());
    region.setNotifyOnExit(apiBeaconRegion.getNotifyOnExit());
    region.setTags(apiBeaconRegion.getTags());

    return region;
  }

}
