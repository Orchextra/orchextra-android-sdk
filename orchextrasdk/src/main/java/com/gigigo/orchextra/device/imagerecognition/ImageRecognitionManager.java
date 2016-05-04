package com.gigigo.orchextra.device.imagerecognition;

import com.gigigo.imagerecognitioninterface.ImageRecognition;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/5/16.
 */
public interface ImageRecognitionManager {

  void setImplementation(ImageRecognition imageRecognition);

  void startImageRecognition();

  void recognizedPattern(String stringExtra);
}
