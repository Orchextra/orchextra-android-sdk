package com.gigigo.orchextra.initalization;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.android.applifecycle.OrchextraActivityLifecycle;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public interface OrchextraContextProvider extends ContextProvider {
  void setOrchextraActivityLifecycle(OrchextraActivityLifecycle orchextraActivityLifecycle);
}
