package com.gigigo.orchextra.device.imagerecognition;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.imagerecognitioninterface.ImageRecognition;
import com.gigigo.orchextra.control.controllers.imagerecognition.ImageRecognitionController;
import com.gigigo.orchextra.control.controllers.imagerecognition.OnImageRecognitionCredentialsReadyListener;
import com.gigigo.orchextra.domain.model.entities.Vuforia;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/5/16.
 */
public class ImageRecognitionManagerImpl implements ImageRecognitionManager,
    OnImageRecognitionCredentialsReadyListener {

  private final ImageRecognitionController imageRecognitionController;
  private ImageRecognition imageRecognitionImplementation;

  public ImageRecognitionManagerImpl(ImageRecognitionController imageRecognitionController) {
    this.imageRecognitionController = imageRecognitionController;
    this.imageRecognitionImplementation = new ImageRecognitionNullImpl();
  }

  @Override public void setImplementation(ImageRecognition imageRecognition) {
    this.imageRecognitionImplementation = imageRecognition;
  }

  @Override public void startImageRecognition() {
    imageRecognitionController.getCredentials(this);
  }

  @Override public void onCredentialsReady(Vuforia vuforia) {
    VuforiaImageRecognitionCredentialsAdapter adapter = new VuforiaImageRecognitionCredentialsAdapter(vuforia);
    imageRecognitionImplementation.startImageRecognition(adapter);
  }

  @Override public void onCredentialsError(String message) {
    GGGLogImpl.log(message, LogLevel.ERROR);
  }

  @Override public void recognizedPattern(String patternId) {
    imageRecognitionController.recognizedPattern(patternId);
  }
}
