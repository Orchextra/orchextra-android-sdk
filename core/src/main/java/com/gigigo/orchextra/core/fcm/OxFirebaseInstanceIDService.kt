package com.gigigo.orchextra.core.fcm

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class OxFirebaseInstanceIDService : FirebaseInstanceIdService() {

  override fun onTokenRefresh() {
    val refreshedToken = FirebaseInstanceId.getInstance().token
    Log.d("FirebaseInstanceID", "Refreshed token: " + refreshedToken!!)
  }
}