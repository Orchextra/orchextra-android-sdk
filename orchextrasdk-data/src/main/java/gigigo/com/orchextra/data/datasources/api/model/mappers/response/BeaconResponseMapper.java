package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.network.mappers.DateFormatConstants;
import com.gigigo.ggglib.network.mappers.ResponseMapper;
import com.gigigo.orchextra.domain.entities.Beacon;
import com.gigigo.orchextra.domain.entities.ProximityPointType;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiBeacon;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class BeaconResponseMapper implements ResponseMapper<Beacon, ApiBeacon>{

  @Override public Beacon dataToModel(ApiBeacon apiBeacon) {
    Beacon beacon = new Beacon();

    beacon.setActive(apiBeacon.getActive());
    beacon.setMajor(apiBeacon.getMajor());
    beacon.setMinor(apiBeacon.getMinor());
    beacon.setUuid(apiBeacon.getUuid());

    beacon.setCode(apiBeacon.getCode());
    beacon.setId(apiBeacon.getId());
    beacon.setName(apiBeacon.getName());
    beacon.setNotifyOnEntry(apiBeacon.getNotifyOnEntry());
    beacon.setNotifyOnExit(apiBeacon.getNotifyOnExit());
    beacon.setStayTime(apiBeacon.getStayTime());
    beacon.setTags(apiBeacon.getTags());
    beacon.setType(ProximityPointType.getProximityPointTypeValue(apiBeacon.getType()));

    beacon.setCreatedAt(DateUtils.stringToDateWithFormat(apiBeacon.getCreatedAt(),
        DateFormatConstants.DATE_FORMAT));
    beacon.setUpdatedAt(DateUtils.stringToDateWithFormat(apiBeacon.getUpdatedAt(),
        DateFormatConstants.DATE_FORMAT));

    return beacon;
  }

}
