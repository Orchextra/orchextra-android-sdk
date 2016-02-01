package gigigo.com.orchextra.data.datasources.api.auth;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.ggglib.network.executors.ApiServiceExecutor;
import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.ggglib.network.responses.ApiGenericResponse;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.domain.entities.ClientAuthData;
import com.gigigo.orchextra.domain.entities.Credentials;
import com.gigigo.orchextra.domain.entities.SdkAuthData;

import javax.inject.Provider;

import gigigo.com.orchextra.data.datasources.api.model.requests.GrantType;
import gigigo.com.orchextra.data.datasources.api.model.requests.OrchextraApiAuthRequest;
import gigigo.com.orchextra.data.datasources.api.model.requests.OrchextraApiClientAuthRequest;
import gigigo.com.orchextra.data.datasources.api.model.requests.OrchextraApiSdkAuthRequest;
import gigigo.com.orchextra.data.datasources.api.service.OrchextraApiService;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 1/12/15.
 */
public class AuthenticationDataSourceImpl implements AuthenticationDataSource {

  private final OrchextraApiService orchextraApiService;
  private final Provider<ApiServiceExecutor> serviceExecutorProvider;

  private final ApiGenericResponseMapper sdkResponseMapper;
  private final ApiGenericResponseMapper clientResponseMapper;

  public AuthenticationDataSourceImpl(OrchextraApiService orchextraApiService,
      Provider<ApiServiceExecutor> serviceExecutorProvider,
      ApiGenericResponseMapper sdkResponseMapper,
      ApiGenericResponseMapper clientResponseMapper) {
    this.orchextraApiService = orchextraApiService;
    this.serviceExecutorProvider = serviceExecutorProvider;
    this.sdkResponseMapper = sdkResponseMapper;
    this.clientResponseMapper = clientResponseMapper;
  }

  @Override public BusinessObject<SdkAuthData> authenticateSdk(Credentials credentials) {
    ApiServiceExecutor serviceExecutor = serviceExecutorProvider.get();

    OrchextraApiAuthRequest request = new OrchextraApiSdkAuthRequest(GrantType.AUTH_SDK, credentials);

    ApiGenericResponse apiGenericResponse = serviceExecutor.executeNetworkServiceConnection(
        SdkAuthData.class, orchextraApiService.sdkAuthentication(request));

    return sdkResponseMapper.mapApiGenericResponseToBusiness(apiGenericResponse);
  }

  @Override public BusinessObject<ClientAuthData> authenticateUser(Credentials credentials) {
    ApiServiceExecutor serviceExecutor = serviceExecutorProvider.get();

    OrchextraApiAuthRequest request = new OrchextraApiClientAuthRequest(
        GrantType.AUTH_USER, credentials);

    ApiGenericResponse apiGenericResponse = serviceExecutor.executeNetworkServiceConnection(
        SdkAuthData.class, orchextraApiService.clientAuthentication(request));

    return clientResponseMapper.mapApiGenericResponseToBusiness(apiGenericResponse);
  }
}
