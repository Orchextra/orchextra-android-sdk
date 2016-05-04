package com.gigigo.orchextra.domain.interactors.imagerecognition;

import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import com.gigigo.orchextra.domain.services.imagerecognition.GetImageRecognitionCredentialsService;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/5/16.
 */
public class GetImageRecognitionCredentialsInteractor implements Interactor<InteractorResponse<Vuforia>>{
  private final GetImageRecognitionCredentialsService credentialsService;

  public GetImageRecognitionCredentialsInteractor(GetImageRecognitionCredentialsService credentialsService) {
    this.credentialsService = credentialsService;
  }

  @Override public InteractorResponse<Vuforia> call() throws Exception {
    return credentialsService.obtainImageRecognitionCredentials();
  }

}
