package com.hamidjonhamidov.whoiskhamidjon.instrumentation_testing.requests.firebase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hamidjonhamidov.whoiskhamidjon.models.about_me.AboutMeModel
import com.hamidjonhamidov.whoiskhamidjon.models.skills.SkillModel
import com.hamidjonhamidov.whoiskhamidjon.util.GenericNetworkResponse
import com.hamidjonhamidov.whoiskhamidjon.util.LiveDataUtil
import com.hamidjonhamidov.whoiskhamidjon.util.NetworkSuccessResponse
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class RequestFromFirebaseTest : FirebaseTest(){

    val aboutMeInCurrentFirebase = AboutMeModel(
        -1,
        "hamidovhamid1998@gmail.com",
        "133B Granville Road, London, N22 5LS",
        "07565336207",
        "[Data Structures and Algorithms - 5/5,C++ - 5/5,Math - 5/5,Physics - 5/5,Russian - 5/5,History - 5/5,English - 5/5,Computer&Human Interaction - 5/5]",
        "[Math - 90/100,Computer Science - 96/100,Software Development - 100/100]",
        "https://firebasestorage.googleapis.com/v0/b/who-is-khamidjon.appspot.com/o/profile_img_compressed.jpg?alt=media&token=9f22421d-fa58-49ee-ae10-d8f72d939d99"
    )

    val aboutMeFalseModel = AboutMeModel(
        344,
        "dsf",
        "dsflak",
        "dslaf",
        "sadlkfj",
        "kdsfl",
        "lasfdja;dlkf"
    )

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    /*
        test if the aboutMeModel matches with info in firebase firestore
     */
    @Test
    fun requestAboutMeInfoTest(){
        // get from firebase
        val liveDataUtil = LiveDataUtil<GenericNetworkResponse<AboutMeModel>>()
        val firebaseModelNetwork = liveDataUtil
            .getValue(requestAboutMe())

        val firebaseModel = (firebaseModelNetwork as NetworkSuccessResponse<AboutMeModel>).body

        // check if the data received is correct
        assertEquals(aboutMeInCurrentFirebase, firebaseModel)
        assertNotEquals(aboutMeFalseModel, firebaseModel)
    }

    /*
        test if skills list size matches with its size in real firebase so that to check the data is received
     */
    @Test
    fun checkSkillsListSizeMatches(){
        // get skillslist from firebase
        val liveDataUtil = LiveDataUtil<GenericNetworkResponse<List<SkillModel>>>()
        val firebaseSkillListNetwork = liveDataUtil
            .getValue(requestSkills())
        val firebaseSkillList =
            (firebaseSkillListNetwork as NetworkSuccessResponse<List<SkillModel>>)
                .body

        // check if the size matches
        assertEquals(firebaseSkillList.size, 6)
    }
}















