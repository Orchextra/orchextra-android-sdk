package com.gigigo.orchextra.di.modules;

import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.ggglib.network.mappers.Mapper;
import com.gigigo.orchextra.di.qualifiers.ClientDataResponseMapper;
import com.gigigo.orchextra.di.qualifiers.SdkDataResponseMapper;
import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.api.model.mappers.ClientApiResponseMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.OrchextraGenericResponseMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.SdkApiResponseMapper;
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

  private ApiGenericResponseMapper createResponseMapper(Mapper mapper) {
    return new OrchextraGenericResponseMapper(mapper);
  }
}
