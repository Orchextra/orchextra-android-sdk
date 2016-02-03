package com.gigigo.orchextra.control.controllers.config;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.controllers.base.Controller;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.entities.App;
import com.gigigo.orchextra.domain.entities.Device;
import com.gigigo.orchextra.domain.entities.GeoLocation;
import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;

import me.panavtec.threaddecoratedview.views.ThreadSpec;

public class ConfigController extends Controller<ConfigDelegate> {

    private final InteractorInvoker interactorInvoker;
    private final SendConfigInteractor sendConfigInteractor;

    public ConfigController(ThreadSpec mainThreadSpec, InteractorInvoker interactorInvoker,
                            SendConfigInteractor sendConfigInteractor) {
        super(mainThreadSpec);

        this.interactorInvoker = interactorInvoker;
        this.sendConfigInteractor = sendConfigInteractor;
    }

    @Override
    public void onDelegateAttached() {

    }

    public void sendConfiguration(App appInfo, Device deviceInfo, GeoLocation geoLocation) {
        sendConfigInteractor.setApp(appInfo);
        sendConfigInteractor.setDevice(deviceInfo);
        sendConfigInteractor.setGeoLocation(geoLocation);

        new InteractorExecution<>(sendConfigInteractor)
                .result(new InteractorResult<ConfigInfoResult>() {
                    @Override
                    public void onResult(ConfigInfoResult result) {

                    }
                })
                .execute(interactorInvoker);
    }
}
