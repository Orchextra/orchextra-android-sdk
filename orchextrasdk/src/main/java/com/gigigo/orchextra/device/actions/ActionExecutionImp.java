/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
