package com.gigigo.orchextra.domain.initalization.observables;

import com.gigigo.orchextra.domain.abstractions.observer.Observer;
import com.gigigo.orchextra.domain.abstractions.observer.OrchextraChanges;

import java.util.ArrayList;

/**
 * Created by nubor on 18/11/2016.
 */
public class ConfigChangeObservable implements OrchextraChanges {

    private ArrayList<Observer> observers;

    public ConfigChangeObservable() {
        this.observers = new ArrayList<>();
    }

    @Override public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override public void removeObserver(Observer o) {
        int index = observers.indexOf(o);
        if (index >= 0) {
            observers.remove(index);
        }
    }

    @Override public void notifyObservers(Object configData) {
        for (Observer observer : observers) {
            observer.update(this, configData);
        }
    }
}
