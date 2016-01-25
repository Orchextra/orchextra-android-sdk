package com.gigigo.orchextra.domain.entities.actions.strategy;

public class EmptyNotifFunctionalityImpl implements NotifFunctionality {

    @Override
    public Notification getNotification() {
        return null;
    }

    @Override
    public boolean isSupported() {
        return false;
    }
}
