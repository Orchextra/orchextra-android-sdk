package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiEddyStoneRegion;

/**
 * Created by nubor on 17/07/2017.
 */

public class EddyStoneBeaconExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiEddyStoneRegion, OrchextraRegion> {

  @Override public OrchextraRegion externalClassToModel(ApiEddyStoneRegion apiRegion) {

    OrchextraRegion region =
        new OrchextraRegion(apiRegion.getCode(), apiRegion.getNamespace(), -1, -1,
            apiRegion.getActive());

    //Set supper fields
    region.setNotifyOnEntry(apiRegion.getNotifyOnEntry());
    region.setNotifyOnExit(apiRegion.getNotifyOnExit());
    region.setTags(apiRegion.getTags());

    return region;
  }
}