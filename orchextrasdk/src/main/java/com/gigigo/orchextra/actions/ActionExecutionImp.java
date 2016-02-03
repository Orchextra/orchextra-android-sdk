package com.gigigo.orchextra.actions;

import com.gigigo.orchextra.domain.entities.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.entities.actions.types.CustomAction;
import com.gigigo.orchextra.domain.entities.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.entities.actions.types.NotificationAction;
import com.gigigo.orchextra.domain.entities.actions.types.ScanAction;
import com.gigigo.orchextra.domain.entities.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.entities.actions.types.WebViewAction;
import com.gigigo.orchextra.domain.interactors.actions.ActionExecution;

public class ActionExecutionImp implements ActionExecution {

    private final BrowserActionExecutor browserActionExecutor;
    private final WebViewActionExecutor webViewActionExecutor;
    private final CustomActionExecutor customActionExecutor;
    private final ScanActionExecutor scanActionExecutor;
    private final VuforiaActionExecutor vuforiaActionExecutor;
    private final NotificationActionExecutor notificationActionExecutor;

    public ActionExecutionImp(BrowserActionExecutor browserActionExecutor,
                              WebViewActionExecutor webViewActionExecutor,
                              CustomActionExecutor customActionExecutor,
                              ScanActionExecutor scanActionExecutor,
                              VuforiaActionExecutor vuforiaActionExecutor,
                              NotificationActionExecutor notificationActionExecutor) {

        this.browserActionExecutor = browserActionExecutor;
        this.webViewActionExecutor = webViewActionExecutor;
        this.customActionExecutor = customActionExecutor;
        this.scanActionExecutor = scanActionExecutor;
        this.vuforiaActionExecutor = vuforiaActionExecutor;
        this.notificationActionExecutor = notificationActionExecutor;
    }

    @Override
    public void execute(BrowserAction action) {
        browserActionExecutor.execute(action);
    }

    @Override
    public void execute(WebViewAction action) {
        webViewActionExecutor.execute(action);
    }

    @Override
    public void execute(CustomAction action) {
        customActionExecutor.execute(action);
    }

    @Override
    public void execute(ScanAction action) {
        scanActionExecutor.execute(action);
    }

    @Override
    public void execute(VuforiaScanAction action) {
        vuforiaActionExecutor.execute(action);
    }

    @Override
    public void execute(NotificationAction action) {
        notificationActionExecutor.execute(action);
    }

    @Override
    public void execute(EmptyAction action) {

    }
}
