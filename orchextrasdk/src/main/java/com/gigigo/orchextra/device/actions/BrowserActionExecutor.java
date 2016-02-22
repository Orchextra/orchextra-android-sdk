package com.gigigo.orchextra.device.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.gigigo.orchextra.domain.abstractions.actions.ActionExecutor;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;

public class BrowserActionExecutor implements ActionExecutor {

    private final Context context;

    public BrowserActionExecutor(Context context) {
        this.context = context;
    }

    @Override
    public void execute(BasicAction action) {
        String url = action.getUrl();
        Uri uriUrl = Uri.parse(url);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uriUrl);
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
    }
}
