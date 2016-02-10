package com.gigigo.orchextra.device.information;

import com.gigigo.orchextra.BuildConfig;
import com.gigigo.orchextra.domain.model.entities.App;

public class AndroidApp {

    public App getAndroidAppInfo() {
        App app = new App();

        app.setAppVersion(BuildConfig.VERSION_NAME);
        app.setBuildVersion(String.valueOf(BuildConfig.VERSION_CODE));
        app.setBundleId(BuildConfig.APPLICATION_ID);

        return app;
    }
}
