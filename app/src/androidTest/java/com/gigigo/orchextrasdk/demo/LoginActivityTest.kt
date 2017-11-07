package com.gigigo.orchextrasdk.demo

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.gigigo.orchextrasdk.demo.screen.LoginScreen
import com.gigigo.orchextrasdk.demo.ui.login.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

  @Rule
  @JvmField
  val rule = ActivityTestRule(LoginActivity::class.java)

  val screen = LoginScreen()

  @Test
  fun testChangeProjectOnDoubleClick() {
    screen {

      logoLayout {
        click()
        click()
      }

      projectNameEditText {
        hasText("IOS")
      }
    }
  }
}