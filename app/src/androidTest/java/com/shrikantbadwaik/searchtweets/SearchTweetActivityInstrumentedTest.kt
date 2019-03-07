package com.shrikantbadwaik.searchtweets

import android.support.test.espresso.Espresso
import android.support.test.espresso.PerformException
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.shrikantbadwaik.searchtweets.view.searchtweets.SearchTweetsActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SearchTweetActivityInstrumentedTest {
    @Rule
    @JvmField
    val rule = ActivityTestRule(SearchTweetsActivity::class.java)

    @Test
    fun searchViewTextTest() {
        try {
            Espresso.onView(ViewMatchers.withId(R.id.search_view))
                .perform(ViewActions.typeText("Nasa"))
                .perform(ViewActions.pressImeActionButton())
        } catch (e: PerformException) {
            Log.d("SearchTweetActivityInstrumentedTest", e.localizedMessage)
        }
    }

    @Test
    fun dialogDisplayTest() {
        try {
            Espresso.onView(ViewMatchers.withId(R.id.search_view))
                .perform(ViewActions.typeText("ChampionsLeague"))
                .perform(ViewActions.pressImeActionButton())
            Espresso.onView(ViewMatchers.withId(R.id.txt_error_message))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
        } catch (e: PerformException) {
            Log.d("SearchTweetActivityInstrumentedTest", e.localizedMessage)
        }
    }

    @Test
    fun dialogButtonOKClickTest() {
        try {
            Espresso.onView(ViewMatchers.withId(R.id.search_view))
                .perform(ViewActions.typeText("ChampionsLeague"))
                .perform(ViewActions.pressImeActionButton())
            Espresso.onView(ViewMatchers.withId(R.id.txt_error_message))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.btn_ok))
                .perform(ViewActions.click())
        } catch (e: PerformException) {
            Log.d("SearchTweetActivityInstrumentedTest", e.localizedMessage)
        }
    }
}
