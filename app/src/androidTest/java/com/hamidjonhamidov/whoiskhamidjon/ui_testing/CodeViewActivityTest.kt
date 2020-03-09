package com.hamidjonhamidov.whoiskhamidjon.ui_testing.source_code

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.hamidjonhamidov.whoiskhamidjon.R
import com.hamidjonhamidov.whoiskhamidjon.ui.source_code.CodeViewActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class CodeViewActivityTest{

    @get: Rule
    val activityRule
            = ActivityScenarioRule(CodeViewActivity::class.java)


    @Test
    fun test_isActivitInView() {

        onView(withId(R.id.cl_container_code_view))
            .check(matches(isDisplayed()))
    }

//    @Test
//    fun test_codeViewInView() {
//        onView(withId(R.id.codeView))
//            .check(matches(isDisplayed()))
//    }
}



























