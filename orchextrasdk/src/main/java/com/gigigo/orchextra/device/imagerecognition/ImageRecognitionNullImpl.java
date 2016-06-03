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

import com.gigigo.imagerecognitioninterface.ImageRecognition;
import com.gigigo.imagerecognitioninterface.ImageRecognitionCredentials;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;

public class ImageRecognitionNullImpl implements ImageRecognition{

  private final OrchextraLogger orchextraLogger;

  public ImageRecognitionNullImpl(OrchextraLogger orchextraLogger) {
    this.orchextraLogger = orchextraLogger;
  }

  @Override public <T> void setContextProvider(T contextProvider) {}

  @Override
  public void startImageRecognition(ImageRecognitionCredentials imageRecognitionCredentials) {
    orchextraLogger.log("Image Recognition Module not initialized", OrchextraSDKLogLevel.WARN);
  }
}
