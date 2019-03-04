package com.shrikantbadwaik.weatherforcast

import android.support.test.espresso.Espresso
import android.support.test.espresso.PerformException
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.shrikantbadwaik.weatherforcast.view.weatherforecast.WeatherForecastActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class WeatherForecastActivityInstrumentationTest {
    @Rule
    @JvmField
    val rule = ActivityTestRule(WeatherForecastActivity::class.java)

    private val currentTempInC: String? = "20\u00B0"
    private val currentCity: String? = ""

    @Test
    fun currentTemperatureTest() {
        try {
            currentTempInC?.let {
                Espresso.onView(withId(R.id.tv_current_temperature))
                    .perform(ViewActions.typeText(currentTempInC))
            }
        } catch (e: PerformException) {
            Log.d("WeatherForecastActivityInstrumentationTest", e.localizedMessage)
        }
    }

    @Test
    fun currentCityTest() {
        try {
            currentCity?.let {
                Espresso.onView(withId(R.id.tv_city))
                    .perform(ViewActions.typeText(currentCity))
            }
        } catch (e: PerformException) {
            Log.d("WeatherForecastActivityInstrumentationTest", e.localizedMessage)
        }
    }
}
