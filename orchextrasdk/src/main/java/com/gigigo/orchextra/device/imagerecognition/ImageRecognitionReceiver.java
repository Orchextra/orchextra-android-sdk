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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gigigo.imagerecognitioninterface.ImageRecognitionConstants;
import com.gigigo.orchextra.di.injector.InjectorImpl;
import com.gigigo.orchextra.sdk.OrchextraManager;

import orchextra.javax.inject.Inject;

public class ImageRecognitionReceiver extends BroadcastReceiver {

    @Inject
    ImageRecognitionManager imageRecognitionManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        initDependencies();

        if (OrchextraManager.getInjector() != null) {
            if (intent!=null && intent.getExtras().containsKey(context.getPackageName())
                    && intent.getExtras().containsKey(ImageRecognitionConstants.VUFORIA_PATTERN_ID)) {

                vuforiaPatternRecognized(intent.getStringExtra(ImageRecognitionConstants.VUFORIA_PATTERN_ID));

            }
        }

    }

    private void initDependencies() {
        InjectorImpl injector = OrchextraManager.getInjector();
        if (injector != null) {
            injector.injectImageBroadcastComponent(this);
        }
    }


    public void vuforiaPatternRecognized(String stringExtra) {
        imageRecognitionManager.recognizedPattern(stringExtra);
    }
}
