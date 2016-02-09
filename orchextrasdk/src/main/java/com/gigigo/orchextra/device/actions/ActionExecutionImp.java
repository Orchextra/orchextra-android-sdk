package com.gigigo.orchextra.device.actions;

import com.gigigo.orchextra.domain.abstractions.actions.ActionExecution;
import com.gigigo.orchextra.domain.model.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.model.actions.types.ScanAction;
import com.gigigo.orchextra.domain.model.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.model.actions.types.WebViewAction;

public class ActionExecutionImp implements ActionExecution {

    private final BrowserActionExecutor browserActionExecutor;
    private final WebViewActionExecutor webViewActionExecutor;
    private final ScanActionExecutor scanActionExecutor;
    private final VuforiaActionExecutor vuforiaActionExecutor;

    public ActionExecutionImp(BrowserActionExecutor browserActionExecutor,
                              WebViewActionExecutor webViewActionExecutor,
                              ScanActionExecutor scanActionExecutor,
                              VuforiaActionExecutor vuforiaActionExecutor) {

        this.browserActionExecutor = browserActionExecutor;
        this.webViewActionExecutor = webViewActionExecutor;
        this.scanActionExecutor = scanActionExecutor;
        this.vuforiaActionExecutor = vuforiaActionExecutor;
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
    public void execute(ScanAction action) {
        scanActionExecutor.execute(action);
    }

    @Override
    public void execute(VuforiaScanAction action) {
        vuforiaActionExecutor.execute(action);
    }

}
