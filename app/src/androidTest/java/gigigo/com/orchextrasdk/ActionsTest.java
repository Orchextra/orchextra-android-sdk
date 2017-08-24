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

package gigigo.com.orchextrasdk;

import android.support.test.runner.AndroidJUnit4;
import gigigo.com.orchextrasdk.pages.MainPage;
import gigigo.com.orchextrasdk.pages.WebViewPage;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class) public class ActionsTest extends BaseSectionTest {

  @Test public void shouldExecuteBrowserActionOnDetectQRCode() {

    startActivity();

    WebViewPage webViewPage =
        (new MainPage(getContext())).startOrchextra().detectQRCodeWithOpenWebViewActionAssociated();
  }
}
