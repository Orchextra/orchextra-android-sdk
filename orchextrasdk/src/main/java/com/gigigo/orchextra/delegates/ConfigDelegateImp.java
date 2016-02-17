package com.gigigo.orchextra.delegates;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.control.controllers.config.ConfigController;
import com.gigigo.orchextra.control.controllers.config.ConfigDelegate;

public class ConfigDelegateImp implements ConfigDelegate {

    private final ConfigController configController;

    public ConfigDelegateImp(ConfigController configController) {
        this.configController = configController;
    }

    @Override
    public void init() {
        configController.attachDelegate(this);
    }

    @Override
    public void destroy() {
        configController.detachDelegate();
    }

    public void sendConfiguration() {
        init();
        configController.sendConfiguration();
    }

    @Override
    public void configSuccessful() {
        destroy();
    }

    @Override
    public void configError(BusinessError result) {
        GGGLogImpl.log("Config Error: " + result.getMessage(), LogLevel.ERROR);
        destroy();
    }

}
