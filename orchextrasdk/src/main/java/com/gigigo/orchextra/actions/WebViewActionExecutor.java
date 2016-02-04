package com.gigigo.orchextra.actions;

import android.content.Context;

import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
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
