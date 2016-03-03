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

package gigigo.com.orchextra.data.datasources.api.model.responses.base;

import com.gigigo.ggglib.network.responses.ApiGenericResponse;
import com.gigigo.ggglib.network.responses.HttpResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 30/11/15.
 */
public class BaseOrchextraApiResponse<JSONData>
    implements ApiGenericResponse<JSONData, OrchextraApiErrorResponse> {

  @SerializedName("status") private boolean status;
  @SerializedName("data") private JSONData data;
  @SerializedName("error") private OrchextraApiErrorResponse error;

  private HttpResponse httpResponse;

  public boolean getStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  @Override public HttpResponse getHttpResponse() {
    return httpResponse;
  }

  @Override public void setHttpResponse(HttpResponse httpResponse) {
    this.httpResponse = httpResponse;
  }

  @Override public boolean isException() {
    return false;
  }

  @Override public JSONData getResult() {
    return data;
  }

  @Override public void setResult(JSONData data) {
    this.data = data;
  }

  @Override public void setBusinessError(OrchextraApiErrorResponse error) {
    this.error = error;
  }

  @Override public OrchextraApiErrorResponse getBusinessError() {
    return error;
  }
}
