package com.gigigo.orchextra.domain.abstractions.actions;

import com.gigigo.orchextra.domain.model.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.model.actions.types.ScanAction;
import com.gigigo.orchextra.domain.model.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.model.actions.types.WebViewAction;

public interface ActionExecution {

    void execute(BrowserAction action);
    void execute(WebViewAction action);
    void execute(ScanAction action);
    void execute(VuforiaScanAction action);

}
