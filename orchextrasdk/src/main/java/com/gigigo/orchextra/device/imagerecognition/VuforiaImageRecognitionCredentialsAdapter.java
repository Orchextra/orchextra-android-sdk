package com.gigigo.orchextra.device.imagerecognition;

import com.gigigo.imagerecognitioninterface.ImageRecognitionCredentials;
import com.gigigo.orchextra.domain.model.entities.Vuforia;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/5/16.
 */
public class VuforiaImageRecognitionCredentialsAdapter implements ImageRecognitionCredentials{

  private final Vuforia vuforia;

  public VuforiaImageRecognitionCredentialsAdapter(Vuforia vuforia) {
    this.vuforia = vuforia;
  }

  @Override public String getClientAccessKey() {
    return vuforia.getClientAccessKey();
  }

  @Override public String getLicensekey() {
    return vuforia.getLicenseKey();
  }

  @Override public String getClientSecretKey() {
    return vuforia.getClientSecretKey();
  }
}
