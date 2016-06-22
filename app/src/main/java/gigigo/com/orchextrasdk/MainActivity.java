package gigigo.com.orchextrasdk;

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
import com.gigigo.orchextra.ORCUser;
import com.gigigo.orchextra.ORCUserTag;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.sdk.OrchextraManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isRunning = false;

    private Button button3;
    private TextView statusText;

    /**/
    private final static String APPOXEE_SDK_KEY = "575e7877b16df0.22390647";
    private final static String APPOXEE_SDK_SECRET = "575e7877b16f48.87387869";
    private static final String TAG = "MainActivity";
    private static final String SOME_UNIQUE_USER_IDENTIFIER = "example";
    private static final String SOME_CUSTOM_FIELD = "custom_field";
    ExecutorService threadPool = Executors.newSingleThreadExecutor();
    /**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("APP", "Hello MainActivity, start onCreate");
        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        Log.d("APP", "Hello MainActivity, end onCreate");

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);

        statusText = (TextView) findViewById(R.id.statusText);


        startOrchextra();
        startAppoxee();
    }

    /**/
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

    /**/
    private void setCrmUser() {
     /*new method setuser for tags*/
        ORCUserTag[] orcUserTagArray = new ORCUserTag[]{
                new ORCUserTag("Pais", "Colombia"),
                new ORCUserTag("::macheo", "espacio espacio"),
                new ORCUserTag("or 1=1", "and 0=0"),
                new ORCUserTag("||  1=1", "&& 0=0"),
                new ORCUserTag("JiYgIDE9MQ==", "JiYgIDE9MQ=="),
                new ORCUserTag("grijander monder6", " 7::1"),
                new ORCUserTag("genero", "macho ta"),
                new ORCUserTag("GEneRo", "/7uijk''=2"),
                new ORCUserTag("yeah 41", "sibarete 34 "),
                new ORCUserTag("12 :3432 dasa", "uoyo"),
                new ORCUserTag("fasa", "teg tags a"),
                new ORCUserTag("fasa", "yanosaleel:nullen el 105")};

        String myCrmId = getApplicationContext().getPackageName();
        //
        /*ORCUserTag[] orcUserTagArray = new ORCUserTag[]{
                new ORCUserTag("Pais", "Colombia")};*/
        GregorianCalendar ORCBirthDay = new GregorianCalendar(1990, Calendar.MAY, 29);
        ORCUser orcUser = new ORCUser(myCrmId
                , ORCBirthDay
                , ORCUser.Gender.ORCGenderMale,
                orcUserTagArray);
        Orchextra.setUser(orcUser);
    }


    @Override
    public void onClick(View v) {
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
    }

    private void startOrchextra() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Orchextra.start(App.API_KEY, App.API_SECRET);
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
}
