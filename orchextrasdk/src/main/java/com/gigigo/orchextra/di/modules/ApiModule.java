package com.gigigo.orchextra.di.modules;

import android.os.Build;
import com.gigigo.orchextra.BuildConfig;
import com.gigigo.orchextra.data.datasources.api.service.OrchextraApiService;
import com.gigigo.orchextra.data.datasources.api.interceptors.Headers;
import com.gigigo.orchextra.qualifiers.data.Endpoint;
import com.gigigo.orchextra.qualifiers.data.HeadersInterceptor;
import com.gigigo.orchextra.qualifiers.data.RetrofitLog;
import com.gigigo.orchextra.qualifiers.data.UserAgent;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Singleton;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Module
public class ApiModule {

  @Provides @Singleton @Endpoint String provideEndpoint() {
    return BuildConfig.API_URL;
  }

  @Provides @Singleton @RetrofitLog boolean provideRetrofitLog() {
    return BuildConfig.RETROFIT_LOG;
  }

  @Provides @Singleton @UserAgent String provideUserAgent() {
    //TODO set user-Agent info required here, this is just an example:
    return String.format("Android ; %s; %s; %s; %d;", Build.MANUFACTURER, Build.MODEL,
        Build.VERSION.RELEASE, BuildConfig.VERSION_CODE);
  }

  @Provides @Singleton OrchextraApiService provideOrchextraApiService(
      @Endpoint String enpoint, GsonConverterFactory gsonConverterFactory, OkHttpClient okClient) {

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(enpoint)
        .client(okClient)
        .addConverterFactory(gsonConverterFactory).build();

    return retrofit.create(OrchextraApiService.class);

  }

  @Provides @Singleton HttpLoggingInterceptor provideLoggingInterceptor() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    return interceptor;
  }


  @Provides @Singleton @HeadersInterceptor Interceptor provideHeadersInterceptor(
      @UserAgent String userAgent) {
    return new Headers(userAgent);
  }

  @Provides @Singleton OkHttpClient provideOkClient(@RetrofitLog boolean retrofitLog,
      @HeadersInterceptor Interceptor headersInterceptor,
      HttpLoggingInterceptor loggingInterceptor) {
    OkHttpClient okHttpClient = new OkHttpClient();
    List<Interceptor> interceptors = okHttpClient.interceptors();
    interceptors.add(headersInterceptor);

    if (retrofitLog){
      interceptors.add(loggingInterceptor);
    }

    return okHttpClient;
  }

  @Provides @Singleton GsonConverterFactory provideGsonConverterFactory() {
    return GsonConverterFactory.create();
  }
}
