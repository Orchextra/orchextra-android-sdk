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

package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.BuildConfig;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.di.components.InteractorExecutionComponent;
import com.gigigo.orchextra.di.modules.data.DataProviderModule;
import com.gigigo.orchextra.di.qualifiers.BeaconEventsInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.ConfigInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.GeofenceInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.GeofenceProviderInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.GetIrCredentialsInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.ObtainThemeInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.RegionsProviderInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.SaveUserInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.ScannerInteractorExecution;
import com.gigigo.orchextra.domain.invoker.InteractorInvokerImp;
import com.gigigo.orchextra.domain.invoker.InteractorOutputThreadFactory;
import com.gigigo.orchextra.domain.invoker.InteractorPriorityBlockingQueue;
import com.gigigo.orchextra.domain.invoker.LogExceptionHandler;
import com.gigigo.orchextra.domain.invoker.PriorizableThreadPoolExecutor;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.sdk.OrchextraManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import orchextra.javax.inject.Singleton;

import orchextra.dagger.Module;
import orchextra.dagger.Provides;


@Module(includes = DataProviderModule.class)
public class DomainModule {

  @Provides @Singleton InteractorInvoker provideInteractorInvoker(ExecutorService executor,
      LogExceptionHandler logExceptionHandler) {
    return new InteractorInvokerImp(executor, logExceptionHandler);
  }

  @Provides @Singleton Session provideSession() {
    return new Session(BuildConfig.TOKEN_TYPE_BEARER);
  }

  @Provides @Singleton LogExceptionHandler provideLogExceptionHandler() {
    return new LogExceptionHandler();
  }

  @Provides @Singleton ExecutorService provideExecutor(ThreadFactory threadFactory,
      BlockingQueue<Runnable> blockingQueue) {
    return new PriorizableThreadPoolExecutor(BuildConfig.CONCURRENT_INTERACTORS,
        BuildConfig.CONCURRENT_INTERACTORS, 0L, TimeUnit.MILLISECONDS, blockingQueue,
        threadFactory);
  }

  @RegionsProviderInteractorExecution @Provides InteractorExecution provideRegionsProviderInteractorExecution() {
    InteractorExecution interactorExecution = new InteractorExecution();
    InteractorExecutionComponent interactorExecutionComponent = OrchextraManager.getInjector().injectRegionsProviderInteractorExecution(interactorExecution);
    interactorExecution.setInteractor(interactorExecutionComponent.provideRegionsProviderInteractor());
    return interactorExecution;
  }

  @BeaconEventsInteractorExecution @Provides InteractorExecution provideBeaconEventsInteractorExecution() {
    InteractorExecution interactorExecution = new InteractorExecution();
    InteractorExecutionComponent interactorExecutionComponent = OrchextraManager.getInjector().injectBeaconEventsInteractorExecution(
            interactorExecution);
    interactorExecution.setInteractor(interactorExecutionComponent.provideBeaconEventsInteractor());
    return interactorExecution;
  }

  @ConfigInteractorExecution @Provides InteractorExecution provideConfigInteractorExecution() {
    InteractorExecution interactorExecution = new InteractorExecution();
    InteractorExecutionComponent interactorExecutionComponent = OrchextraManager.getInjector().injectConfigInteractorInteractorExecution(
            interactorExecution);
    interactorExecution.setInteractor(interactorExecutionComponent.provideSendConfigInteractor());
    return interactorExecution;
  }

  @GeofenceInteractorExecution @Provides InteractorExecution provideGeofenceInteractorExecution() {
    InteractorExecution interactorExecution = new InteractorExecution();
    InteractorExecutionComponent interactorExecutionComponent = OrchextraManager.getInjector().injectGeofenceInteractorExecution(
            interactorExecution);
    interactorExecution.setInteractor(interactorExecutionComponent.provideGeofenceInteractor());
    return interactorExecution;
  }

  @GeofenceProviderInteractorExecution @Provides InteractorExecution provideGeofenceProviderInteractorExecution() {
    InteractorExecution interactorExecution = new InteractorExecution();
    InteractorExecutionComponent interactorExecutionComponent = OrchextraManager.getInjector().injectGeofenceProviderInteractorExecution(
        interactorExecution);
    interactorExecution.setInteractor(interactorExecutionComponent.provideGeofenceProviderInteractor());
    return interactorExecution;
  }

  @SaveUserInteractorExecution @Provides InteractorExecution provideSaveUserInteractorExecution() {
    InteractorExecution interactorExecution = new InteractorExecution();
    InteractorExecutionComponent interactorExecutionComponent = OrchextraManager.getInjector().injectSaveUserInteractorExecution(
            interactorExecution);
    interactorExecution.setInteractor(interactorExecutionComponent.provideSaveUserInteractor());
    return interactorExecution;
  }

  @ObtainThemeInteractorExecution @Provides InteractorExecution provideObtainThemeInteractorExecution() {
    InteractorExecution interactorExecution = new InteractorExecution();
    InteractorExecutionComponent interactorExecutionComponent = OrchextraManager.getInjector().injectObtainThemeInteractorExecution(
            interactorExecution);
    interactorExecution.setInteractor(interactorExecutionComponent.provideObtainThemeInteractor());
    return interactorExecution;
  }

  @GetIrCredentialsInteractorExecution @Provides InteractorExecution provideObtainIrCredentialsInteractorExecution() {
    InteractorExecution interactorExecution = new InteractorExecution();
    InteractorExecutionComponent interactorExecutionComponent = OrchextraManager.getInjector().injectObtainIrCredentialsInteractorExecution(
        interactorExecution);
    interactorExecution.setInteractor(interactorExecutionComponent.provideGetImageRecognitionCredentialsInteractor());
    return interactorExecution;
  }

  @ScannerInteractorExecution @Provides InteractorExecution provideScannerInteractorExecution() {
    InteractorExecution interactorExecution = new InteractorExecution();
    InteractorExecutionComponent interactorExecutionComponent = OrchextraManager.getInjector().injectScannerInteractorExecution(
            interactorExecution);
    interactorExecution.setInteractor(interactorExecutionComponent.provideScannerInteractor());
    return interactorExecution;
  }

  @Provides @Singleton public BlockingQueue<Runnable> provideBlockingQueue() {
    return new InteractorPriorityBlockingQueue(100);
  }

  @Provides @Singleton ThreadFactory provideThreadFactory() {
    return new InteractorOutputThreadFactory();
  }
}
