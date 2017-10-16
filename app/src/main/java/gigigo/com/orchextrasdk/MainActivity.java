package gigigo.com.orchextrasdk;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.gigigo.orchextra.CrmUser;
import com.gigigo.orchextra.CustomSchemeReceiver;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.ui.webview.OxWebViewActivity;
import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gigigo.orchextra.Orchextra.stop;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  @Override protected void onStop() {
    super.onStop();
    //App.mMotionServiceUtility.start(); //motion, only in background
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //orchextraSDK
    getViews();
    setListeners();
   // Orchextra.start();//step 2 start with bad credentials for ask permission
    //Ad-Ons Motion and Bluetooth the UpdateConfig in on Mainactivity
    //App.mMotionServiceUtility.stop();//motion
  }

  //region Orchextra
  private boolean isRunning = false;
  private Button button, button2, button3, button4, button5, button6, button7, button8, button9;
  private TextView statusText;

  //region getViews/Buttons onClick
  private void getViews() {
    button = (Button) findViewById(R.id.button);
    button2 = (Button) findViewById(R.id.button2);
    button3 = (Button) findViewById(R.id.button3);
    button4 = (Button) findViewById(R.id.button4);
    button5 = (Button) findViewById(R.id.button5);
    button6 = (Button) findViewById(R.id.button6);
    button7 = (Button) findViewById(R.id.button7);
    button8 = (Button) findViewById(R.id.button8);
    button9 = (Button) findViewById(R.id.button9);

    statusText = (TextView) findViewById(R.id.statusText);
  }

  private void setListeners() {
    button.setOnClickListener(this);
    button2.setOnClickListener(this);
    button3.setOnClickListener(this);
    button4.setOnClickListener(this);
    button5.setOnClickListener(this);
    button6.setOnClickListener(this);
    button7.setOnClickListener(this);
    button8.setOnClickListener(this);
    button9.setOnClickListener(this);
  }

  @Override public void onClick(View v) {

    if (v.getId() == R.id.button) {
      Orchextra.startImageRecognition();
    }
    if (v.getId() == R.id.button2) {
      Orchextra.startScannerActivity();
    }
    if (v.getId() == R.id.button3) {
      //if (isRunning) {
      //  stopOrchextra();
      //} else {
      startOrchextra();
      //}
    }
    //region orchextraWebview

    if (v.getId() == R.id.button4) {
      OxWebViewActivity.open(this, App.GIGIGO_URL, false);
      Orchextra.setCustomSchemeReceiver(new CustomSchemeReceiver() {
        @Override public void onReceive(String scheme) {
          if (scheme.contains(App.CUSTOM_SCHEME)) {
            String url = scheme.replace(App.CUSTOM_SCHEME, "");
            OxWebViewActivity.navigateUrl(url);
          }
        }
      });
    }
    //endregion

    //region orchextraWebview in app activity
    if (v.getId() == R.id.button5) {
      Intent intent = new Intent(this, WebViewActivity.class);
      intent.putExtra("URL", App.GIGIGO_URL);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      this.startActivity(intent);
    }
    //endregion

    //region Segmentation
    if (v.getId() == R.id.button6) {
      List<String> lst_tags, lst_bu;
      //all tags/BU or Custom fields must be exist in project dashboard configuration
      //only the matching Tags/BU/CF will be setted on server, you can check it on configuration json response
      lst_tags = Arrays.asList("tagTest", "tagTest1");
      lst_bu = Arrays.asList("BuTest", "BuTest1");
      //region Segmentation DEVICE
      //this set tag for the device
      Orchextra.setDeviceTags(lst_tags);
      Orchextra.setDeviceBusinessUnits(lst_bu);
      //endregion
      //region Segmentation CRMUser
      //if you have a binded Crm User you can set tags/bu/cf
      Orchextra.setUserTags(lst_tags);
      Orchextra.setUserBusinessUnits(lst_bu);

      //Custom Fields, like tags and bu is mandatory that the CF exist on dashboard
      Map<String, String> nameValue = new HashMap<String, String>();
      nameValue.put("name", "yourName");
      Orchextra.setUserCustomFields(nameValue);
      //endregion
      //this request config and save the segmentation data on server side
      Orchextra.commitConfiguration();

      //region Clear segmentation
      //For clear all segmentation data
      //Orchextra.clearDeviceBusinessUnits();
      //Orchextra.clearDeviceTags();

      //Orchextra.clearUserTags();
      //Orchextra.clearUserBusinessUnits();

      //Orchextra.clearUserCustomFields();
      //request configuration for save in server side
      //Orchextra.commitConfiguration();
      //endregion
    }
    //endregion



    //region BindUser
    if (v.getId() == R.id.button8) {

      // CRM User
      String CRM_ID = getUniqueCRMID();
      Orchextra.bindUser(new CrmUser(CRM_ID, new GregorianCalendar(1981, Calendar.MAY, 31),
          CrmUser.Gender.GenderMale));

      Orchextra.commitConfiguration();
    }
    //endregion

    //region ChangeCredentials
    if (v.getId() == R.id.button9) {
      //for reset the CRMUser
      String API_KEY = "8286702045adf5a3ad816f70ecb80e4c91fbb8de";
      String API_SECRET = "eab37080130215ced60eb9d5ff729049749ec205";
      Orchextra.start();
      Orchextra.updateSDKCredentials(API_KEY,API_SECRET,true);
    }
    //endregion

    //region BindUser
    if (v.getId() == R.id.button10) {
      clearDataAndGoToChangeCountryView();
    }
    //endregion
  }

  private void clearDataAndGoToChangeCountryView() {
    clearApplicationData();
    Orchextra.stop(); //asv V.I.Code
  }

  public void clearApplicationData() {
    File cache = getCacheDir();
    File appDir = new File(cache.getParent());
    if (appDir.exists()) {
      String[] children = appDir.list();
      for (String s : children) {
        if (!s.equals("lib")) {
          deleteDir(new File(appDir, s));
        }
      }
    }
  }

  public static boolean deleteDir(File dir) {
    if (dir != null && dir.isDirectory()) {
      String[] children = dir.list();
      for (int i = 0; i < children.length; i++) {
        boolean success = deleteDir(new File(dir, children[i]));
        if (!success) {
          return false;
        }
      }
    }

    return dir.delete();
  }



  private String getUniqueCRMID() {
    String secureAndroidId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
        Settings.Secure.ANDROID_ID);
    String serialNumber = Build.SERIAL;
    String deviceToken = secureAndroidId + BuildConfig.APPLICATION_ID + serialNumber;
    return deviceToken;
  }

  //endregion
  private void startOrchextra() {
    new Handler().post(new Runnable() {
      @Override public void run() {
        Orchextra.updateSDKCredentials(App.API_KEY, App.API_SECRET);
      }
    });

    isRunning = true;
    button3.setText(R.string.ox_stop_orchextra);
    statusText.setText(getString(R.string.status_text, getString(R.string.status_running)));
  }

  private void stopOrchextra() {
    new Handler().post(new Runnable() {
      @Override public void run() {
        stop();
      }
    });
    isRunning = false;
    button3.setText(R.string.ox_start_orchextra);
    statusText.setText(getString(R.string.status_text, getString(R.string.status_stoped)));
  }
  //endregion
}
