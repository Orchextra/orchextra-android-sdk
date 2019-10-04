package com.gigigo.orchextra.core.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import android.util.Log
import com.gigigo.orchextra.core.R
import com.gigigo.orchextra.core.data.datasources.network.models.ApiAction
import com.gigigo.orchextra.core.data.datasources.network.models.toAction
import com.gigigo.orchextra.core.domain.actions.ActionHandlerServiceExecutor
import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.TokenData
import com.gigigo.orchextra.core.utils.LogUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.moshi.Moshi


class OxFirebaseMessagingService : FirebaseMessagingService() {

    private val moshi = Moshi.Builder().build()
    private val apiActionAdapter = moshi.adapter(ApiAction::class.java)
    private lateinit var actionHandlerServiceExecutor: ActionHandlerServiceExecutor
    private lateinit var dbDataSource: DbDataSource

    override fun onCreate() {
        super.onCreate()
        actionHandlerServiceExecutor = ActionHandlerServiceExecutor.create(this)
        dbDataSource = DbDataSource.create(baseContext)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        val networkDataSource = NetworkDataSource.create(this)
        val dbDataSource = DbDataSource.create(this)
        dbDataSource.clearDevice()

        val crm = dbDataSource.getCrm()
        val device = dbDataSource.getDevice()
        networkDataSource.updateTokenData(TokenData(crm = crm, device = device))

        Log.w(TAG, "Refreshed token")
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d(TAG, "From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)

            if (remoteMessage.data.containsKey("isOrchextra")
                && remoteMessage.data["isOrchextra"] != "true"
            ) {
                LogUtils.LOGD(TAG, "isOrchextra == false")
                return
            }

            if (remoteMessage.data.containsKey("action")) {
                remoteMessage.data["action"]?.let {
                    apiActionAdapter.fromJson(it)?.toAction()?.let { action ->
                        handleAction(action)
                    }
                }
            }
        }

        remoteMessage.notification?.let { notification ->
            Log.d(TAG, "Message Notification Body: ${notification.body}")
            Log.d(TAG, "Message Notification Title: ${notification.title}")

            sendNotification(
                title = notification.title ?: "Orchextra",
                body = notification.body ?: ""
            )
        }
    }

    private fun handleAction(action: Action) {
        actionHandlerServiceExecutor.execute(action)
    }

    private fun sendNotification(title: String, body: String) {

        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            createNotificationChannel()
        }

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ox_close)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)

        val notificationActivityName = dbDataSource.getNotificationActivityName()
        if (notificationActivityName.isNotEmpty()) {
            if (getNotificationActivityClass(notificationActivityName) != null) {
                val intent = Intent(this, getNotificationActivityClass(notificationActivityName))
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                val pendingIntent = PendingIntent.getActivity(
                    this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT
                )

                notificationBuilder.setContentIntent(pendingIntent)
            }
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0x9387, notificationBuilder.build())
    }

    @RequiresApi(VERSION_CODES.O)
    private fun createNotificationChannel() {

        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val name = getString(R.string.app_name)
        val description = getString(R.string.app_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)

        mChannel.description = description
        mChannel.enableLights(true)

        mChannel.lightColor = Color.RED
        mChannel.enableVibration(true)
        mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        mNotificationManager.createNotificationChannel(mChannel)
    }

    private fun getNotificationActivityClass(activityName: String): Class<*>? = try {
        Class.forName(activityName)
    } catch (exception: ClassNotFoundException) {
        null
    }

    companion object {
        private const val TAG = "OxFirebaseMsgService"
        private const val CHANNEL_ID = "ox_push_notification"
    }
}
