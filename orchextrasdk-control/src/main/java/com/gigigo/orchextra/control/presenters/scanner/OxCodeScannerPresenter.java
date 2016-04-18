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

package com.gigigo.orchextra.control.presenters.scanner;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.control.presenters.base.Presenter;
import com.gigigo.orchextra.control.presenters.scanner.entities.ScannerResultPresenter;
import com.gigigo.orchextra.control.presenters.scanner.entities.mapper.ScannerResultMapper;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.error.GenericError;
import com.gigigo.orchextra.domain.interactors.scanner.ScannerInteractor;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.ScannerResult;

import java.util.List;

import orchextra.javax.inject.Provider;

import me.panavtec.threaddecoratedview.views.ThreadSpec;

public class OxCodeScannerPresenter extends Presenter<OxCodeScannerView> {

    private final InteractorInvoker interactorInvoker;
    private final Provider<InteractorExecution> scannerInteractorExecutionProvider;
    private final ScannerResultMapper scannerResultMapper;
    private final ActionDispatcher actionDispatcher;
    private final ThreadSpec mainThreadSpec;

    public OxCodeScannerPresenter(ThreadSpec threadSpec,
                                  InteractorInvoker interactorInvoker,
                                  Provider<InteractorExecution> scannerInteractorExecutionProvider,
                                  ScannerResultMapper scannerResultMapper,
                                  ActionDispatcher actionDispatcher,
                                  ThreadSpec mainThreadSpec) {
        super(threadSpec);

        this.interactorInvoker = interactorInvoker;
        this.scannerInteractorExecutionProvider = scannerInteractorExecutionProvider;
        this.scannerResultMapper = scannerResultMapper;
        this.actionDispatcher = actionDispatcher;
        this.mainThreadSpec = mainThreadSpec;
    }

    @Override
    public void onViewAttached() {
        getView().initUi();
    }

    public void sendScannerResult(ScannerResultPresenter scannerResponsePresenter) {
        ScannerResult scannerResult = scannerResultMapper.mapDataToModel(scannerResponsePresenter);

        InteractorExecution interactorExecution = scannerInteractorExecutionProvider.get();
        ScannerInteractor scannerInteractor = (ScannerInteractor) interactorExecution.getInteractor();
        scannerInteractor.setScanner(scannerResult);

        interactorExecution.result(new InteractorResult<List<BasicAction>>() {
            @Override
            public void onResult(List<BasicAction> actions) {
                executeActions(actions);
            }
        }).error(GenericError.class, new InteractorResult<InteractorError>() {
            @Override
            public void onResult(InteractorError result) {

            }
        }).execute(interactorInvoker);
    }

    private void executeActions(List<BasicAction> actions) {
        for (final BasicAction action : actions) {
            mainThreadSpec.execute(new Runnable() {
                @Override public void run() {
                    action.performAction(actionDispatcher);
                }
            });
        }
    }
}
