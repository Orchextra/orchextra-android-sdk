package gigigo.com.orchextra.data.datasources.api.action;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.ggglib.network.executors.ApiServiceExecutor;
import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.ggglib.network.mappers.RequestMapper;
import com.gigigo.ggglib.network.responses.ApiGenericResponse;
import com.gigigo.orchextra.dataprovision.actions.datasource.ActionsDataSource;
import com.gigigo.orchextra.domain.entities.ActionCriteria;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiActionResponse;
import gigigo.com.orchextra.data.datasources.api.service.OrchextraApiService;
import java.util.Map;
import javax.inject.Provider;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ActionsDataSourceImpl implements ActionsDataSource {

  private final OrchextraApiService orchextraApiService;
  private final Provider<ApiServiceExecutor> serviceExecutorProvider;

  private final RequestMapper<ActionCriteria, Map<String, String>> actionQueryParamsMapper;
  private final ApiGenericResponseMapper actionResponseMapper;

  public ActionsDataSourceImpl(OrchextraApiService orchextraApiService,
      Provider<ApiServiceExecutor> serviceExecutorProvider,
      ApiGenericResponseMapper actionResponseMapper,
      RequestMapper actionQueryParamsMapper) {

    this.orchextraApiService = orchextraApiService;
    this.serviceExecutorProvider = serviceExecutorProvider;
    this.actionResponseMapper = actionResponseMapper;
    this.actionQueryParamsMapper = actionQueryParamsMapper;
  }

  @Override public BusinessObject<BasicAction> obtainAction(ActionCriteria actionCriteria) {
    ApiServiceExecutor serviceExecutor = serviceExecutorProvider.get();

    Map<String, String> queryParams = actionQueryParamsMapper.modelToData(actionCriteria);

    ApiGenericResponse apiGenericResponse = serviceExecutor.executeNetworkServiceConnection(
        ApiActionResponse.class, orchextraApiService.obtainAction(queryParams));

    return actionResponseMapper.mapApiGenericResponseToBusiness(apiGenericResponse);
  }

}
