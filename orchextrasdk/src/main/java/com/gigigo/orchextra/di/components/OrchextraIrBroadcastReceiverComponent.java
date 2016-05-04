package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.device.imagerecognition.ImageRecognitionReceiver;
import com.gigigo.orchextra.di.scopes.PerService;
import orchextra.dagger.Component;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/5/16.
 */
@PerService @Component(dependencies = OrchextraComponent.class)
public interface OrchextraIrBroadcastReceiverComponent {
  void injectOrchextraIrBroadcastReceiver(ImageRecognitionReceiver imageRecognitionReceiver);
}
