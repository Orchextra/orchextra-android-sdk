package com.gigigo.orchextra.core.fcm

import android.util.Log
import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.entities.TokenData
import com.google.firebase.iid.FirebaseInstanceIdService

class OxFirebaseInstanceIDService : FirebaseInstanceIdService() {

  override fun onTokenRefresh() {

    val networkDataSource = NetworkDataSource.create(this)
    val dbDataSource = DbDataSource.create(this)
    dbDataSource.clearDevice()

    val crm = dbDataSource.getCrm()
    val device = dbDataSource.getDevice()
    networkDataSource.updateTokenData(TokenData(crm = crm, device = device))

    Log.w(TAG, "Refreshed token")
  }

  companion object {
    private const val TAG = "FirebaseInstanceID"
  }
}