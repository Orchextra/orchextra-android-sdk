package com.gigigo.orchextra.device.imagerecognition;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.imagerecognitioninterface.ImageRecognition;
import com.gigigo.imagerecognitioninterface.ImageRecognitionCredentials;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/5/16.
 */
public class ImageRecognitionNullImpl implements ImageRecognition{

  @Override
  public void startImageRecognition(ImageRecognitionCredentials imageRecognitionCredentials) {
    GGGLogImpl.log("Image Recognition Module not initialized", LogLevel.WARN);
  }
}
