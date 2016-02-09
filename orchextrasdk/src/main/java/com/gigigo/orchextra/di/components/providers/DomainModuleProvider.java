package com.gigigo.orchextra.di.components.providers;

import com.gigigo.orchextra.control.invoker.InteractorInvoker;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public interface DomainModuleProvider {
  InteractorInvoker provideInteractorInvoker();
}
