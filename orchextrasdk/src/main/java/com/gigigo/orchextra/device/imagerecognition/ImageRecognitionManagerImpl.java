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
package com.gigigo.orchextra.device.imagerecognition;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.imagerecognitioninterface.ImageRecognition;
import com.gigigo.orchextra.control.controllers.imagerecognition.ImageRecognitionController;
import com.gigigo.orchextra.control.controllers.imagerecognition.OnImageRecognitionCredentialsReadyListener;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.model.entities.Vuforia;

public class ImageRecognitionManagerImpl implements ImageRecognitionManager,
    OnImageRecognitionCredentialsReadyListener {

  private final ImageRecognitionController imageRecognitionController;
  private final ContextProvider contextProvider;
  private final OrchextraLogger orchextraLogger;
  private ImageRecognition imageRecognitionImplementation;

  public ImageRecognitionManagerImpl(ImageRecognitionController imageRecognitionController,
      ContextProvider contextProvider, OrchextraLogger orchextraLogger) {
    this.imageRecognitionController = imageRecognitionController;
    this.contextProvider = contextProvider;
    this.orchextraLogger = orchextraLogger;
    this.imageRecognitionImplementation = new ImageRecognitionNullImpl(orchextraLogger);

  }

  @Override public void setImplementation(ImageRecognition imageRecognition) {
    this.imageRecognitionImplementation = imageRecognition;
    this.imageRecognitionImplementation.setContextProvider(contextProvider);
  }

  @Override public void startImageRecognition() {
    imageRecognitionController.getCredentials(this);
  }

  @Override public void onCredentialsReady(Vuforia vuforia) {
    VuforiaImageRecognitionCredentialsAdapter adapter = new VuforiaImageRecognitionCredentialsAdapter(vuforia);
    imageRecognitionImplementation.startImageRecognition(adapter);
  }

  @Override public void onCredentialsError(String message) {
    orchextraLogger.log(message, OrchextraSDKLogLevel.ERROR);
  }

  @Override public void recognizedPattern(String patternId) {
    imageRecognitionController.recognizedPattern(patternId);
  }
}
