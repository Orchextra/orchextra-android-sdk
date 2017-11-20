package com.gigigo.orchextra.core.fcm

import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.gigigo.orchextra.core.R
import com.gigigo.orchextra.core.data.datasources.network.models.ApiAction
import com.gigigo.orchextra.core.data.datasources.network.models.toAction
import com.gigigo.orchextra.core.domain.actions.ActionHandlerServiceExecutor
import com.gigigo.orchextra.core.domain.entities.Action
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.moshi.Moshi

class OxFirebaseMessagingService : FirebaseMessagingService() {

  private val moshi = Moshi.Builder().build()
  private val apiActionAdapter = moshi.adapter(ApiAction::class.java)
  private val actionHandlerServiceExecutor = ActionHandlerServiceExecutor.create()

  override fun onMessageReceived(remoteMessage: RemoteMessage?) {

    Log.d(TAG, "From: " + remoteMessage!!.from)

    if (remoteMessage.data.isNotEmpty()) {
      Log.d(TAG, "Message data payload: " + remoteMessage.data)


      if (remoteMessage.data.containsKey("action")) {
        val action = apiActionAdapter.fromJson(remoteMessage.data["action"]).toAction()
        handleAction(action)
      }
    }

    if (remoteMessage.notification != null) {
      Log.d(TAG, "Message Notification Body: " + remoteMessage.notification.body!!)
      Log.d(TAG, "Message Notification Title: " + remoteMessage.notification.title!!)

      sendNotification(
          title = remoteMessage.notification.title ?: "Orchextra",
          body = remoteMessage.notification.body ?: "")
    }
  }

  private fun handleAction(action: Action) {
    actionHandlerServiceExecutor.execute(baseContext, action)
  }

  private fun sendNotification(title: String, body: String) {
//    val intent = Intent(this, MainActivity::class.java)
//    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//    val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//        PendingIntent.FLAG_ONE_SHOT)

    val channelId = "chanelId"
    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val notificationBuilder = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(R.drawable.ox_close)
        .setContentTitle(title)
        .setContentText(body)
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
