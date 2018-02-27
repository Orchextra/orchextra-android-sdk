package com.gigigo.orchextra.device.notifications

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7ox.app.AppCompatActivity
import android.util.Log
import com.gigigo.orchextra.R
import com.gigigo.orchextra.device.notifications.DialogType.ONE_BUTTON
import com.gigigo.orchextra.device.notifications.DialogType.TWO_BUTTON
import com.gigigo.orchextra.domain.model.actions.strategy.OrchextraNotification
import com.gigigo.orchextra.ui.dialogs.CustomDialog
import com.gigigo.orchextra.ui.dialogs.DialogOneOption
import com.gigigo.orchextra.ui.dialogs.DialogTwoOptions


enum class DialogType {
  ONE_BUTTON,
  TWO_BUTTON;
}

class NotificationActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.ox_activity_notification)


    title = ""

    val notification = getNotification()
    val dialogType = getDialogType()

    var dialog: CustomDialog

    when (dialogType) {
      ONE_BUTTON -> {
        dialog = DialogOneOption(this,
            notification.title, notification.body,
            getString(R.string.ox_accept_text),
            { dialog, which ->
              finishWithAcceptation()
            }
        )
      }
      TWO_BUTTON -> {
        dialog = DialogTwoOptions(this,
            notification.title, notification.body,
            getString(R.string.ox_accept_text),
            { dialog, which ->
              finishWithAcceptation()
            },
            getString(R.string.ox_cancel_text),
            { dialog, which ->
              finishWithCancelation()
            }
        )
      }
    }

    dialog.onCreateDialog().show();


    /*
    val dialog = builder.setTitle("notification.title")
        .setMessage("notification.body")
        .setIcon(R.drawable.ox_notification_large_icon)
        .setPositiveButton(android.R.string.ok, { dialog, _ ->
          dialog.dismiss()

          /*
          val notificationActivityName = dbDataSource.getNotificationActivityName()
          if (notificationActivityName.isNotEmpty()) {
            openCustomNotificationActivity(notificationActivityName)
          }

          actionHandlerServiceExecutor.execute(action.copy(notification = Notification()))
          */
        })
        .setNegativeButton(android.R.string.cancel, { dialog, _ -> dialog.dismiss() })
        .show()

    dialog.setOnDismissListener {
      finish()
    }
    */
  }

  private fun finishWithAcceptation() {
    finish()
    onAccept()
  }

  private fun finishWithCancelation() {
    finish()
    onCancelled()
  }


  private fun openCustomNotificationActivity(activityToStart: String) = try {
    val cls = Class.forName(activityToStart)
    val intent = Intent(this, cls)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
  } catch (exception: ClassNotFoundException) {
    Log.e(TAG, "openCustomNotificationActivity($activityToStart)", exception)
  }

  private fun getNotification(): OrchextraNotification = intent.extras.getSerializable(NOTIFICATION_EXTRA) as OrchextraNotification

  private fun getDialogType(): DialogType = intent.extras.getSerializable (DIALOG_TYPE) as DialogType

  companion object Navigator {

    private val TAG = "NotificationActivity.kt"
    private val NOTIFICATION_EXTRA = "notification_extra"
    private val ACTION_EXTRA = "action_extra"
    private val DIALOG_TYPE = "dialog_type"

    var onAccept: () -> Unit = {}
    var onCancelled: () -> Unit = {}

    @JvmOverloads
    fun open(context: Context, notification: OrchextraNotification, dialogType: DialogType,
        onAccept: () -> Unit = {},
        onCancelled: () -> Unit = {}) {
      this.onAccept = onAccept
      this.onCancelled = onCancelled

      val intent = getIntent(context, notification, dialogType)
      context.startActivity(intent)
    }

    fun getIntent(context: Context, notification: OrchextraNotification,
        dialogType: DialogType): Intent {
      val intent = Intent(context, NotificationActivity::class.java)
      val bundle = Bundle()
      bundle.putSerializable(DIALOG_TYPE, dialogType)
      bundle.putSerializable(NOTIFICATION_EXTRA, notification)
      intent.putExtras(bundle)
      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

      return intent
    }
  }
}

