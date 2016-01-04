package gigigo.com.orchextra.data.datasources.api.config;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.ggglib.network.executors.ApiServiceExecutor;
import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.ggglib.network.mappers.RequestMapper;
import com.gigigo.ggglib.network.responses.ApiGenericResponse;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDataSource;
import com.gigigo.orchextra.domain.entities.config.Config;
import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiConfigData;
import gigigo.com.orchextra.data.datasources.api.model.requests.OrchextraApiConfigRequest;
import gigigo.com.orchextra.data.datasources.api.service.OrchextraApiService;
import javax.inject.Provider;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ConfigDataSourceImpl implements ConfigDataSource {

  private final OrchextraApiService orchextraApiService;
  private final Provider<ApiServiceExecutor> serviceExecutorProvider;

  private final RequestMapper<Config, OrchextraApiConfigRequest> configRequestMapper;
  private final ApiGenericResponseMapper configResponseMapper;

  public ConfigDataSourceImpl(OrchextraApiService orchextraApiService,
      Provider<ApiServiceExecutor> serviceExecutorProvider,
      ApiGenericResponseMapper configResponseMapper,
      RequestMapper configRequestMapper) {

    this.orchextraApiService = orchextraApiService;
    this.serviceExecutorProvider = serviceExecutorProvider;
    this.configResponseMapper = configResponseMapper;
    this.configRequestMapper = configRequestMapper;
  }

  @Override public BusinessObject<ConfigInfoResult> sendConfigInfo(Config config) {
    ApiServiceExecutor serviceExecutor = serviceExecutorProvider.get();

    OrchextraApiConfigRequest request = configRequestMapper.modelToData(config);

    ApiGenericResponse apiGenericResponse = serviceExecutor.executeNetworkServiceConnection(
        ApiConfigData.class, orchextraApiService.sendSdkConfig(request));

    return configResponseMapper.mapApiGenericResponseToBusiness(apiGenericResponse);
  }

}
