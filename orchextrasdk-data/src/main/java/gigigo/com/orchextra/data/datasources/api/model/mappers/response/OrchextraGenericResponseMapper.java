package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.gggjavalib.business.model.BusinessContentType;
import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.ggglib.network.responses.ApiGenericExceptionResponse;
import gigigo.com.orchextra.data.datasources.api.model.responses.base.OrchextraApiErrorResponse;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class OrchextraGenericResponseMapper<Model, Data> extends ApiGenericResponseMapper<Model, Data, OrchextraApiErrorResponse> {

  public OrchextraGenericResponseMapper(ExternalClassToModelMapper<Model, Data> mapper) {
    super(mapper);
  }

  @Override
  protected BusinessError createBusinessError(OrchextraApiErrorResponse orchextraApiErrorResponse) {

    if (orchextraApiErrorResponse!=null){
      int code = orchextraApiErrorResponse.getCode();
      String message = orchextraApiErrorResponse.getMessage();
      return new BusinessError(code, message, BusinessContentType.BUSINESS_ERROR_CONTENT);
    }

    return BusinessError.createKoInstance("Empty error message");
  }

  @Override protected BusinessError onException(ApiGenericExceptionResponse exceptionResponse) {
    int code = BusinessError.EXCEPTION_BUSINESS_ERROR_CODE;
    String message = exceptionResponse.getBusinessError().getMessage();
    BusinessError businessError = new BusinessError(code, message, BusinessContentType.EXCEPTION_CONTENT);
    return businessError;
  }
}
