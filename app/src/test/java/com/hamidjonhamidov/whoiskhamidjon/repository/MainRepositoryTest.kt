package com.hamidjonhamidov.whoiskhamidjon.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.hamidjonhamidov.whoiskhamidjon.models.about_me.AboutMeModel
import com.hamidjonhamidov.whoiskhamidjon.models.skills.SkillModel
import com.hamidjonhamidov.whoiskhamidjon.requests.firebase.RequestFromFirebase
import com.hamidjonhamidov.whoiskhamidjon.requests.persistence.MainDao
import com.hamidjonhamidov.whoiskhamidjon.ui.DataState
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeViewState
import com.hamidjonhamidov.whoiskhamidjon.ui.main.skills.state.SkillsViewState
import com.hamidjonhamidov.whoiskhamidjon.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*


@ExtendWith(InstantExecutorExtension::class)
class MainRepositoryTest {

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    // system under test
    private lateinit var mainRepository: MainRepository

    private lateinit var requestFromFirebase: RequestFromFirebase
    private lateinit var mMainDao: MainDao
    private var dbFirebase: FirebaseFirestore? = null

    @BeforeEach
    fun initEach() {

        dbFirebase = mock(FirebaseFirestore::class.java)
        requestFromFirebase = mock(RequestFromFirebase::class.java)
        mMainDao = mock(MainDao::class.java)

        mainRepository = MainRepository(
            requestFromFirebase,
            mMainDao
        )
    }

    /*
        assert objects are not null
     */
    @Test
    fun assertNotNull() {
        assertNotNull(mMainDao)
        assertNotNull(requestFromFirebase)
        assertNotNull(mainRepository)
    }

    /*
        getAboutMeInfo by calling getAboutMeInfo()
        verify correct data received
     */
    @Test
    fun getAboutMe_checkData() {
        // arrange
        val mockModel = TestUtil.ABOUTME_TEMPLATE_1
        val liveDataModel = MutableLiveData<AboutMeModel?>()
        liveDataModel.value = mockModel

        val fireBaseResponse = MutableLiveData<GenericNetworkResponse<AboutMeModel>>()
        fireBaseResponse.value = GenericNetworkResponse.create(mockModel)

        `when`(mMainDao.getAboutMeModel()).thenReturn(liveDataModel)
        `when`(mMainDao.insertOrReplaceAboutMe(mockModel)).thenReturn(1)
        `when`(requestFromFirebase.requestAboutMeInfo()).thenReturn(fireBaseResponse)


        // act
        val liveDataUtil = LiveDataUtil<DataState<AboutMeViewState>>()
        val returnedValue = liveDataUtil.getValue(mainRepository.getAboutMeInfo(true))

        // assert
        assertEquals(
            (fireBaseResponse.value as NetworkSuccessResponse<AboutMeModel>).body,
            mockModel
        )

        assertEquals(
            returnedValue?.data?.dataReceived?.peekContent()?.aboutMeFields?.aboutMeModel,
            mockModel
        )
    }


    /*
        get SkillsList by calling getSkillsList()
        verify correct data received
     */
    @Test
    internal fun getSkillsList_checkData() {
        // arrange
        val mockSkillsList = TestUtil.skillList
        val liveDataModel = MutableLiveData<List<SkillModel>>()
        liveDataModel.value = mockSkillsList

        val fireBaseResponse =
            MutableLiveData<GenericNetworkResponse<List<SkillModel>>>()

        fireBaseResponse.value = GenericNetworkResponse.create(mockSkillsList)

        `when`(mMainDao.getSkills()).thenReturn(liveDataModel)
        `when`(requestFromFirebase.requestSkills()).thenReturn(fireBaseResponse)

        // act
        val liveDataUtil = LiveDataUtil<DataState<SkillsViewState>>()
        val returnedValue =
            liveDataUtil.getValue(mainRepository.getSkillsList(true))

        // assert
        assertEquals(
            (fireBaseResponse.value as NetworkSuccessResponse<List<SkillModel>>).body,
            mockSkillsList
        )

        assertEquals(
            returnedValue?.data?.dataReceived?.peekContent()?.skillsListFields?.skillsList,
            mockSkillsList
        )
    }
}













