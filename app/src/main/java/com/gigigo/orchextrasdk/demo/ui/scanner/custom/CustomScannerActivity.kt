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

package com.gigigo.orchextrasdk.demo.ui.scanner.custom

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import android.widget.Toast
import com.gigigo.orchextra.core.domain.entities.TriggerType.BARCODE
import com.gigigo.orchextra.core.domain.entities.TriggerType.QR
import com.gigigo.orchextra.core.receiver.TriggerBroadcastReceiver
import com.gigigo.orchextrasdk.demo.R
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class CustomScannerActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {

  private val PERMISSIONS_REQUEST_CAMERA = 1
  private val handler = Handler()
  private lateinit var scannerView: ZBarScannerView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_scanner)

    scannerView = ZBarScannerView(this)
    val contentFrame = findViewById<ViewGroup>(R.id.content_frame)
    contentFrame.addView(scannerView)

    initToolbar()
  }

  private fun initToolbar() {

    val toolbar = findViewById<Toolbar>(R.id.toolbar)

    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)

    toolbar.setNavigationOnClickListener { onBackPressed() }

    title = getString(R.string.custom_scanner_title)
  }

  override fun handleResult(rawResult: Result) {

    if (rawResult.barcodeFormat.name == "QRCODE") {
      sendBroadcast(
          TriggerBroadcastReceiver.getTriggerIntent(this, QR withValue rawResult.contents))
      finish()
    } else {
      sendBroadcast(
          TriggerBroadcastReceiver.getTriggerIntent(this, BARCODE withValue rawResult.contents))
      finish()
    }

    handler.postDelayed({ scannerView.resumeCameraPreview(this@CustomScannerActivity) }, 2000)
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

  fun requestPermission() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED) {

      if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
        // Show an expanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.
        Toast.makeText(this, "Explanation!!!", Toast.LENGTH_SHORT).show()
      } else {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
            PERMISSIONS_REQUEST_CAMERA)
      }
    }
  }

  public override fun onPause() {
    super.onPause()
    scannerView.stopCamera()
  }

  public override fun onDestroy() {
    super.onDestroy()
    scannerActivity = null
  }

  override fun onRequestPermissionsResult(requestCode: Int,
      permissions: Array<String>, grantResults: IntArray) {
    when (requestCode) {
      PERMISSIONS_REQUEST_CAMERA -> {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          scannerView.startCamera()
        } else {
          CustomScannerActivity.finish()
        }
      }
    }
  }

  companion object Navigator {
    private var scannerActivity: CustomScannerActivity? = null

    fun open(context: Context) {
      val intent = Intent(context, CustomScannerActivity::class.java)
      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      context.startActivity(intent)
    }

    fun finish() {
      scannerActivity?.finish()
    }
  }
}
