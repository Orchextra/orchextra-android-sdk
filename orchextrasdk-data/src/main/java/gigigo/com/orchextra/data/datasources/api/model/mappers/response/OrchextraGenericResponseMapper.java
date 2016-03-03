/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.gggjavalib.business.model.BusinessContentType;
import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.ggglib.network.responses.ApiGenericExceptionResponse;
import gigigo.com.orchextra.data.datasources.api.model.responses.base.OrchextraApiErrorResponse;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class OrchextraGenericResponseMapper<Model, Data>
    extends ApiGenericResponseMapper<Model, Data, OrchextraApiErrorResponse> {

  public OrchextraGenericResponseMapper(ExternalClassToModelMapper<Model, Data> mapper) {
    super(mapper);
  }

  @Override
  protected BusinessError createBusinessError(OrchextraApiErrorResponse orchextraApiErrorResponse) {

    if (orchextraApiErrorResponse != null) {
      int code = orchextraApiErrorResponse.getCode();
      String message = orchextraApiErrorResponse.getMessage();
      return new BusinessError(code, message, BusinessContentType.BUSINESS_ERROR_CONTENT);
    }

    return BusinessError.createKoInstance("Empty error message");
  }

  @Override protected BusinessError onException(ApiGenericExceptionResponse exceptionResponse) {
    int code = BusinessError.EXCEPTION_BUSINESS_ERROR_CODE;
    String message = exceptionResponse.getBusinessError().getMessage();
    BusinessError businessError =
        new BusinessError(code, message, BusinessContentType.EXCEPTION_CONTENT);
    return businessError;
  }
}
