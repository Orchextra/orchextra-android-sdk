package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiClientAuthData;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class ClientApiExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiClientAuthData, ClientAuthData> {

  @Override
  public ClientAuthData externalClassToModel(ApiClientAuthData apiClientAuthData) {

    ClientAuthData clientAuthData = new ClientAuthData(
        apiClientAuthData.getProjectId(),
        apiClientAuthData.getUserId(),
        apiClientAuthData.getValue(),
        apiClientAuthData.getExpiresIn()
    );

    return clientAuthData;
  }
}
