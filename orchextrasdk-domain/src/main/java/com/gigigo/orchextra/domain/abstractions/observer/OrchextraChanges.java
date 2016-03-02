package com.gigigo.orchextra.domain.abstractions.observer;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public interface OrchextraChanges {
  void registerObserver(Observer o);

  void removeObserver(Observer o);

  void notifyObservers(Object data);
}
