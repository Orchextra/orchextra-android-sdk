package com.gigigo.orchextra.di.modules;

import com.gigigo.ggglib.network.converters.ErrorConverter;
import com.gigigo.ggglib.network.defaultelements.RetryOnErrorPolicy;
import com.gigigo.ggglib.network.executors.ApiServiceExecutor;
import com.gigigo.ggglib.network.executors.RetrofitApiServiceExcecutor;
import com.gigigo.orchextra.BuildConfig;
import com.gigigo.orchextra.domain.entities.Session;
import com.gigigo.orchextra.qualifiers.data.AcceptLanguage;
import com.gigigo.orchextra.qualifiers.data.ApiVersion;
import com.gigigo.orchextra.qualifiers.data.Endpoint;
import com.gigigo.orchextra.qualifiers.data.HeadersInterceptor;
import com.gigigo.orchextra.qualifiers.data.RetrofitLog;
import com.gigigo.orchextra.qualifiers.data.XAppSdk;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.List;
import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.api.interceptors.Headers;
import gigigo.com.orchextra.data.datasources.api.model.responses.base.OrchextraApiErrorResponse;
import gigigo.com.orchextra.data.datasources.api.service.DefatultErrorConverterImpl;
import gigigo.com.orchextra.data.datasources.api.service.DefaultRetryOnErrorPolicyImpl;
import gigigo.com.orchextra.data.datasources.api.service.OrchextraApiService;
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
    Session provideSession() {
        return new Session(BuildConfig.TOKEN_TYPE_BEARER);
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
        return new DefatultErrorConverterImpl(retrofit, OrchextraApiErrorResponse.class);
    }

    @Provides
    @Singleton
    RetryOnErrorPolicy provideRetryOnErrorPolicy() {
        return new DefaultRetryOnErrorPolicyImpl();
    }

}
