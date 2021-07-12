package com.android.fairmoney

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.android.fairmoney.activities.MainActivity
import com.android.fairmoney.models.User
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testAllViewsAddedToScreen() {
        onView(withId(R.id.progress_bar_circular))
        onView(withId(R.id.text_view_empty))
        onView(withId(R.id.recycler_view_users))
    }

    @Test
    fun testRecyclerViewItemDataType() {
        onView(withId(R.id.recycler_view_users))
        onData(allOf(`is`(instanceOf(User.DataBeam::class.java))))
    }

    @Test
    fun testSelectByPositionRecyclerView() {
        onView(withId(R.id.recycler_view_users)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))
    }
}