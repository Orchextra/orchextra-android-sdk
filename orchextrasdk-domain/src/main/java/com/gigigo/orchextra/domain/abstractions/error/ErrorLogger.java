package com.gigigo.orchextra.domain.abstractions.error;

import com.gigigo.gggjavalib.business.model.BusinessError;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/2/16.
 */
public interface ErrorLogger {
  void log(BusinessError businessError);
  void log(String message);
}
