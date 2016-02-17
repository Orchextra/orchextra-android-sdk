package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.BuildConfig;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.di.components.InteractorExecutionComponent;
import com.gigigo.orchextra.di.modules.data.DataProviderModule;
import com.gigigo.orchextra.di.qualifiers.RegionsProviderInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.BeaconEventsInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.ConfigInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.GeofenceInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.SaveUserInteractorExecution;
import com.gigigo.orchextra.domain.invoker.InteractorInvokerImp;
import com.gigigo.orchextra.domain.invoker.InteractorOutputThreadFactory;
import com.gigigo.orchextra.domain.invoker.InteractorPriorityBlockingQueue;
import com.gigigo.orchextra.domain.invoker.LogExceptionHandler;
import com.gigigo.orchextra.domain.invoker.PriorizableThreadPoolExecutor;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
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
    InteractorExecutionComponent interactorExecutionComponent = Orchextra.getInjector().injectRegionsProviderInteractorExecution(interactorExecution);
    interactorExecution.setInteractor(interactorExecutionComponent.provideRegionsProviderInteractor());
    return interactorExecution;
  }

  @BeaconEventsInteractorExecution @Provides InteractorExecution provideBeaconEventsInteractorExecution() {
    InteractorExecution interactorExecution = new InteractorExecution();
    InteractorExecutionComponent interactorExecutionComponent = Orchextra.getInjector().injectBeaconEventsInteractorExecution(interactorExecution);
    interactorExecution.setInteractor(interactorExecutionComponent.provideBeaconEventsInteractor());
    return interactorExecution;
  }

  @ConfigInteractorExecution @Provides InteractorExecution provideConfigInteractorExecution() {
    InteractorExecution interactorExecution = new InteractorExecution();
    InteractorExecutionComponent interactorExecutionComponent = Orchextra.getInjector().injectConfigInteractorInteractorExecution(interactorExecution);
    interactorExecution.setInteractor(interactorExecutionComponent.provideSendConfigInteractor());
    return interactorExecution;
  }

  @GeofenceInteractorExecution @Provides InteractorExecution provideGeofenceInteractorExecution() {
    InteractorExecution interactorExecution = new InteractorExecution();
    InteractorExecutionComponent interactorExecutionComponent = Orchextra.getInjector().injectGeofenceInteractorExecution(interactorExecution);
    interactorExecution.setInteractor(interactorExecutionComponent.provideGeofenceInteractor());
    return interactorExecution;
  }

  @SaveUserInteractorExecution @Provides InteractorExecution provideSaveUserInteractorExecution() {
    InteractorExecution interactorExecution = new InteractorExecution();
    InteractorExecutionComponent interactorExecutionComponent = Orchextra.getInjector().injectSaveUserInteractorExecution(interactorExecution);
    interactorExecution.setInteractor(interactorExecutionComponent.provideSaveUserInteractor());
    return interactorExecution;
  }

  @Provides @Singleton public BlockingQueue<Runnable> provideBlockingQueue() {
    return new InteractorPriorityBlockingQueue(100);
  }

  @Provides @Singleton ThreadFactory provideThreadFactory() {
    return new InteractorOutputThreadFactory();
  }
}
