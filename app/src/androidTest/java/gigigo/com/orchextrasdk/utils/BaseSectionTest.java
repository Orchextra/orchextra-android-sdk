/*
 * Created by Orchextra
 *
 * Copyright (C) 2017 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gigigo.com.orchextrasdk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import gigigo.com.orchextrasdk.demo.ui.login.LoginActivity;
import java.io.File;
import org.junit.Rule;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class BaseSectionTest {

  @Rule public IntentsTestRule<LoginActivity> activityRule =
      new IntentsTestRule<>(LoginActivity.class, true, false);

  public LoginActivity startActivity() {
    return activityRule.launchActivity(null);
  }

  @SuppressLint("ApplySharedPref") public static void clearPreferences() {
    File root = InstrumentationRegistry.getTargetContext().getFilesDir().getParentFile();
    String[] sharedPreferencesFileNames = new File(root, "shared_prefs").list();
    for (String fileName : sharedPreferencesFileNames) {
      InstrumentationRegistry.getTargetContext()
          .getSharedPreferences(fileName.replace(".xml", ""), Context.MODE_PRIVATE)
          .edit()
          .clear()
          .commit();
    }
  }

  public static Context getContext() {
    return getInstrumentation().getTargetContext().getApplicationContext();
  }
}
