package com.gigigo.orchextra.di.modules;

import android.content.Context;
import com.gigigo.orchextra.di.qualifiers.BackThread;
import com.gigigo.orchextra.domain.outputs.BackThreadSpec;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Module(includes = ControllersModule.class)
public class OrchextraModule {

  private Context context;

  public OrchextraModule(Context context) {
    this.context = context;
  }

  @Provides @Singleton Context provideApplicationContext(){
    return context;
  }

  @Singleton @Provides @BackThread ThreadSpec provideBackThread(){
    return new BackThreadSpec();
  }
}
