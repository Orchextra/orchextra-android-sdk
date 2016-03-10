package com.gigigo.orchextra.domain.abstractions.future;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 7/3/16.
 */
public interface FutureResultNotifier<T> {

  void onReadyFutureResult(T orchextraStatus);

  void onErrorFutureResult(String error);
}
