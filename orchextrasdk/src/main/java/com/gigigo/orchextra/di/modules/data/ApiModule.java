package com.gigigo.orchextra.di.modules.data;

import com.gigigo.ggglib.network.converters.ErrorConverter;
import com.gigigo.ggglib.network.defaultelements.RetryOnErrorPolicy;
import com.gigigo.ggglib.network.executors.ApiServiceExecutor;
import com.gigigo.ggglib.network.executors.RetrofitApiServiceExcecutor;
import com.gigigo.orchextra.di.modules.data.qualifiers.AcceptLanguage;
import com.gigigo.orchextra.di.modules.data.qualifiers.ApiVersion;
import com.gigigo.orchextra.di.modules.data.qualifiers.Endpoint;
import com.gigigo.orchextra.di.modules.data.qualifiers.HeadersInterceptor;
import com.gigigo.orchextra.di.modules.data.qualifiers.RetrofitLog;
import com.gigigo.orchextra.di.modules.data.qualifiers.XAppSdk;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import gigigo.com.orchextra.data.datasources.api.model.responses.base.BaseOrchextraApiResponse;
import java.util.List;
import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.api.interceptors.Headers;
import gigigo.com.orchextra.data.datasources.api.service.DefatultErrorConverterImpl;
import gigigo.com.orchextra.data.datasources.api.service.DefaultRetryOnErrorPolicyImpl;
import gigigo.com.orchextra.data.datasources.api.service.OrchextraApiService;

import com.gigigo.orchextra.BuildConfig;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    @Endpoint
    String provideEndpoint() {
        return BuildConfig.API_URL;
    }

    @Provides
    @Singleton
    @ApiVersion
    String provideApiVersion() {
        return BuildConfig.API_VERSION;
    }

    @Provides
    @Singleton
    @RetrofitLog
    boolean provideRetrofitLog() {
        return BuildConfig.RETROFIT_LOG;
    }

    @Provides
    @Singleton
    @XAppSdk
    String provideXAppSdk() {
        //TODO Verify two decimals in version name
        return BuildConfig.X_APP_SDK + "_" + BuildConfig.VERSION_NAME;
    }

    @Provides
    @Singleton
    @AcceptLanguage
    String provideAcceptLanguage() {
        return Locale.getDefault().toString();
    }

    @Provides
    @Singleton
    Retrofit provideOrchextraRetrofitObject(
            @Endpoint String enpoint, @ApiVersion String version,
            GsonConverterFactory gsonConverterFactory, OkHttpClient okClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(enpoint + version)
                .client(okClient)
                .addConverterFactory(gsonConverterFactory).build();

        return retrofit;

    }

    @Provides
    @Singleton
    OrchextraApiService provideOrchextraApiService(Retrofit retrofit) {
        return retrofit.create(OrchextraApiService.class);
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


    @Provides
    @Singleton
    @HeadersInterceptor
    Interceptor provideHeadersInterceptor(@XAppSdk String xAppSdk, @AcceptLanguage String acceptLanguage, Session session) {
        return new Headers(xAppSdk, acceptLanguage, session);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkClient(@RetrofitLog boolean retrofitLog,
                                 @HeadersInterceptor Interceptor headersInterceptor, HttpLoggingInterceptor loggingInterceptor) {

        OkHttpClient okHttpClient = new OkHttpClient();

        List<Interceptor> interceptors = okHttpClient.interceptors();
        interceptors.add(headersInterceptor);

//        if (retrofitLog) {
            interceptors.add(loggingInterceptor);
//        }

        return okHttpClient;
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    ApiServiceExecutor provideApiServiceExecutor(ErrorConverter errorConverter,
                                                 RetryOnErrorPolicy retryOnErrorPolicy) {
        return new RetrofitApiServiceExcecutor.Builder()
                .errorConverter(errorConverter)
                .retryOnErrorPolicy(retryOnErrorPolicy).build();
    }

    @Provides
    @Singleton
    ErrorConverter provideErrorConverter(Retrofit retrofit) {
        return new DefatultErrorConverterImpl(retrofit, BaseOrchextraApiResponse.class);
    }

    @Provides
    @Singleton
    RetryOnErrorPolicy provideRetryOnErrorPolicy() {
        return new DefaultRetryOnErrorPolicyImpl();
    }

}
