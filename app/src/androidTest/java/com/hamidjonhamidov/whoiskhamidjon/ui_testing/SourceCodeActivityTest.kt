package com.hamidjonhamidov.whoiskhamidjon.ui_testing.source_code

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.ui.source_code.SourceCodeActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SourceCodeActivityTest{

    // testing activity is in view
    @Test
    fun test_isActivityInView() {
        val activityScenario =
            ActivityScenario.launch(SourceCodeActivity::class.java)

        // check main container is in view
        onView(withId(R.id.ll_source_code_main_id)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_toolbar_imageExpand_imageFileType_fileName() {
        val activityScenario = ActivityScenario.launch(SourceCodeActivity::class.java)

        // test root container container
        onView(withId(R.id.tool_bar_source_code))
            .check(matches(isDisplayed()))

        // test main container for root file
        onView(withId(R.id.cl_root_file_container))
            .check(matches(isDisplayed()))

        // test image view expand for root
        onView(withId(R.id.iv_expand))
            .check(matches(isDisplayed()))

        // text image view file type for root
        onView(withId(R.id.iv_file_type))
            .check(matches(isDisplayed()))

        // test text view file name for root
        onView(withId(R.id.tv_file_name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_file_name_textView_Displayed() {
        val activityScenario =
            ActivityScenario.launch(SourceCodeActivity::class.java)

        onView(withId(R.id.tv_file_name))
            .check(matches(withText(R.string.root_file)))
    }
}























