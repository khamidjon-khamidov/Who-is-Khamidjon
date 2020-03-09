package com.hamidjonhamidov.whoiskhamidjon.instrumentation_testing.requests.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.hamidjonhamidov.whoiskhamidjon.requests.firebase.RequestFromFirebase
import org.junit.After
import org.junit.Before

abstract class FirebaseTest{

    // system under test
    private lateinit var requestFromFirebase: RequestFromFirebase

    fun requestAboutMe() = requestFromFirebase.requestAboutMeInfo()

    fun requestSkills() = requestFromFirebase.requestSkills()

    @Before
    fun init(){
        requestFromFirebase = RequestFromFirebase(FirebaseFirestore.getInstance())
    }
}