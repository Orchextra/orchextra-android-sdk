package com.gigigo.orchextra.di.components;

import android.content.Context;
import com.gigigo.orchextra.di.modules.DataModule;
import com.gigigo.orchextra.di.modules.OrchextraModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Singleton @Component(modules = {OrchextraModule.class, DataModule.class})
public interface OrchextraComponent {
  Context provideContext();
}
