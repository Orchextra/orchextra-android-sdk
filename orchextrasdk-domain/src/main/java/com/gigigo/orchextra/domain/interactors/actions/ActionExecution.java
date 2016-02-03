package com.gigigo.orchextra.domain.interactors.actions;

import com.gigigo.orchextra.domain.entities.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.entities.actions.types.CustomAction;
import com.gigigo.orchextra.domain.entities.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.entities.actions.types.NotificationAction;
import com.gigigo.orchextra.domain.entities.actions.types.ScanAction;
import com.gigigo.orchextra.domain.entities.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.entities.actions.types.WebViewAction;

public interface ActionExecution {

    void execute(BrowserAction action);
    void execute(WebViewAction action);
    void execute(CustomAction action);
    void execute(ScanAction action);
    void execute(VuforiaScanAction action);
    void execute(NotificationAction action);
    void execute(EmptyAction action);
}
