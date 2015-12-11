package com.gigigo.orchextra.di.modules;

import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.ggglib.network.mappers.Mapper;
import com.gigigo.orchextra.di.qualifiers.SdkDataResponseMapper;
import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.datasources.api.model.mappers.OrchextraGenericResponseMapper;
import gigigo.com.orchextra.data.datasources.datasources.api.model.mappers.SdkMapper;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
@Module
public class ResponseMappersModule {


  @Provides @Singleton @SdkDataResponseMapper ApiGenericResponseMapper provideApiGenericResponseMapper(SdkMapper sdkMapper){
    return createResponseMapper(sdkMapper);
  }

  @Provides @Singleton SdkMapper provideSdkMapper(){
   return new SdkMapper();
  }

  private ApiGenericResponseMapper createResponseMapper(Mapper mapper) {
    return new OrchextraGenericResponseMapper(mapper);
  }
}
