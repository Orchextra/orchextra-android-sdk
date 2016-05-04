package com.gigigo.orchextra.device.imagerecognition;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.gigigo.imagerecognitioninterface.ImageRecognitionConstants;
import com.gigigo.orchextra.sdk.OrchextraManager;
import orchextra.javax.inject.Inject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 29/4/16.
 */
public class ImageRecognitionReceiver extends BroadcastReceiver {

  @Inject ImageRecognitionManager imageRecognitionManager;

  @Override public void onReceive(Context context, Intent intent) {

    initDependencies();

    if (intent.getExtras().containsKey(ImageRecognitionConstants.VUFORIA_PATTERN_ID)){
      vuforiaPatternRecognized(intent.getStringExtra(ImageRecognitionConstants.VUFORIA_PATTERN_ID));
    }

  }

  private void initDependencies() {
    OrchextraManager.getInjector().injectImageBroadcastComponent(this);
  }


  public void vuforiaPatternRecognized(String stringExtra){
    imageRecognitionManager.recognizedPattern(stringExtra);
  }
}
