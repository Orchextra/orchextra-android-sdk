package com.gigigo.orchextra.control.controllers.config;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.controllers.base.Controller;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;
import com.gigigo.orchextra.domain.model.entities.App;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.model.vo.Device;
import com.gigigo.orchextra.domain.model.vo.GeoLocation;

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
                .result(new InteractorResult<OrchextraUpdates>() {
                    @Override public void onResult(OrchextraUpdates result) {
                        System.out.println("CONTROLLER SUCCESS :: " + result.toString());
                        if (result != null) {
                            notifyChanges(result);
                        }
                    }
                }).error(InteractorError.class, new InteractorResult<InteractorError>() {
            @Override public void onResult(InteractorError result) {
                    manageInteractorError(result);
                System.out.println("CONTROLLER ERROR :: " + result.toString());
            }
        }).execute(interactorInvoker);
    }

    private void manageInteractorError(InteractorError result) {
        //TODO
        InteractorError error = result;

        System.out.println("Error :: " + result.toString());

    }

    private void notifyChanges(OrchextraUpdates result) {
        if (result.hasChanges()){
            configObservable.notifyObservers(result);
        }
    }
}
