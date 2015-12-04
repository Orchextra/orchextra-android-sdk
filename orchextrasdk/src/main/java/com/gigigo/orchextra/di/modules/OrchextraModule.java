package com.gigigo.orchextra.di.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Module(includes = {ApiModule.class})
public class OrchextraModule {

  private Context context;

  public OrchextraModule(Context context) {
    this.context = context;
  }

  @Provides @Singleton Context provideApplicationContext(){
    return context;
  }

}
