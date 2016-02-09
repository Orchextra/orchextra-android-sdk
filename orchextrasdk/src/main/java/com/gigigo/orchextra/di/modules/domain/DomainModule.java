package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.BuildConfig;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
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
@Module(includes = InteractorsModule.class)
public class DomainModule {

  @Provides @Singleton InteractorInvoker provideInteractorInvoker(ExecutorService executor,
      LogExceptionHandler logExceptionHandler) {
    return new InteractorInvokerImp(executor, logExceptionHandler);
  }

  @Provides @Singleton Session provideSession() {
    return new Session();
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

  @Provides @Singleton public BlockingQueue<Runnable> provideBlockingQueue() {
    return new InteractorPriorityBlockingQueue(100);
  }

  @Provides @Singleton ThreadFactory provideThreadFactory() {
    return new InteractorOutputThreadFactory();
  }
}
