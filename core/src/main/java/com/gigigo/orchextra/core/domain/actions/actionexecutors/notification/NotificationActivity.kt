package com.gigigo.orchextra.core.domain.actions.actionexecutors.notification

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gigigo.orchextra.core.R
import com.gigigo.orchextra.core.domain.actions.ActionHandlerServiceExecutor
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.Notification

class NotificationActivity : AppCompatActivity() {

  val actionHandlerServiceExecutor = ActionHandlerServiceExecutor.create()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notification)

    val notification = getNotification()
    val action = getAction()

    val builder = AlertDialog.Builder(this)
    builder.setTitle(notification.title)
        .setMessage(notification.body)
        .setIcon(R.drawable.ox_notification_large_icon)
        .setPositiveButton(android.R.string.ok, { dialog, _ ->
          dialog.dismiss()
          actionHandlerServiceExecutor.execute(this@NotificationActivity,
              action.copy(notification = Notification()))
          this@NotificationActivity.finish()
        })
        .show()
  }

  private fun getNotification(): Notification = intent.getParcelableExtra(NOTIFICATION_EXTRA)

  private fun getAction(): Action = intent.getParcelableExtra(ACTION_EXTRA)

  companion object Navigator {

    private val NOTIFICATION_EXTRA = "notification_extra"
    private val ACTION_EXTRA = "action_extra"

    fun open(context: Context, notification: Notification, action: Action) {
      val intent = getIntent(context, notification, action)
      context.startActivity(intent)
    }

    fun getIntent(context: Context, notification: Notification, action: Action): Intent {
      val intent = Intent(context, NotificationActivity::class.java)
      intent.putExtra(NOTIFICATION_EXTRA, notification)
      intent.putExtra(ACTION_EXTRA, action)
      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

      return intent
    }
  }
}
