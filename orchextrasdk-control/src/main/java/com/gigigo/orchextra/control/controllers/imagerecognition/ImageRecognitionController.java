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
package com.gigigo.orchextra.control.controllers.imagerecognition;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.control.presenters.scanner.entities.ScannerResultPresenter;
import com.gigigo.orchextra.control.presenters.scanner.entities.mapper.ScannerResultMapper;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.threads.ThreadSpec;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.error.GenericError;
import com.gigigo.orchextra.domain.interactors.scanner.ScannerInteractor;
import com.gigigo.orchextra.domain.interactors.scanner.ScannerType;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.ScannerResult;
import com.gigigo.orchextra.domain.model.entities.Vuforia;

import java.util.List;

import orchextra.javax.inject.Provider;

public class ImageRecognitionController {

    private final InteractorInvoker interactorInvoker;
    private final Provider<InteractorExecution> getImageRecognitionCredentialsExecutionProvider;
    private final Provider<InteractorExecution> scannerInteractorExecutionProvider;
    private final ActionDispatcher actionDispatcher;
    private final ThreadSpec mainThreadSpec;

    private final ScannerResultMapper mScannerResultMapper;
    private final OrchextraLogger mErrorLogger;

    public ImageRecognitionController(InteractorInvoker interactorInvoker,
                                      Provider<InteractorExecution> getImageRecognitionCredentialsExecutionProvider,
                                      Provider<InteractorExecution> scannerInteractorExecutionProvider,
                                      ActionDispatcher actionDispatcher,
                                      ThreadSpec mainThreadSpec,
                                      ScannerResultMapper scannerResultMapper,
                                      OrchextraLogger errorLogger
    ) {

        this.interactorInvoker = interactorInvoker;
        this.getImageRecognitionCredentialsExecutionProvider = getImageRecognitionCredentialsExecutionProvider;
        this.scannerInteractorExecutionProvider = scannerInteractorExecutionProvider;
        this.actionDispatcher = actionDispatcher;
        this.mainThreadSpec = mainThreadSpec;
        this.mScannerResultMapper = scannerResultMapper;
        this.mErrorLogger = errorLogger;
    }

    public void getCredentials(final OnImageRecognitionCredentialsReadyListener listener) {
        getImageRecognitionCredentialsExecutionProvider.get().result(new InteractorResult<Vuforia>() {
            @Override
            public void onResult(Vuforia result) {
                if (result != null) {
                    listener.onCredentialsReady(result);
                }
            }
        }).error(GenericError.class, new InteractorResult<GenericError>() {
            @Override
            public void onResult(GenericError result) {
                listener.onCredentialsError(result.getError().getMessage());
            }
        }).execute(interactorInvoker);
    }

    /**
     * This method makes de call for send the info to the server
     *
     * @param patternId value of identificate pattern to send
     */
    public void recognizedPattern(String patternId) {
        InteractorExecution interactorExecution = scannerInteractorExecutionProvider.get();

        final ScannerResult scanResult = obtainMappedScannerResult(patternId);
        ScannerInteractor scannerInteractor = (ScannerInteractor) interactorExecution.getInteractor();
        scannerInteractor.setScanner(scanResult);

        interactorExecution.result(new InteractorResult<List<BasicAction>>() {
            @Override
            public void onResult(List<BasicAction> actions) {
                executeActions(actions);
            }
        }).error(GenericError.class, new InteractorResult<GenericError>() {
            @Override
            public void onResult(GenericError result) {
                manageGenericError(result);
            }
        }).execute(interactorInvoker);
    }

    private void manageGenericError(GenericError result) {
        mErrorLogger.log(result.getError().toString());
    }

    private ScannerResult obtainMappedScannerResult(String patternId) {
        final ScannerResultPresenter scanResult = new ScannerResultPresenter();
        scanResult.setContent(patternId);
        scanResult.setType(ScannerType.IMAGE_RECOGNITION.toString());

        return this.mScannerResultMapper.mapDataToModel(scanResult);
    }

    private void executeActions(List<BasicAction> actions) {
        for (final BasicAction action : actions) {
            mainThreadSpec.execute(new Runnable() {
                @Override
                public void run() {
                    action.performAction(actionDispatcher);
                }
            });
        }
    }
}
