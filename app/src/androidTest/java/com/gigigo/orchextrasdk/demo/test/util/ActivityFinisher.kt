package com.gigigo.orchextrasdk.demo.test.util

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.support.test.runner.lifecycle.ActivityLifecycleMonitor
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage

import java.util.ArrayList
import java.util.EnumSet


class ActivityFinisher private constructor() : Runnable {

  private val activityLifecycleMonitor: ActivityLifecycleMonitor = ActivityLifecycleMonitorRegistry.getInstance()

  override fun run() {
    val activities = ArrayList<Activity>()

    for (stage in EnumSet.range(Stage.CREATED, Stage.STOPPED)) {
      activities.addAll(activityLifecycleMonitor.getActivitiesInStage(stage))
    }

    activities.filterNot { it.isFinishing }
        .forEach { it.finish() }
  }

  companion object {

    fun finishOpenActivities() {
      Handler(Looper.getMainLooper()).post(ActivityFinisher())
    }
  }
}