package com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.viewmodel

import com.hamidjonhamidov.whoiskhamidjon.repository.MainRepository
import com.hamidjonhamidov.whoiskhamidjon.session.SessionManager
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeStateEvent
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeStateEvent.GetAboutMeStateEvent
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeViewState
import com.hamidjonhamidov.whoiskhamidjon.ui.main.about_me.state.AboutMeViewState.AboutMeFields
import com.hamidjonhamidov.whoiskhamidjon.util.AbsentLiveData
import com.hamidjonhamidov.whoiskhamidjon.util.InstantExecutorExtension
import com.hamidjonhamidov.whoiskhamidjon.util.LiveDataUtil
import com.hamidjonhamidov.whoiskhamidjon.util.TestUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
class AboutMeViewModelTest {

    // system under test
    private lateinit var aboutMeViewModel: AboutMeViewModel

    @Mock
    private lateinit var mainRepository: MainRepository

    @Mock
    private lateinit var sessionManager: SessionManager

    @BeforeEach
    fun init(){
        MockitoAnnotations.initMocks(this)
        aboutMeViewModel = AboutMeViewModel(
            sessionManager = sessionManager,
            mainRepository = mainRepository
        )
    }

    /*
        assert aboutMeModel is null when not set
     */
    @Test
    fun  assertModelIsNulWhenNotSet(){
        // arrange
        val liveDataUtil = LiveDataUtil<AboutMeViewState>()

        // act
        val aboutMeViewState =
            liveDataUtil.getValue(aboutMeViewModel.viewState)

        // assert
        assertNull(aboutMeViewState)
    }


    /*
        Observe the note when new stateEvent is set
     */
    @Test
    fun assertModelIsSet(){

        // arrange
        val mockModel = TestUtil.ABOUTME_TEMPLATE_1
        val mockState = AboutMeViewState(
            AboutMeFields(mockModel)
        )

        val liveDataUtil = LiveDataUtil<AboutMeViewState>()

        // act
        aboutMeViewModel.setViewState(mockState)
        val settedModel = liveDataUtil.getValue(
            aboutMeViewModel.viewState
        )

        // assert
        assertEquals(mockModel, settedModel?.aboutMeFields?.aboutMeModel)
    }

    @Test
    fun assertHandleStateEventCalledWhenStateEventSet(){
        // arrange
        val mockStateEvent = GetAboutMeStateEvent()
//        val liveDataUtil = LiveDataUtil<AboutMeStateEvent>()

        // act
        `when`(mainRepository.getAboutMeInfoForTesting())
            .thenReturn(AbsentLiveData.create())

//        aboutMeViewModel.setStateEvent(mockStateEvent)

        // assert
//        println(aboutMeViewModel.dataState.value)
        verify(mainRepository).getAboutMeInfoForTesting()
    }
}















