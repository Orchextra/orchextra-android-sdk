package com.gigigo.orchextra.device.notifications

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7ox.app.AppCompatActivity
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

    val customDialog: CustomDialog

    when (dialogType) {
      ONE_BUTTON -> {
        customDialog = DialogOneOption(this,
            notification.title, notification.body,
            getString(R.string.ox_accept_text),
            { _, _ ->
              finishWithAcceptation()
            }
        )
      }
      TWO_BUTTON -> {
        customDialog = DialogTwoOptions(this,
            notification.title, notification.body,
            getString(R.string.ox_accept_text),
            { _, _ ->
              finishWithAcceptation()
            },
            getString(R.string.ox_cancel_text),
            { _, _ ->
              finishWithCancelation()
            }
        )
      }
    }

    val dialog = customDialog.onCreateDialog()

    dialog.setOnDismissListener {
      finish()
    }

    dialog.show()
  }

  private fun finishWithAcceptation() {
    finish()
    onAccept()
  }

  private fun finishWithCancelation() {
    finish()
    onCancelled()
  }


  private fun getNotification(): OrchextraNotification = intent.extras.getSerializable(
      NOTIFICATION_EXTRA) as OrchextraNotification

  private fun getDialogType(): DialogType = intent.extras.getSerializable(DIALOG_TYPE) as DialogType

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

