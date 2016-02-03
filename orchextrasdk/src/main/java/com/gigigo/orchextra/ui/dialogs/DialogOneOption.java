package com.gigigo.orchextra.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.gigigo.orchextra.R;

public class DialogOneOption {

    private final Context context;
    private final String title;
    private final String message;
    private final String okText;
    private final DialogInterface.OnClickListener positiveListener;

    public DialogOneOption(Context context, String title, String message, String positiveText, DialogInterface.OnClickListener positiveListener) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.okText = positiveText;
        this.positiveListener = positiveListener;
    }

    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog = builder.setIcon(R.mipmap.ic_launcher)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(okText, positiveListener)
                .create();

        return dialog;
    }
}
