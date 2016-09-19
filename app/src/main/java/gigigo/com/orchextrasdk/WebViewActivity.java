package gigigo.com.orchextrasdk;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.gigigo.orchextra.CustomSchemeReceiver;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.ui.webview.OxWebView;

public class WebViewActivity extends AppCompatActivity {
    private OxWebView mWebView;

    //https://web.destapp.com/orchextra
    private static final String GIGIGO_RESEARCH_URL = "https://web.destapp.com/orchextra"; // "http://research.gigigo.com";
    private static final String CUSTOM_SCHEME = "webview://";
    FrameLayout frmContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_web_view);

        if (Build.VERSION.SDK_INT > 17) {
            mWebView = new OxWebView(this);
            mWebView.loadUrl(GIGIGO_RESEARCH_URL);
            mWebView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.getWebView().canGoBack()) {
                        mWebView.getWebView().goBack();
                        return true;
                    }
                    return false;
                }
            });

            frmContent = (FrameLayout) findViewById(R.id.frmcontent);
            frmContent.addView(mWebView);

            Orchextra.setCustomSchemeReceiver(new CustomSchemeReceiver() {
                @Override
                public void onReceive(String scheme) {
                    if (scheme.contains(CUSTOM_SCHEME)) {
                        String url = scheme.replace(CUSTOM_SCHEME, "");
                        mWebView.loadUrl(url);
                    }
                }
            });
        }
    }

}
