package com.gigigo.orchextra.android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.gigigo.orchextra.R;

public class DialogTwoOptions {

    private final String title;
    private final String message;
    private final String okText;
    private final String cancelText;
    private final DialogInterface.OnClickListener positiveListener;
    private final DialogInterface.OnClickListener negativeListener;

    private final Context context;

    public DialogTwoOptions(Context context, String title, String message, String positiveText, DialogInterface.OnClickListener positiveListener,
                            String negativeText, DialogInterface.OnClickListener negativeListener) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.okText = positiveText;
        this.cancelText = negativeText;
        this.positiveListener = positiveListener;
        this.negativeListener = negativeListener;
    }

    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog = builder.setIcon(R.mipmap.ic_launcher)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(okText, positiveListener)
                .setNegativeButton(cancelText, negativeListener)
                .create();

        return dialog;
    }
}
