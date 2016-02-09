package com.gigigo.orchextra.device.actions;

import android.content.Context;

import com.gigigo.orchextra.domain.abstractions.actions.ActionExecutor;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.ui.WebViewActivity;

public class WebViewActionExecutor implements ActionExecutor {

    private final Context context;

    public WebViewActionExecutor(Context context) {
        this.context = context;
    }

    @Override
    public void execute(BasicAction action) {
        WebViewActivity.open(context, action.getUrl());
    }
}
