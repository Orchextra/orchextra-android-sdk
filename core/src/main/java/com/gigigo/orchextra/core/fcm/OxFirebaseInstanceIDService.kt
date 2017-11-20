package com.gigigo.orchextra.core.fcm

import com.gigigo.orchextra.core.data.datasources.network.models.toOxDevice
import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.utils.LogUtils
import com.gigigo.orchextra.core.utils.extensions.getBaseApiOxDevice
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class OxFirebaseInstanceIDService : FirebaseInstanceIdService() {

  override fun onTokenRefresh() {

    val dbDataSource = DbDataSource.create(this)

    val newDevice = applicationContext.getBaseApiOxDevice().toOxDevice()
    dbDataSource.saveDevice(newDevice)

    val refreshedToken = FirebaseInstanceId.getInstance().token
    LogUtils.LOGD("FirebaseInstanceID", "Refreshed token: " + refreshedToken)
  }
}