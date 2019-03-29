package com.shrikantbadwaik.newsheadlines

import android.support.test.espresso.Espresso
import android.support.test.espresso.PerformException
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.shrikantbadwaik.newsheadlines.view.topheadline.TopHeadlineActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TopHeadlineActivityInstrumentedTest {
    @Rule
    @JvmField
    val rule = ActivityTestRule(TopHeadlineActivity::class.java)

    @Test
    fun searchViewTextTest() {
        try {
            Espresso.onView(ViewMatchers.withId(R.id.search_view))
                .perform(ViewActions.typeText("IPL"))
                .perform(ViewActions.pressImeActionButton())
        } catch (e: PerformException) {
            Log.d("SearchTweetActivityInstrumentedTest", e.localizedMessage)
        }
    }

    @Test
    fun dialogButtonOKClickTest() {
        try {
            Espresso.onView(ViewMatchers.withId(R.id.search_view))
                .perform(ViewActions.typeText("Election 2019"))
                .perform(ViewActions.pressImeActionButton())
            Espresso.onView(ViewMatchers.withId(R.id.txt_error_message))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.btn_ok))
                .perform(ViewActions.click())
        } catch (e: PerformException) {
            Log.d("SearchTweetActivityInstrumentedTest", e.localizedMessage)
        }
    }

    @Test
    fun selectCountryButtonClickTest() {
        try {
            Espresso.onView(ViewMatchers.withId(R.id.btn_select_country))
                .perform(ViewActions.click())
            Espresso.onView(ViewMatchers.withText(R.string.txt_select_country))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        } catch (e: PerformException) {
            Log.d("SearchTweetActivityInstrumentedTest", e.localizedMessage)
        }
    }

    @Test
    fun selectCategoryButtonClickTest() {
        try {
            Espresso.onView(ViewMatchers.withId(R.id.btn_select_category))
                .perform(ViewActions.click())
            Espresso.onView(ViewMatchers.withText(R.string.txt_select_category))
                .inRoot(RootMatchers.isDialog())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        } catch (e: PerformException) {
            Log.d("SearchTweetActivityInstrumentedTest", e.localizedMessage)
        }
    }
}
