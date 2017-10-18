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

package com.gigigo.orchextra.core.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.gigigo.orchextra.core.R
import com.gigigo.orchextra.core.domain.entities.Error.Companion.PERMISSION_ERROR
import com.gigigo.orchextra.core.domain.entities.Error.Companion.PERMISSION_RATIONALE_ERROR
import com.gigigo.orchextra.core.domain.exceptions.OxException

class PermissionsActivity : AppCompatActivity() {

  private val PERMISSIONS_REQUEST = 0x132

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notification)
    title = ""

    if (ContextCompat.checkSelfPermission(this@PermissionsActivity,
        getPermission()) == PackageManager.PERMISSION_GRANTED) {
      finishWithPermissionsGranted()
    } else {
      requestPermission()
    }
  }

  private fun requestPermission() {
    if (ContextCompat.checkSelfPermission(this,
        getPermission()) != PackageManager.PERMISSION_GRANTED) {

      if (ActivityCompat.shouldShowRequestPermissionRationale(this, getPermission())) {
        finishWithoutPermissions(
            OxException(PERMISSION_RATIONALE_ERROR, "Should show request permission rationale"))
      } else {
        ActivityCompat.requestPermissions(this, arrayOf(getPermission()),
            PERMISSIONS_REQUEST)
      }
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
      grantResults: IntArray) {

    when (requestCode) {
      PERMISSIONS_REQUEST -> {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          finishWithPermissionsGranted()
        } else {
          finishWithoutPermissions(
              OxException(PERMISSION_ERROR, "Permission is mandatory"))
        }
      }
    }
  }

  private fun finishWithPermissionsGranted() {
    finish()
    onSuccess()
  }

  private fun finishWithoutPermissions(exception: OxException) {
    finish()
    onError(exception)
  }

  private fun getPermission(): String =
      intent.getStringExtra(EXTRA_PERMISSION) ?: Manifest.permission.ACCESS_FINE_LOCATION

  companion object Navigator {

    val EXTRA_PERMISSION = "extra_permission"
    var onSuccess: () -> Unit = {}
    var onError: (OxException) -> Unit = {}

    fun open(context: Context, permission: String, onSuccess: () -> Unit = {},
        onError: (OxException) -> Unit = {}) {

      this.onSuccess = onSuccess
      this.onError = onError

      val intent = Intent(context, PermissionsActivity::class.java)
      intent.putExtra(EXTRA_PERMISSION, permission)
      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      context.startActivity(intent)
    }
  }
}
