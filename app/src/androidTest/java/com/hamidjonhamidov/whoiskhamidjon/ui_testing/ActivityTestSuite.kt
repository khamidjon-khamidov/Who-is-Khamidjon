package com.hamidjonhamidov.whoiskhamidjon.ui_testing.source_code

import com.hamidjonhamidov.whoiskhamidjon.ui.source_code.CodeViewActivity
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(
    SourceCodeActivityTest::class,
    CodeViewActivity::class
)
class ActivityTestSuite{}