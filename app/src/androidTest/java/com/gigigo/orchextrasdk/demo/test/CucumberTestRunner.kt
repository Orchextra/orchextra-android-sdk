package com.gigigo.orchextrasdk.demo.test

import android.os.Bundle
import android.support.test.runner.MonitoringInstrumentation
import cucumber.api.android.CucumberInstrumentationCore

class CucumberTestRunner : MonitoringInstrumentation() {

  private val instrumentationCore = CucumberInstrumentationCore(this)

  override fun onCreate(arguments: Bundle) {
    super.onCreate(arguments)

    instrumentationCore.create(arguments)
    start()
  }

  override fun onStart() {
    super.onStart()

    waitForIdleSync()
    instrumentationCore.start()
  }
}
