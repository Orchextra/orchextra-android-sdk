package com.gigigo.orchextra.domain.abstractions.initialization;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public interface OrchextraCompletionCallback {
  void onSuccess();
  void onError(String s);
}
