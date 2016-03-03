package com.gigigo.orchextra.domain.abstractions.initialization;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public interface OrchextraManagerCompletionCallback {
  void onSuccess();
  void onError(String s);
}
