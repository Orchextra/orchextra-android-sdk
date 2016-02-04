package com.gigigo.orchextra.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.gigigo.orchextra.R;
import com.gigigo.orchextra.domain.interactors.actions.ActionExecution;

public class WebViewActivity extends AppCompatActivity {

    private static final String EXTRA_URL = "EXTRA_URL";

    OrchextraWebView orchextraWebView;

    
    ActionExecution actionExecution;

    public static void open(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(EXTRA_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orch_activity_webview_layout);

        initViews();
        initWebView();
    }

    private void initViews() {
        orchextraWebView = (OrchextraWebView) findViewById(R.id.orchextraWebView);
    }

    private void initWebView() {
        orchextraWebView.setOnActionListener(onActionListener);

        String url = getIntent().getStringExtra(EXTRA_URL);

        if (!TextUtils.isEmpty(url)) {
            orchextraWebView.loadUrl(url);
        }
    }

    private OrchextraWebView.OnActionListener onActionListener =
            new OrchextraWebView.OnActionListener() {
                @Override
                public void openScanner() {
                    //TODO Open Scanner
                }

                @Override
                public void openImageRecognition() {
                    //TODO Open Image Recognition
                }
            };
}
