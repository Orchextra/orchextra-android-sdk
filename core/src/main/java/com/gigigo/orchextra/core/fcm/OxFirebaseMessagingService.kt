package com.gigigo.orchextra.core.fcm

import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.gigigo.orchextra.core.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class OxFirebaseMessagingService : FirebaseMessagingService() {

  override fun onMessageReceived(remoteMessage: RemoteMessage?) {

    Log.d(TAG, "From: " + remoteMessage!!.from)

    if (remoteMessage.data.size > 0) {
      Log.d(TAG, "Message data payload: " + remoteMessage.data)
      handleNow()
    }

    if (remoteMessage.notification != null) {
      Log.d(TAG, "Message Notification Body: " + remoteMessage.notification.body!!)
    }

    sendNotification("hola")
  }

  private fun handleNow() {
    Log.d(TAG, "Short lived task is done.")
  }

  private fun sendNotification(messageBody: String) {
//    val intent = Intent(this, MainActivity::class.java)
//    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//    val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//        PendingIntent.FLAG_ONE_SHOT)

    val channelId = "chanelId"
    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val notificationBuilder = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(R.drawable.ox_close)
        .setContentTitle("FCM Message")
        .setContentText(messageBody)
        .setAutoCancel(true)
        .setSound(defaultSoundUri)
//        .setContentIntent(pendingIntent)

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
  }

  companion object {
    private val TAG = "MyFirebaseMsgService"
  }
}
