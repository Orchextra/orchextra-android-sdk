package com.gigigo.orchextra.core.domain.actions.actionexecutors.notification

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gigigo.orchextra.core.R
import com.gigigo.orchextra.core.domain.entities.Notification

class NotificationActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notification)

    val notification = getNotification()

    val builder = AlertDialog.Builder(this)
    builder.setTitle(notification.title)
        .setMessage(notification.body)
        .setIcon(R.drawable.ox_notification_large_icon)
        .setPositiveButton(android.R.string.ok, { dialog, _ ->
          dialog.dismiss()
          this@NotificationActivity.finish()
        })
        .show()
  }

  private fun getNotification(): Notification = intent.getParcelableExtra(NOTIFICACTION_EXTRA)

  companion object Navigator {

    private val NOTIFICACTION_EXTRA = "notification_extra"

    fun open(context: Context, notification: Notification) {
      val intent = Intent(context, NotificationActivity::class.java)
      intent.putExtra(NOTIFICACTION_EXTRA, notification)
      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      context.startActivity(intent)
    }
  }
}
