package com.gigigo.orchextra.control.controllers.imagerecognition;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.error.GenericError;
import com.gigigo.orchextra.domain.interactors.scanner.ScannerInteractor;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.ScannerResult;
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import java.util.List;
import me.panavtec.threaddecoratedview.views.ThreadSpec;
import orchextra.javax.inject.Provider;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/5/16.
 */
public class ImageRecognitionController {

  private final InteractorInvoker interactorInvoker;
  private final Provider<InteractorExecution> getImageRecognitionCredentialsExecutionProvider;
  private final Provider<InteractorExecution> scannerInteractorExecutionProvider;
  private final ActionDispatcher actionDispatcher;
  private final ThreadSpec mainThreadSpec;

  public ImageRecognitionController(InteractorInvoker interactorInvoker,
      Provider<InteractorExecution> getImageRecognitionCredentialsExecutionProvider,
      Provider<InteractorExecution> scannerInteractorExecutionProvider,
      ActionDispatcher actionDispatcher, ThreadSpec mainThreadSpec) {

    this.interactorInvoker = interactorInvoker;
    this.getImageRecognitionCredentialsExecutionProvider = getImageRecognitionCredentialsExecutionProvider;
    this.scannerInteractorExecutionProvider = scannerInteractorExecutionProvider;
    this.actionDispatcher = actionDispatcher;
    this.mainThreadSpec = mainThreadSpec;
  }

  public void getCredentials(final OnImageRecognitionCredentialsReadyListener listener) {
    getImageRecognitionCredentialsExecutionProvider.get().result(new InteractorResult<Vuforia>() {
      @Override public void onResult(Vuforia result) {
        if (result != null) {
          listener.onCredentialsReady(result);
        }
      }
    }).error(GenericError.class, new InteractorResult<GenericError>() {
      @Override public void onResult(GenericError result) {
        listener.onCredentialsError(result.getError().getMessage());
      }
    }).execute(interactorInvoker);
  }

  public void recognizedPattern(String patternId) {
    InteractorExecution interactorExecution = scannerInteractorExecutionProvider.get();
    ScannerInteractor scannerInteractor = (ScannerInteractor) interactorExecution.getInteractor();
    scannerInteractor.setScanner(ScannerResult.createImageRecognitionResult(patternId));

    interactorExecution.result(new InteractorResult<List<BasicAction>>() {
      @Override
      public void onResult(List<BasicAction> actions) {
        executeActions(actions);
      }
    }).error(GenericError.class, new InteractorResult<InteractorError>() {
      @Override
      public void onResult(InteractorError result) {
        //TODO Manage Error
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
