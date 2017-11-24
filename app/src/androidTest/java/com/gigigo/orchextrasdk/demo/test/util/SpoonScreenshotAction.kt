package com.gigigo.orchextrasdk.demo.test.util


import android.app.Activity
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.isRoot
import android.view.View
import com.squareup.spoon.Spoon
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import java.io.File

/**
 * Class to take screenshots using Spoon library from an Espresso test
 * Original code from Gist: https://gist.github.com/edenman/7fdd32a4d59ccc01185b
 */
class SpoonScreenshotAction
/**
 * Initialize with information required to take a screenshot
 *
 * @param tag        Name of the screenshot to include in the file name
 * @param testClass  Name of the class taking the screenshot (required by Spoon library)
 * @param testMethod Name of the method taking the screenshot
 */
private constructor(private val mTag: String, private val mTestClass: String,
    private val mTestMethod: String) : ViewAction {

  override fun getConstraints(): Matcher<View> {
    return Matchers.any(View::class.java)
  }

  override fun getDescription(): String {
    return "Taking a screenshot using spoon."
  }

  override fun perform(uiController: UiController, view: View) {
    lastScreenshot = Spoon.screenshot(getActivity(view), mTag, mTestClass, mTestMethod)
  }

  companion object {

    /**
     * Get the last captured screenshot file
     *
     * @return Last screenshot file handler or null if there was no screenshot taken
     */
    var lastScreenshot: File? = null
      private set

    /**
     * Get the activity from the context of the view
     *
     * @param view View from which the activity will be inferred
     * @return Activity that contains the given view
     */
    private fun getActivity(view: View): Activity {
      return view.findViewById<View>(android.R.id.content).context as Activity
    }

    /**
     * Espresso action to be take a screenshot of the current activity
     * This must be called directly from the test method
     *
     * @param tag Name of the screenshot to include in the file name
     */
    fun perform(tag: String) {
      val trace = Thread.currentThread().stackTrace
      val testClass = trace[3].className
      val testMethod = trace[3].methodName
      onView(isRoot()).perform(SpoonScreenshotAction(tag, testClass, testMethod))
    }
  }
}