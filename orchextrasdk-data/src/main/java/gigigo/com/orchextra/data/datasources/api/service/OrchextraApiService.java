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

package gigigo.com.orchextra.data.datasources.api.service;

import java.util.Map;

import gigigo.com.orchextra.data.datasources.api.model.requests.OrchextraApiAuthRequest;
import gigigo.com.orchextra.data.datasources.api.model.requests.OrchextraApiConfigRequest;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiActionResponse;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiClientAuthResponse;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiConfigResponse;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiSdkAuthResponse;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;


public interface OrchextraApiService {

  @POST("security/token") Call<ApiSdkAuthResponse> sdkAuthentication(
      @Body OrchextraApiAuthRequest authRequest);

  @POST("security/token") Call<ApiClientAuthResponse> clientAuthentication(
      @Body OrchextraApiAuthRequest authRequest);

  @POST("configuration") Call<ApiConfigResponse> sendSdkConfig(
      @Body OrchextraApiConfigRequest configRequest);

  @FormUrlEncoded @POST("action") Call<ApiActionResponse> obtainAction(
      @FieldMap Map<String, String> parameters);

  @POST("action/confirm/{trackId}")
    Call<Object> sendCompletedAction(@Path("trackId") String trackId);
}
