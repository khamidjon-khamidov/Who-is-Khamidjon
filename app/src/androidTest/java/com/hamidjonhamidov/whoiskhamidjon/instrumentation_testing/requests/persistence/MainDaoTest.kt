package com.hamidjonhamidov.whoiskhamidjon.instrumentation_testing.requests.persistence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario.launch
import com.hamidjonhamidov.whoiskhamidjon.models.about_me.AboutMeModel
import com.hamidjonhamidov.whoiskhamidjon.models.posts.BlogPostModel
import com.hamidjonhamidov.whoiskhamidjon.models.skills.SkillModel
import com.hamidjonhamidov.whoiskhamidjon.util.LiveDataUtil
import com.hamidjonhamidov.whoiskhamidjon.util.TestUtil
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MainDaoTest : AppDatabaseTest() {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    /*
        InsertOrReplace, Read aboutMe model
     */
    @Test
    fun insertOrReplaceReadAboutMe() {
        val aboutMe = AboutMeModel(TestUtil.ABOUTME_TEMPLATE_1)

        // insert
        getMainDao().insertOrReplaceAboutMe(aboutMe)

        // read
        val liveDataUtil = LiveDataUtil<AboutMeModel?>()
        val insertedModel =
            liveDataUtil
                .getValue(getMainDao().getAboutMeModel())

        assertNotNull(insertedModel)
        assertEquals(aboutMe.phone_number, insertedModel?.phone_number)
        assertEquals(aboutMe.tuit, insertedModel?.tuit)
        assertEquals(aboutMe.south_bank, insertedModel?.south_bank)

        println(aboutMe)
        println(insertedModel)
        println("Hello")
        assertEquals(aboutMe, insertedModel)
    }

    /*
        InsertOrReplace skill model, get SkillsModel List
     */
    @Test
    fun insertOrReplaceSkillModel() {
        runBlocking {
            // insert skills
            getMainDao().insertOrReplaceSkill(TestUtil.SKILL_TEMPLATE_1)
            getMainDao().insertOrReplaceSkill(TestUtil.SKILL_TEMPLATE_2)
        }

        // read skills
        val liveDataUtil = LiveDataUtil<List<SkillModel>>()
        val insertedList = liveDataUtil
            .getValue(getMainDao().getSkills())

        assertNotNull(insertedList)
        assertEquals(2, insertedList!!.size)
        assertEquals(insertedList[0], TestUtil.SKILL_TEMPLATE_1)
        assertEquals(insertedList[1], TestUtil.SKILL_TEMPLATE_2)
    }


}














