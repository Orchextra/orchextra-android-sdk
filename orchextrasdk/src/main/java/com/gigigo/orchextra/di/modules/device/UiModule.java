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

package com.gigigo.orchextra.di.modules.device;

import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.control.presenters.scanner.OxCodeScannerPresenter;
import com.gigigo.orchextra.control.presenters.scanner.entities.mapper.ScannerResultMapper;
import com.gigigo.orchextra.di.qualifiers.BackThread;
import com.gigigo.orchextra.di.qualifiers.MainThread;
import com.gigigo.orchextra.di.qualifiers.ScannerInteractorExecution;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

@Module
public class UiModule {

    @Singleton
    @Provides ScannerResultMapper provideScannerResultMapper() {
        return new ScannerResultMapper();
    }

    @Singleton
    @Provides
    OxCodeScannerPresenter provideOxCodeScannerPresenter(@BackThread ThreadSpec threadSpec,
                                                         InteractorInvoker interactorInvoker,
                                                         @ScannerInteractorExecution Provider<InteractorExecution> scannerProvider,
                                                         ScannerResultMapper scannerResultMapper,
                                                         ActionDispatcher actionDispatcher,
                                                         @MainThread ThreadSpec mainThreadSpec) {

        return new OxCodeScannerPresenter(threadSpec, interactorInvoker, scannerProvider, scannerResultMapper,
                actionDispatcher, mainThreadSpec);
    }
}
