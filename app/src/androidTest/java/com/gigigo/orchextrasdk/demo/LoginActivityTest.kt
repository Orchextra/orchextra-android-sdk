package com.gigigo.orchextrasdk.demo

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.gigigo.orchextrasdk.demo.screen.LoginScreen
import com.gigigo.orchextrasdk.demo.screen.ScannerScreen
import com.gigigo.orchextrasdk.demo.ui.login.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

  @Rule
  @JvmField
  val rule = ActivityTestRule(LoginActivity::class.java)

  val loginScreen = LoginScreen()
  val scannerScreen = ScannerScreen()

  @Test
  fun testChangeProjectOnDoubleClick() {
    loginScreen {

      logoLayout {
        click()
        click()
      }

      projectNameEditText {
        hasText("IOS")
      }
    }
  }

  @Test
  fun testLoginInTestProject() {
    loginScreen {

      projectNameEditText {
        hasText("AndroidSDK")
      }

      button {
        click()
      }
    }

    scannerScreen {
      oxScannerButton {
        isVisible()
      }
    }
  }
}