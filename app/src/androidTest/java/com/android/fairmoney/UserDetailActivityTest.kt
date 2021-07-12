package com.android.fairmoney

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.android.fairmoney.activities.EXTRA_USER_ID
import com.android.fairmoney.activities.UserDetailActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDetailActivityTest {

    @get:Rule
    var rule: ActivityTestRule<UserDetailActivity> = ActivityTestRule(
        UserDetailActivity::class.java,
        false,
        false
    )

    @Test
    fun testAllViewsAddedToScreen() {
        val intent = Intent()
        intent.putExtra(EXTRA_USER_ID, "60d0fe4f5311236168a109ca")
        rule.launchActivity(intent)

        onView(withId(R.id.progress_bar_circular))
        onView(withId(R.id.image_view_full_profile_picture))
        onView(withId(R.id.text_view_full_profile_name))
        onView(withId(R.id.text_view_error_occurred))
        onView(withId(R.id.text_view_address))
        onView(withId(R.id.text_view_email))
        onView(withId(R.id.text_view_phone))
        onView(withId(R.id.text_view_gender))
    }
}