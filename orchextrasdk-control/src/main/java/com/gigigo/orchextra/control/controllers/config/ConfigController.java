package com.gigigo.orchextra.control.controllers.config;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.controllers.base.Controller;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.model.entities.App;
import com.gigigo.orchextra.domain.model.vo.Device;
import com.gigigo.orchextra.domain.model.vo.GeoLocation;
import com.gigigo.orchextra.domain.model.config.strategy.ConfigInfoResult;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;

import me.panavtec.threaddecoratedview.views.ThreadSpec;

public class ConfigController extends Controller<ConfigDelegate> {

    private final InteractorInvoker interactorInvoker;
    private final SendConfigInteractor sendConfigInteractor;
    private final ConfigObservable configObservable;

    public ConfigController(ThreadSpec mainThreadSpec, InteractorInvoker interactorInvoker,
                            SendConfigInteractor sendConfigInteractor,
                            ConfigObservable configObservable) {

        super(mainThreadSpec);

        this.interactorInvoker = interactorInvoker;
        this.sendConfigInteractor = sendConfigInteractor;
        this.configObservable = configObservable;
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
                        notifyChanges(result);
                    }
                })
                .execute(interactorInvoker);
    }

    private void notifyChanges(ConfigInfoResult result) {
        if (result.hasChanges()){
            configObservable.notifyObservers(result);
        }
    }
}
