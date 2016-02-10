package com.gigigo.orchextra.domain.interactors.error;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public interface AuthErrorHandler {
  void authenticateWhenError(PendingInteractorExecution pendingInteractorExecution);
}
