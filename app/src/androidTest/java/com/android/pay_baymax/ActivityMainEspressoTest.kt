package com.android.pay_baymax

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.android.pay_baymax.ui.MainActivity
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class ActivityMainEspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testHintDisplay() {
        onView(withHint("0.0")).check(matches(isDisplayed()))
    }

    @Test
    fun testAllViewsAddedToScreen() {
        onView(withId(R.id.amount_hint_edittext))
        onView(withId(R.id.conversion_rate_list_spinner))
    }

    @Test
    fun testSelectBySpinnerPosition() {
        onView(withId(R.id.conversion_rate_list_spinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)))).atPosition(2).perform(click())
    }

    @Test
    fun testSelectBySpinnerText() {
        onView(withId(R.id.conversion_rate_list_spinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("--AED-- United Arab Emirates Dirham"))).perform(click())
    }

    @Test
    fun testTypeTextIntoEditText() {
        onView(withId(R.id.amount_hint_edittext)).perform(typeText("129138392478364782"))
    }
}