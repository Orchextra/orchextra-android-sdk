package gigigo.com.orchextra.data.datasources.datasources.api.service;

import gigigo.com.orchextra.data.datasources.datasources.api.model.responses.ApiSdkAuthenticationResponse;
import retrofit.Call;
import retrofit.http.POST;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
public interface OrchextraApiService {

  @POST Call<ApiSdkAuthenticationResponse> sdkAuthentication();

}
