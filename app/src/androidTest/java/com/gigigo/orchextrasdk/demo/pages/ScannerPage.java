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

package com.gigigo.orchextrasdk.demo.pages;

import android.content.Context;
import android.support.test.espresso.ViewInteraction;
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.utils.TestUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class ScannerPage extends DemoPage {

  public ScannerPage(Context context) {
    super(context);
  }

  public ScannerPage checkIfOxScannerButtonIsDisplayed() {
    ViewInteraction button = onView(allOf(withId(R.id.ox_scanner_button),
        TestUtils.childAtPosition(TestUtils.childAtPosition(withId(R.id.fragment_container), 0), 0),
        isDisplayed()));
    button.check(matches(isDisplayed()));

    return this;
  }
}
