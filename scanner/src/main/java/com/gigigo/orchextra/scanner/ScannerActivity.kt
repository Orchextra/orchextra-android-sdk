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

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import com.gigigo.orchextra.core.domain.actions.ActionHandlerServiceExecutor
import com.gigigo.orchextra.core.domain.actions.actionexecutors.scanner.ScannerType
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.ActionType.SCAN_CODE
import com.gigigo.orchextra.core.domain.entities.TriggerType.BARCODE
import com.gigigo.orchextra.core.domain.entities.TriggerType.QR
import com.gigigo.orchextra.core.receiver.TriggerBroadcastReceiver
import com.gigigo.orchextra.core.utils.PermissionsActivity
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class ScannerActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {

  private val handler = Handler()
  private lateinit var scannerView: ZBarScannerView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.ox_activity_scanner)

    scannerView = ZBarScannerView(this)
    val contentFrame = findViewById<ViewGroup>(R.id.content_frame)
    contentFrame.addView(scannerView)

    initToolbar()
  }

  private fun initToolbar() {

    val toolbar = findViewById<Toolbar>(com.gigigo.orchextra.core.R.id.ox_toolbar)

    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)

    toolbar.setNavigationOnClickListener { onBackPressed() }

    title = getString(R.string.ox_scanner_title)
  }

  override fun handleResult(rawResult: Result) {

    when (getScannerType()) {
      ScannerType.SCANNER_WITHOUT_ACTION -> {
        val actionHandlerServiceExecutor = ActionHandlerServiceExecutor.create(
            this@ScannerActivity)
        actionHandlerServiceExecutor.execute(Action(type = SCAN_CODE, url = rawResult.contents))
      }
      else ->
        if (rawResult.barcodeFormat.name == "QRCODE") {
          sendBroadcast(TriggerBroadcastReceiver.getTriggerIntent(QR withValue rawResult.contents))
        } else {
          sendBroadcast(
              TriggerBroadcastReceiver.getTriggerIntent(BARCODE withValue rawResult.contents))
        }
    }

    finish()
    handler.postDelayed({ scannerView.resumeCameraPreview(this@ScannerActivity) }, 2000)
  }

  public override fun onResume() {
    super.onResume()
    scannerActivity = this
    scannerView.setResultHandler(this)

    if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
      scannerView.startCamera()
    } else {
      requestPermission()
    }
  }

  private fun requestPermission() {
    PermissionsActivity.open(this, Manifest.permission.CAMERA,
        onSuccess = {
          scannerView.startCamera()
        },
        onError = {
          ScannerActivity.finish()
        })
  }

  public override fun onPause() {
    super.onPause()
    scannerView.stopCamera()
  }

  public override fun onDestroy() {
    super.onDestroy()
    scannerActivity = null
  }

  private fun getScannerType(): ScannerType = intent.getSerializableExtra(TYPE_EXTRA) as ScannerType

  companion object Navigator {
    private var scannerActivity: ScannerActivity? = null
    private val TYPE_EXTRA = "type_extra"

    fun open(context: Context, scannerType: ScannerType) {
      val intent = Intent(context, ScannerActivity::class.java)
      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      intent.putExtra(TYPE_EXTRA, scannerType)
      context.startActivity(intent)
    }

    fun finish() {
      scannerActivity?.finish()
    }
  }
}
