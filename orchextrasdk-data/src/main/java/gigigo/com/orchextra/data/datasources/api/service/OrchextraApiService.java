package gigigo.com.orchextra.data.datasources.api.service;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiActionResponse;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiClientAuthResponse;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiConfigResponse;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiSdkAuthResponse;
import gigigo.com.orchextra.data.datasources.api.model.requests.OrchextraApiAuthRequest;
import gigigo.com.orchextra.data.datasources.api.model.requests.OrchextraApiConfigRequest;
import java.util.Map;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.QueryMap;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
public interface OrchextraApiService {

  @POST("security/token")
  Call<ApiSdkAuthResponse> sdkAuthentication(@Body OrchextraApiAuthRequest authRequest);

  @POST("security/token")
  Call<ApiClientAuthResponse> clientAuthentication(@Body OrchextraApiAuthRequest authRequest);

  @POST("configuration")
  Call<ApiConfigResponse> sendSdkConfig(@Body OrchextraApiConfigRequest configRequest);

  @FormUrlEncoded
  @POST("action")
  Call<ApiActionResponse> obtainAction(@FieldMap Map<String,String> parameters);
}
