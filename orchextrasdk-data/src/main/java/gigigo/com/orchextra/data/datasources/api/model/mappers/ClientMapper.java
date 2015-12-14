package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.ggglib.network.mappers.Mapper;
import com.gigigo.orchextra.domain.entities.ClientAuthData;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiClientAuthData;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class ClientMapper implements Mapper<ApiClientAuthData, ClientAuthData> {

  @Override public ClientAuthData modelToData(ApiClientAuthData apiClientAuthData) {
    ClientAuthData clientAuthData = new ClientAuthData();

    clientAuthData.setValue(apiClientAuthData.getValue());
    //TODO create and use GGGLib DateUtils Ex:  DateUtils.stringToDateWithFormat(apiClientAuthData.getExpiresAt(), FORMAT)
    clientAuthData.setExpiresAt(null);
    clientAuthData.setExpiresIn(apiClientAuthData.getExpiresIn());
    clientAuthData.setProjectId(apiClientAuthData.getProjectId());
    clientAuthData.setUserId(apiClientAuthData.getUserId());

    return clientAuthData;
  }

  @Override public ApiClientAuthData dataToModel(ClientAuthData clientAuthData) {
    ApiClientAuthData apiClientAuthData = new ApiClientAuthData();

    apiClientAuthData.setValue(clientAuthData.getValue());
    //TODO create and use GGGLib DateUtils Ex:  DateUtils.dateToStringWithFormat(clientAuthData.getExpiresAt(), FORMAT)
    apiClientAuthData.setExpiresAt(null);
    apiClientAuthData.setExpiresIn(clientAuthData.getExpiresIn());
    apiClientAuthData.setProjectId(clientAuthData.getProjectId());
    apiClientAuthData.setUserId(clientAuthData.getUserId());

    return apiClientAuthData;
  }
}
