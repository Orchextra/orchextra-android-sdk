package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.ggglib.network.mappers.RequestMapper;
import com.gigigo.orchextra.domain.entities.ActionCriteria;
import java.util.Map;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ActionCriteriaRequestMapper implements
    RequestMapper<ActionCriteria, Map<String, String>> {

  @Override public Map<String, String> modelToData(ActionCriteria actionCriteria) {
    return null;
  }
}
