package com.gigigo.orchextra.android.beacons;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public interface Subject {
  void registerObserver(Observer o);
  void removeObserver(Observer o);
  void notifyObservers(Object data);
}
