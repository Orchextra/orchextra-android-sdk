package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.network.mappers.RequestMapper;
import com.gigigo.orchextra.domain.entities.triggers.Trigger;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ActionQueryRequestMapper implements RequestMapper<Trigger, Map<String, String>> {

  private final static String TYPE_QUERY_PARAM = "type";
  private final static String VALUE_QUERY_PARAM = "value";
  private final static String LAT_QUERY_PARAM = "lat";
  private final static String LNG_QUERY_PARAM = "lng";
  private final static String EVENT_QUERY_PARAM = "event";
  private final static String PHONE_STATUS_QUERY_PARAM = "phoneStatus";
  private final static String DISTANCE_QUERY_PARAM = "distance";

  @Override public Map<String, String> modelToData(Trigger trigger) {

    Map<String, String> query = new HashMap<>();

    query.put(TYPE_QUERY_PARAM, trigger.getTriggerType().getStringValue());
    query.put(VALUE_QUERY_PARAM, trigger.getId());

    if (trigger.geoPointIsSupported()){
      query.put(LAT_QUERY_PARAM, String.valueOf(trigger.getPoint().getLat()));
      query.put(LNG_QUERY_PARAM, String.valueOf(trigger.getPoint().getLng()));
    }

    if (trigger.getGeoPointEventType() != null) {
      query.put(EVENT_QUERY_PARAM, trigger.getGeoPointEventType().getStringValue());
    }

    query.put(PHONE_STATUS_QUERY_PARAM, trigger.getPhoneStatusType().getStringValue());

    if (trigger.getGeoDistance()>0){
      query.put(DISTANCE_QUERY_PARAM, String.valueOf(trigger.getGeoDistance()));
    }

    return query;
  }
}
