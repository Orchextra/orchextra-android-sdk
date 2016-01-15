package com.gigigo.orchextra;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.gigigo.orchextra.domain.entities.triggers.PhoneStatusType;

public class OrchextraActivityLifecycle implements Application.ActivityLifecycleCallbacks {


    private Activity currentActivity;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        this.currentActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        this.currentActivity = null;
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public PhoneStatusType getPhoneStatusType() {
        return currentActivity != null ? PhoneStatusType.FOREGROUND : PhoneStatusType.BACKGROUND;
    }
}
