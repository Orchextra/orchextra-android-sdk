package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.ggglib.network.mappers.Mapper;
import com.gigigo.ggglib.network.responses.ApiGenericExceptionResponse;
import gigigo.com.orchextra.data.datasources.api.model.responses.base.OrchextraApiErrorResponse;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class OrchextraGenericResponseMapper<Model, Data> extends ApiGenericResponseMapper<Model, Data, OrchextraApiErrorResponse> {

  public OrchextraGenericResponseMapper(Mapper<Model, Data> mapper) {
    super(mapper);
  }

  @Override
  protected BusinessError createBusinessError(OrchextraApiErrorResponse orchextraApiErrorResponse) {
    int code = orchextraApiErrorResponse.getCode();
    String message = orchextraApiErrorResponse.getMessage();
    BusinessError businessError = new BusinessError(code, message);
    return businessError;
  }

  @Override protected BusinessError onException(ApiGenericExceptionResponse exceptionResponse) {
    //TODO define custom business code for exception
    int code = BusinessError.EXCEPTION_BUSINESS_ERROR_CODE;
    String message = exceptionResponse.getBusinessError().getMessage();
    BusinessError businessError = new BusinessError(code, message);
    return businessError;
  }
}
