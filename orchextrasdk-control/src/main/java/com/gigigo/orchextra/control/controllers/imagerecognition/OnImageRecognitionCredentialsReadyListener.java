package com.gigigo.orchextra.control.controllers.imagerecognition;

import com.gigigo.orchextra.domain.model.entities.Vuforia;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/5/16.
 */
public interface OnImageRecognitionCredentialsReadyListener {
  void onCredentialsReady(Vuforia vuforia);

  void onCredentialsError(String message);
}
