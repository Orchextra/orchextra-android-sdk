package com.gigigo.orchextra.core.domain.actions.actionexecutors.scanner

import android.os.Handler
import android.os.Looper

object ScanCodeActionExecutor {

  private val handler = Handler(Looper.getMainLooper())
  var oxCustomActionListener: (customSchema: String) -> Unit = {}

  fun onCustomSchema(url: String) {
    handler.post { oxCustomActionListener(url) }
  }
}