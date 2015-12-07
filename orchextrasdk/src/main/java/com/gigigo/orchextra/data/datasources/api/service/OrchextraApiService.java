package com.gigigo.orchextra.data.datasources.api.service;

import com.gigigo.orchextra.data.datasources.api.model.responses.ApiSdkAuthenticationResponse;
import retrofit.Call;
import retrofit.http.POST;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
public interface OrchextraApiService {

  @POST Call<ApiSdkAuthenticationResponse> sdkAuthentication();

}
