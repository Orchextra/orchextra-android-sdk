package com.gigigo.orchextra.delegates;

import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.device.information.AndroidApp;
import com.gigigo.orchextra.device.information.AndroidDevice;
import com.gigigo.orchextra.device.geolocation.geocoder.AndroidGeolocationManager;
import com.gigigo.orchextra.control.controllers.config.ConfigController;
import com.gigigo.orchextra.control.controllers.config.ConfigDelegate;
import com.gigigo.orchextra.di.components.DelegateComponent;
import com.gigigo.orchextra.domain.model.entities.App;
import com.gigigo.orchextra.domain.model.vo.Device;
import com.gigigo.orchextra.domain.model.vo.GeoLocation;

import javax.inject.Inject;

public class ConfigDelegateImp implements ConfigDelegate {

    private DelegateComponent delegateComponent;

    @Inject
    ConfigController configController;

    @Inject
    AndroidApp androidApp;

    @Inject
    AndroidDevice androidDevice;

    @Inject
    AndroidGeolocationManager androidGeolocationManager;

    @Override
    public void init() {
        delegateComponent = Orchextra.getInjector().injectConfigDelegate(this);
        configController.attachDelegate(this);
    }

    @Override
    public void destroy() {
        delegateComponent = null;
        configController.detachDelegate();
    }

    public void sendConfiguration() {
        getDeviceGeoLocation();
    }

    public void getDeviceGeoLocation() {
        androidGeolocationManager.setRetrieveGeolocationListener(retrieveGeolocationListener);
        androidGeolocationManager.retrieveGeolocation();
    }

    @Override
    public void configSuccessful() {

    }

    @Override
    public void configError() {

    }

    private AndroidGeolocationManager.RetrieveGeolocationListener retrieveGeolocationListener =
            new AndroidGeolocationManager.RetrieveGeolocationListener() {
                @Override
                public void retrieveGeolocation(GeoLocation geoLocation) {
                    configuration(geoLocation);
                }
            };

    public void configuration(GeoLocation geoLocation) {
        App appInfo = androidApp.getAndroidAppInfo();
        Device deviceInfo = androidDevice.getAndroidDeviceInfo();

        configController.sendConfiguration(appInfo, deviceInfo, geoLocation);
    }
}
