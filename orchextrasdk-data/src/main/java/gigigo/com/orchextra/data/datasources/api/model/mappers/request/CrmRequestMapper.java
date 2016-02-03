package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.network.mappers.DateFormatConstants;
import com.gigigo.ggglib.network.mappers.RequestMapper;
import com.gigigo.orchextra.domain.entities.Crm;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiCrm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class CrmRequestMapper implements RequestMapper<Crm, ApiCrm> {

  @Override public ApiCrm modelToData(Crm crm) {

    ApiCrm apiCrm = new ApiCrm();

    apiCrm.setBirthDate(DateUtils.dateToStringWithFormat(crm.getBirthDate(),
            DateFormatConstants.DATE_FORMAT));
    apiCrm.setCrmId(crm.getCrmId());

    if (crm.getGender() != null) {
      apiCrm.setGender(crm.getGender().getStringValue());
    }

    apiCrm.setKeywords(crm.getKeywords());

    return apiCrm;
  }
}
