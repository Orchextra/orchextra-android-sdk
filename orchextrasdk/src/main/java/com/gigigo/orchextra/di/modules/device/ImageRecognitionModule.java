package com.gigigo.orchextra.di.modules.device;

import com.gigigo.orchextra.control.controllers.imagerecognition.ImageRecognitionController;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.device.imagerecognition.ImageRecognitionManager;
import com.gigigo.orchextra.device.imagerecognition.ImageRecognitionManagerImpl;
import com.gigigo.orchextra.di.qualifiers.GetIrCredentialsInteractorExecution;
import com.gigigo.orchextra.di.qualifiers.MainThread;
import com.gigigo.orchextra.di.qualifiers.ScannerInteractorExecution;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import me.panavtec.threaddecoratedview.views.ThreadSpec;
import orchextra.dagger.Module;
import orchextra.dagger.Provides;
import orchextra.javax.inject.Provider;
import orchextra.javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/5/16.
 */
@Module
public class ImageRecognitionModule {

  @Singleton
  @Provides ImageRecognitionManager provideImageRecognitionManager(ImageRecognitionController imageRecognitionController) {
    return new ImageRecognitionManagerImpl(imageRecognitionController);
  }

  @Singleton
  @Provides ImageRecognitionController provideImageImageRecognitionController(
      InteractorInvoker interactorInvoker,

      @GetIrCredentialsInteractorExecution Provider<InteractorExecution>
          getImageRecognitionCredentialsInteractorExecutionProvider,

      @ScannerInteractorExecution Provider<InteractorExecution> scannerProvider,
      ActionDispatcher actionDispatcher, @MainThread ThreadSpec mainThreadSpec) {

    return new ImageRecognitionController(interactorInvoker,
        getImageRecognitionCredentialsInteractorExecutionProvider, scannerProvider,
        actionDispatcher, mainThreadSpec);
  }



}
