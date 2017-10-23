/*
 * Created by Orchextra
 *
 * Copyright (C) 2017 Gigigo Mobile Services SL
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

package com.gigigo.orchextra.imagerecognizer

import android.app.Activity
import android.content.Context
import com.gigigo.imagerecognition.Credentials
import com.gigigo.imagerecognition.vuforia.ContextProvider
import com.gigigo.imagerecognition.vuforia.ImageRecognitionVuforia
import com.gigigo.orchextra.core.domain.entities.ImageRecognizerCredentials
import com.gigigo.orchextra.core.domain.entities.TriggerType.IMAGE_RECOGNITION
import com.gigigo.orchextra.core.domain.triggers.OxTrigger
import com.gigigo.orchextra.core.receiver.TriggerBroadcastReceiver

class OxImageRecognizerImp private constructor(
    private val activity: Activity) : OxTrigger<ImageRecognizerCredentials> {

  private lateinit var credentials: ImageRecognizerCredentials

  override fun init() {
    startVuforia(licenseKey = credentials.licenseKey,
        accessKey = credentials.clientAccessKey,
        secretKey = credentials.clientSecretKey)
  }

  override fun setConfig(config: ImageRecognizerCredentials) {
    this.credentials = config
  }

  override fun finish() {
  }

  private fun startVuforia(licenseKey: String, accessKey: String, secretKey: String) {

    val imageRecognition = ImageRecognitionVuforia()

    ImageRecognitionVuforia.onRecognizedPattern {
      showResponseCode(it)
    }

    imageRecognition.setContextProvider(object : ContextProvider {
      override fun getApplicationContext(): Context = activity.applicationContext

      override fun getCurrentActivity(): Activity = activity

      override fun isApplicationContextAvailable(): Boolean = true

      override fun isActivityContextAvailable(): Boolean = true
    })

    imageRecognition.startImageRecognition(object : Credentials {
      override fun getLicensekey(): String = licenseKey

      override fun getClientSecretKey(): String = secretKey

      override fun getClientAccessKey(): String = accessKey
    })
  }

  private fun showResponseCode(code: String) {
    activity.sendBroadcast(
        TriggerBroadcastReceiver.getTriggerIntent(IMAGE_RECOGNITION withValue code))
  }

  companion object Factory {
    fun create(activity: Activity): OxImageRecognizerImp = OxImageRecognizerImp(activity)
  }
}