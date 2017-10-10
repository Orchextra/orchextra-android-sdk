package com.gigigo.orchextra.imagerecognizer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.gigigo.imagerecognitioninterface.ImageRecognitionConstants

class ImageRecognizerReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {

    if (intent!=null && intent.getExtras().containsKey(context?.getPackageName())
        && intent.getExtras().containsKey(ImageRecognitionConstants.VUFORIA_PATTERN_ID)) {

      vuforiaPatternRecognized(intent?.getStringExtra(ImageRecognitionConstants.VUFORIA_PATTERN_ID))
    }
  }

  fun vuforiaPatternRecognized(code: String) {
    OxImageRecognizerImp.recognizedPattern(code)
  }
}