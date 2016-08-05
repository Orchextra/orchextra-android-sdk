package gigigo.com.orchextrasdk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appoxee.Appoxee;
import com.appoxee.AppoxeeObserver;
import com.appoxee.asyncs.initAsync;
import com.gigigo.orchextra.CustomSchemeReceiver;
//import com.gigigo.orchextra.CrmUser;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.ui.webview.OxWebViewActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("APP", "Hello MainActivity, start onCreate");
        //orchextraSDK
        getViews();
        setListeners();

        startOrchextra();

        //appoxeeSDK
        startAppoxee();
    }

    //region Appoxee
    private final static String APPOXEE_SDK_KEY = "575e7877b16df0.22390647";
    private final static String APPOXEE_SDK_SECRET = "575e7877b16f48.87387869";
    private static final String TAG = "MainActivity";
    private static final String SOME_UNIQUE_USER_IDENTIFIER = "example";
    private static final String SOME_CUSTOM_FIELD = "custom_field";
    ExecutorService threadPool = Executors.newSingleThreadExecutor();

    private void startAppoxee() {
        new initAsync(this,
                APPOXEE_SDK_KEY,
                APPOXEE_SDK_SECRET,
                MainActivity.class.getName(), true, appoxeeCallbacksObserver)
                .execute();

    }

    //we'll wait for appoxee to complete registration before using the API
    private AppoxeeObserver appoxeeCallbacksObserver = new AppoxeeObserver() {
        @Override
        public void onRegistrationCompleted() {
            Log.d(TAG, "appoxee is ready");

            //using a background thread, because API method invoke network operations directly
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    //setting device alias
                    boolean success = Appoxee.setDeviceAlias(SOME_UNIQUE_USER_IDENTIFIER);
                    Log.d(TAG, "alias had been set: " + success);

                    //reading device alias value
                    String alias = Appoxee.getDeviceAlias();
                    Log.d(TAG, "alias: " + alias);
                }
            });

            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    //setting a custom fiels
                    boolean success = Appoxee.setCustomField(SOME_CUSTOM_FIELD, "ok");
                    Log.d(TAG, "custom field set success: " + success);

                    ArrayList<String> fieldArray = new ArrayList<String>();
                    fieldArray.add(SOME_CUSTOM_FIELD);
                    //reading custom field value
                    List<String> fieldValue = Appoxee.getCustomFieldsValues(fieldArray);
                    if (fieldValue != null && fieldValue.size() > 0) {
                        Log.d(TAG, "field value: " + fieldValue.get(0));
                    } else {
                        Log.w(TAG, "couldn't get field value");
                    }

                }
            });

            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String[] tagArray = {"example_tag"};
                    boolean success = Appoxee.addTagsToDevice(new ArrayList<String>(Arrays.asList(tagArray)));
                    Log.d(TAG, "custom field set success: " + success);

                    List<String> tags = Appoxee.getTagList();
                    Log.d(TAG, "tags list: " + tags);
                }
            });
        }

        @Override
        public void onRegistrationFailure() {
            Log.d(TAG, "appoxee registration failed");
        }

        @Override
        public void onGeoRegistrationFailure() {
            //for future support
        }

        @Override
        public void onMessagesUpdateCompleted() {
            //for custom inbox
        }
    };

    //endregion

    //region Orchextra
    private boolean isRunning = false;
    private Button button, button2, button3, button4, button5;
    private TextView statusText;

    //region getViews/Buttons onClick
    private void getViews() {
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        statusText = (TextView) findViewById(R.id.statusText);
    }

    private void setListeners() {
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String TEST_STREAM_URL = "http://research.gigigo.com ";
        final String CUSTOM_SCHEME = "webview://";

        if (v.getId() == R.id.button) {
            Orchextra.startImageRecognition();
        }
        if (v.getId() == R.id.button2) {
            Orchextra.startScannerActivity();
        }
        if (v.getId() == R.id.button3) {
            if (isRunning) {
                stopOrchextra();
            } else {
                startOrchextra();
            }
        }
        //region orchextraWebview

        if (v.getId() == R.id.button4) {
            OxWebViewActivity.open(this, TEST_STREAM_URL, false);
            Orchextra.setCustomSchemeReceiver(new CustomSchemeReceiver() {
                @Override
                public void onReceive(String scheme) {
                    if (scheme.contains(CUSTOM_SCHEME)) {
                        String url = scheme.replace(CUSTOM_SCHEME, "");
                        OxWebViewActivity.navigateUrl(url);
                    }
                }
            });
        }
        //endregion

        //region orchextraWebview in app activity
        if (v.getId() == R.id.button5) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("URL", TEST_STREAM_URL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        }
        //endregion
    }

    //endregion
    private void startOrchextra() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Orchextra.start();
            //   Orchextra.start("aaf3e003bdeb2ca5c657dc3fdec161c7c11928b7", "d62ae5a993af5eda9e48fe06c4acc525a335f632");
            }
        });

        isRunning = true;
        button3.setText(R.string.ox_stop_orchextra);
        statusText.setText(getString(R.string.status_text, getString(R.string.status_running)));
    }

    private void stopOrchextra() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Orchextra.stop();
            }
        });
        isRunning = false;
        button3.setText(R.string.ox_start_orchextra);
        statusText.setText(getString(R.string.status_text, getString(R.string.status_stoped)));
    }

    //todo neverused
    /*
    public void setCrmUser() {
        String myCrmId = getApplicationContext().getPackageName();
        GregorianCalendar  BirthDay = new GregorianCalendar(1981, Calendar.MAY, 31);
        CrmUser crmUser = new CrmUser(myCrmId
                , BirthDay
                , CrmUser.Gender.GenderMale
        );
        Orchextra.setUser(crmUser);
    }
    */
    //endregion


}
