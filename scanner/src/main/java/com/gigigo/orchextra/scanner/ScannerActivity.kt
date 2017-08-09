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

package com.gigigo.orchextra.scanner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.gigigo.orchextra.core.domain.entities.TriggerType.BARCODE
import com.gigigo.orchextra.core.domain.entities.TriggerType.QR
import com.gigigo.orchextra.core.domain.triggers.TriggerListener
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class ScannerActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {

  private val handler = Handler()
  private lateinit var mScannerView: ZBarScannerView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_scanner)

    scannerActivity = this

    mScannerView = ZBarScannerView(this)
    val contentFrame = findViewById(R.id.content_frame) as ViewGroup
    contentFrame.addView(mScannerView)
  }

  override fun handleResult(rawResult: Result) {

    if (rawResult.barcodeFormat.name == "QRCODE") {
      triggerListener?.onTriggerDetected(QR withValue rawResult.contents)
    } else {
      triggerListener?.onTriggerDetected(BARCODE withValue rawResult.contents)
    }

    handler.postDelayed({ mScannerView.resumeCameraPreview(this@ScannerActivity) }, 2000)
  }

  public override fun onResume() {
    super.onResume()
    mScannerView.setResultHandler(this)
    mScannerView.startCamera()
  }

  public override fun onPause() {
    super.onPause()
    mScannerView.stopCamera()
  }

  public override fun onDestroy() {
    super.onDestroy()
    triggerListener = null
    scannerActivity = null
  }

  companion object Navigator {

    var triggerListener: TriggerListener? = null
    private var scannerActivity: ScannerActivity? = null

    fun open(context: Context) {
      val intent = Intent(context, ScannerActivity::class.java)
      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      context.startActivity(intent)
    }

    fun finish() {
      scannerActivity?.finish()
    }
  }
}
