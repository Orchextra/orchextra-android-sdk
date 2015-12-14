package gigigo.com.orchextra.data.datasources.api.service;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiActionResponse;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiClientAuthResponse;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiConfigResponse;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiSdkAuthResponse;
import gigigo.com.orchextra.data.datasources.api.model.resquests.OrchextraApiAuthRequest;
import gigigo.com.orchextra.data.datasources.api.model.resquests.OrchextraApiConfigRequest;
import java.util.Map;
import retrofit.Call;
import retrofit.http.Body;
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

  @GET("action")
  Call<ApiActionResponse> obtainAction(@QueryMap Map<String,String> parameters);
}
