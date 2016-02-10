package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.control.invoker.InteractorInvoker;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public interface DomainModuleProvider extends InteractorsModuleProvider{
  InteractorInvoker provideInteractorInvoker();
}
