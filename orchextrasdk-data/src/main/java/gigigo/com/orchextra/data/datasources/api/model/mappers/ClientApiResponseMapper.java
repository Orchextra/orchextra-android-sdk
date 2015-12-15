package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.network.mappers.DateFormatConstants;
import com.gigigo.ggglib.network.mappers.Mapper;
import com.gigigo.orchextra.domain.entities.ClientAuthData;

import java.util.Date;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiClientAuthData;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class ClientApiResponseMapper implements Mapper<ApiClientAuthData, ClientAuthData> {

  @Override public ClientAuthData modelToData(ApiClientAuthData apiClientAuthData) {
    ClientAuthData clientAuthData = new ClientAuthData();

    clientAuthData.setValue(apiClientAuthData.getValue());

    Date expiredAtDate = DateUtils.stringToDateWithFormat(apiClientAuthData.getExpiresAt(), DateFormatConstants.DATE_FORMAT);
    clientAuthData.setExpiresAt(expiredAtDate);

    clientAuthData.setExpiresIn(apiClientAuthData.getExpiresIn());
    clientAuthData.setProjectId(apiClientAuthData.getProjectId());
    clientAuthData.setUserId(apiClientAuthData.getUserId());

    return clientAuthData;
  }

  @Override public ApiClientAuthData dataToModel(ClientAuthData clientAuthData) {
    ApiClientAuthData apiClientAuthData = new ApiClientAuthData();

    apiClientAuthData.setValue(clientAuthData.getValue());

    String expiredAtString = DateUtils.dateToStringWithFormat(clientAuthData.getExpiresAt(), DateFormatConstants.DATE_FORMAT);
    apiClientAuthData.setExpiresAt(expiredAtString);

    apiClientAuthData.setExpiresIn(clientAuthData.getExpiresIn());
    apiClientAuthData.setProjectId(clientAuthData.getProjectId());
    apiClientAuthData.setUserId(clientAuthData.getUserId());

    return apiClientAuthData;
  }
}
