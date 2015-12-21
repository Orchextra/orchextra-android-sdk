package com.gigigo.orchextra.di.modules;

import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.ggglib.network.mappers.ResponseMapper;
import com.gigigo.orchextra.di.qualifiers.ClientDataResponseMapper;
import com.gigigo.orchextra.di.qualifiers.ConfigResponseMapper;
import com.gigigo.orchextra.di.qualifiers.SdkDataResponseMapper;
import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.ClientApiResponseMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.ConfigApiResponseMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.OrchextraGenericResponseMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.SdkApiResponseMapper;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
@Module
public class ApiMappersModule {

  @Provides @Singleton @SdkDataResponseMapper ApiGenericResponseMapper
  provideSdkDataResponseMapper(SdkApiResponseMapper sdkMapper){
    return createResponseMapper(sdkMapper);
  }

  @Provides @Singleton @ClientDataResponseMapper ApiGenericResponseMapper
  provideClientDataResponseMapper(ClientApiResponseMapper clientMapper){
    return createResponseMapper(clientMapper);
  }

  @Provides @Singleton SdkApiResponseMapper provideSdkMapper(){
   return new SdkApiResponseMapper();
  }

  @Provides @Singleton ClientApiResponseMapper provideClientMapper(){
    return new ClientApiResponseMapper();
  }

  @Provides @Singleton @ConfigResponseMapper ConfigApiResponseMapper provideConfigResponseMapper(){
    //TODO this and many more mappers
    return new ConfigApiResponseMapper(null, null, null, null);
  }


  private ApiGenericResponseMapper createResponseMapper(ResponseMapper mapper) {
    return new OrchextraGenericResponseMapper(mapper);
  }
}
