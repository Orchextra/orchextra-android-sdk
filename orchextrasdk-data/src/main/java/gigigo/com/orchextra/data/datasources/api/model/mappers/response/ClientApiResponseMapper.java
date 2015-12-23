package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.network.mappers.ResponseMapper;
import com.gigigo.orchextra.domain.entities.ClientAuthData;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiClientAuthData;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class ClientApiResponseMapper implements ResponseMapper<ClientAuthData, ApiClientAuthData> {

  @Override
  public ClientAuthData dataToModel(ApiClientAuthData apiClientAuthData) {

    ClientAuthData clientAuthData = new ClientAuthData(
        apiClientAuthData.getProjectId(),
        apiClientAuthData.getUserId(),
        apiClientAuthData.getValue(),
        apiClientAuthData.getExpiresIn()
    );

    return clientAuthData;
  }
}
