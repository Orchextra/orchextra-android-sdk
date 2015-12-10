package gigigo.com.orchextra.data.datasources.datasources.api.auth;

import com.gigigo.ggglib.network.executors.ApiServiceExecutor;
import com.gigigo.ggglib.network.responses.ApiGenericResponse;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.domain.entities.Sdk;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import com.gigigo.orchextra.domain.entities.User;
import gigigo.com.orchextra.data.datasources.datasources.api.service.OrchextraApiService;
import javax.inject.Provider;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 1/12/15.
 */
public class AuthenticationDataSourceImpl implements AuthenticationDataSource {

  private final OrchextraApiService orchextraApiService;
  private final Provider<ApiServiceExecutor> serviceExecutorProvider;

  public AuthenticationDataSourceImpl(OrchextraApiService orchextraApiService,
      Provider<ApiServiceExecutor> serviceExecutorProvider) {
    this.orchextraApiService = orchextraApiService;
    this.serviceExecutorProvider = serviceExecutorProvider;
  }

  @Override public Sdk authenticateSdk(SdkAuthCredentials sdkAuthCredentials) {
    ApiServiceExecutor serviceExecutor = serviceExecutorProvider.get();

    ApiGenericResponse apiGenericResponse = serviceExecutor.executeNetworkServiceConnection(
        Sdk.class, orchextraApiService.sdkAuthentication());

    Sdk sdk = (Sdk) apiGenericResponse.getResult();

    return sdk;
  }

  @Override public User authenticateUser(Sdk sdk) {
    return null;
  }
}
