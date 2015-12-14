package com.gigigo.orchextra.di.modules;

import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.ggglib.network.mappers.Mapper;
import com.gigigo.orchextra.di.qualifiers.ClientDataResponseMapper;
import com.gigigo.orchextra.di.qualifiers.SdkDataResponseMapper;
import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.api.model.mappers.ClientMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.OrchextraGenericResponseMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.SdkMapper;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
@Module
public class ApiMappersModule {


  @Provides @Singleton @SdkDataResponseMapper ApiGenericResponseMapper
  provideSdkDataResponseMapper(SdkMapper sdkMapper){
    return createResponseMapper(sdkMapper);
  }

  @Provides @Singleton @ClientDataResponseMapper ApiGenericResponseMapper
  provideClientDataResponseMapper(ClientMapper clientMapper){
    return createResponseMapper(clientMapper);
  }

  @Provides @Singleton SdkMapper provideSdkMapper(){
   return new SdkMapper();
  }

  @Provides @Singleton ClientMapper provideClientMapper(){
    return new ClientMapper();
  }

  private ApiGenericResponseMapper createResponseMapper(Mapper mapper) {
    return new OrchextraGenericResponseMapper(mapper);
  }
}
