package com.gigigo.orchextra.domain.abstractions.observer;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public interface Observer {
  void update(OrchextraChanges observable, Object data);
}
