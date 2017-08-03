package gigigo.com.orchextrasdk;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class WebViewActivity extends AppCompatActivity {
  //private OxWebView mWebView;
  FrameLayout frmContent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_view);
    //webview with customscheme for renavigate
    if (Build.VERSION.SDK_INT > 17) {
      //mWebView = new OxWebView(this);
      //mWebView.loadUrl(App.GIGIGO_URL);
      //mWebView.setOnKeyListener(new View.OnKeyListener() {
      //    @Override
      //    public boolean onKey(View v, int keyCode, KeyEvent event) {
      //        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.getWebView().canGoBack()) {
      //            mWebView.getWebView().goBack();
      //            return true;
      //        }
      //        return false;
      //    }
      //});
      //
      //frmContent = (FrameLayout) findViewById(R.id.frmcontent);
      //frmContent.addView(mWebView);
      //
      //Orchextra.setCustomSchemeReceiver(new CustomSchemeReceiver() {
      //    @Override
      //    public void onReceive(String scheme) {
      //        if (scheme.contains(App.CUSTOM_SCHEME)) {
      //            String url = scheme.replace(App.CUSTOM_SCHEME, "");
      //            mWebView.loadUrl(url);
      //        }
      //    }
      //});
    }
  }
}
