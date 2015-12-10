package com.gigigo.orchextra.di.components;

import android.content.Context;
import com.gigigo.orchextra.control.controllers.authentication.AuthenticationController;
import com.gigigo.orchextra.di.modules.OrchextraModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Singleton @Component(modules = {OrchextraModule.class})
public interface OrchextraComponent {
  Context provideContext();
  AuthenticationController provideAuthenticationController();
}
