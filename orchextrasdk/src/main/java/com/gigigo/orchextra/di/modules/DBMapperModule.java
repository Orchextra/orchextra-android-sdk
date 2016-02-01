package com.gigigo.orchextra.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ClientAuthCredentialsRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ClientAuthRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.CrmRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.KeyWordRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.SdkAuthCredentialsRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.SdkAuthReamlMapper;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
@Module
public class DBMapperModule {

    @Singleton
    @Provides
    SdkAuthReamlMapper provideSdkAuthReamlMapper() {
        return new SdkAuthReamlMapper();
    }

    @Singleton
    @Provides
    ClientAuthRealmMapper provideClientAuthRealmMapper() {
        return new ClientAuthRealmMapper();
    }

    @Singleton
    @Provides
    KeyWordRealmMapper provideKeyWordRealmMapper() {
        return new KeyWordRealmMapper();
    }

    @Singleton
    @Provides
    CrmRealmMapper provideCrmRealmMapper(KeyWordRealmMapper keyWordRealmMapper) {
        return new CrmRealmMapper(keyWordRealmMapper);
    }

    @Singleton
    @Provides
    SdkAuthCredentialsRealmMapper providedkCredentialsRealmMapper() {
        return new SdkAuthCredentialsRealmMapper();
    }

    @Singleton
    @Provides
    ClientAuthCredentialsRealmMapper provideClientCredentialsRealmMapper() {
        return new ClientAuthCredentialsRealmMapper();
    }
}
